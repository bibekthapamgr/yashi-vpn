# Architecture and implementation contracts

## System boundaries

Android connects to the HTTPS API for account, device enrollment and access authorization. It connects separately over WireGuard UDP to an exit node for browsing. The web portals use the HTTPS API; they never carry VPN data traffic or hold device private keys. PostgreSQL is private to the control plane. A restricted node agent communicates over authenticated encrypted control links; no public agent admin API.

Control plane modules: identity, device registry, plan/entitlement policy, node catalog, lease provisioning, usage/quota ledger, operations, support, privacy/deletion and later billing. Begin as a modular service with background workers; do not add distributed infrastructure without a measured need.

## Entities (migrate and index explicitly)

| Entity | Key fields / constraints |
|---|---|
| User | UUID, verified email, password hash, status, created/deleted times; unique normalized email |
| AdminUser | Separate from customer User: UUID, verified email, password hash, MFA factor references, role (Owner/Operator/Support/Read-only), status; short-lived MFA-bound sessions; no shared accounts |
| Device | UUID, user, public key, friendly label, enrolled/revoked times; public key unique |
| AuthSession | hashed refresh-token reference, user, expiry, rotation/revocation state |
| VerificationToken | user, purpose (email verification, password reset, email change), hashed single-use token, expiry, used time |
| PrivacyChoice | user, setting (e.g. crash reports, diagnostics), value, changed time, privacy-text version |
| PlanVersion | tier, quota bytes, device limit, region eligibility, effective date; immutable versions |
| Entitlement | user, plan version, start/end, source, state; server-derived only |
| VpnNode | provider ref, region/city, public tunnel endpoint, public key, health, capacity, lifecycle |
| PeerLease | device, node, assigned addresses, expiry, state, idempotency key; unique active free lease |
| UsageLedger | account/period/node/lease, byte delta, counter epoch/sequence; deduplicated |
| QuotaReservation | account/period/node, allocated/consumed/returned bytes; atomic transaction |
| AgentCommand | typed command, desired generation, acknowledgement/error; no arbitrary shell |
| AdminAudit | actor, action, object, reason, timestamp; no secrets/destinations |
| Ticket / Incident | minimally necessary support content, states, retention |
| DiagnosticsReport | user/device, explicit consent time, redacted payload reference, app version, retention expiry; no destinations or browsing data |
| BetaInterest | email, consent-text version and time, source, confirmed/withdrawn times; minimal fields, deleted per retention |
| Subscription (M10) | provider token reference, verified purchase state, expiry, linked token, dedupe IDs |

Usage unit: bytes internally, GiB displayed (1 GiB = 1,073,741,824 bytes). Count WireGuard peer RX+TX as disclosed product usage; host egress bills are separate because provider measurement and overhead differ. Reconcile both, never equate them blindly. Period starts at 00:00 UTC on the first calendar day. Ledger survives agent/node restarts and counter resets via an epoch identifier.

## Quota and device enforcement

Account-wide 5 GiB proposed free allowance. Allocate bounded byte reservations to active nodes in a transactional backend operation; total outstanding plus consumed never exceeds allowance except a documented small packet/accounting tolerance. Node enforces its remaining allocation using local counters/firewall policy and removes access on exhaustion. Test enforcement with the app killed and backend unavailable.

Agent samples counters at a bounded interval, records durable epochs, sends idempotent deltas and does not double bill retries. On restart, validate local journal; uncertain state fails closed for new allocations. Do not erase usage by recreating a peer or switching regions. Return unused reservations only after the old peer is actually revoked or its enforced lease expires, not simply on a failed app disconnect request. Avoid cross-node overlap during switch/revocation; test simultaneous requests. Set a measured upper bound on packet/accounting overshoot (proposed <=16 MiB/account/period) and document it.

Leases have server-enforced expiry. Proposed renewal 5 minutes, hard lifetime 15 minutes, tuned after load/reliability tests. During control-plane outage existing authorized peers may continue only within their unexpired lease and reserved quota; new grants fail closed. Agent loss/revocation failure quarantines node and stops new leases; report pending revocation honestly. OS lockdown state remains separate from server authorization.

## Proposed API contract

Version `/v1`; JSON schema/OpenAPI source of truth; timestamps UTC ISO 8601; byte values integer-safe; cursor pagination. Error shape: `code`, safe `message`, `requestId`, optional `retryAfter`. No stack traces/secrets. All object ownership checks server-side.

| Endpoint group | Purpose / rules |
|---|---|
| POST `/auth/register`, `/verify`, `/login`, `/refresh`, `/logout`, `/reset/*` | Verification, rate limits, token rotation, generic anti-enumeration responses |
| GET `/me`, `/me/entitlement`, `/me/usage` | Authoritative account/quota/reset and timestamps |
| POST `/me/password`; GET `/me/sessions`; DELETE `/me/sessions/{id}` | Password change with reauthentication (revokes other sessions); list and revoke own sessions |
| GET/PATCH `/me/privacy` | Read and update privacy choices; enforced server-side and recorded with privacy-text version |
| POST/GET `/devices`; DELETE `/devices/{id}` | Enrollment with public key; transactional device-limit check; revoke node access |
| GET `/locations` | Sanitized healthy eligible catalog, maintenance, measurement age; no control secrets |
| POST `/leases`; POST `/leases/{id}/renew`; DELETE `/leases/{id}` | Idempotent grant/renew/revoke; async provisioning readiness; no private key sent |
| GET `/leases/{id}` | Desired vs actual provisioning; explicit pending/ready/failed |
| GET `/status` | Public sanitized status and current incidents |
| POST `/beta-interest` | Public consented beta signup before Play listing; email confirmation, rate limits, generic responses, withdrawal link |
| POST/GET `/support/tickets` | Owner-scoped tickets, attachment restrictions and retention |
| POST `/diagnostics` | Opt-in redacted diagnostics after in-app preview and explicit consent; size limits and retention expiry |
| POST `/me/deletion` | Reauthentication, delete workflow, immediate peer/session revocation |
| `/admin/*` | Separate admin authentication (AdminUser), MFA/RBAC, reason, audit, rate limits; sensitive operations step-up auth |
| POST `/billing/play/verify` (M10) | Backend verifies purchase; never trust a premium Boolean from app |

Lease response contains assigned tunnel addresses, node endpoint/public key, approved DNS and allowed routes; device composes the config locally with its private key. A successful HTTP response is not proof the peer is ready: wait for node acknowledgement and test the tunnel. Use polling with backoff for pending operations; no heavy realtime stack needed initially.

## Agent/infra controls

- Root only for bootstrap; restrict runtime privileges/capabilities to necessary VPN and firewall operations. Authenticate agent with per-node certificates or equivalent short-lived credentials; rotate/revoke independently.
- Linux image pinned to maintained Ubuntu LTS. Terraform/Ansible deploy firewall, WireGuard, IP forwarding, NAT, DNS, usage journal, agent and observability. Provider firewall permits tunnel UDP and restricted management only. Use SSH keys, no open database ports.
- Protect link-local/cloud metadata and private management ranges from VPN clients. Block client-to-client traffic and administrative services. Address IPv4 and IPv6, including ULA/link-local routes, not just one RFC1918 subnet. Egress abuse controls (e.g. outbound SMTP blocking) documented in acceptable use.
- Choose privacy-reviewed DNS routed in the tunnel. If operating a resolver, restrict it to VPN peers and disable query logging; never create an internet-open resolver. If using third-party DNS, disclose and review retention/processor terms.
- IPv6: fully route and test, or explicitly prevent IPv6 bypass. Never silently let IPv6 escape a nominal full tunnel. DNS/MTU/UDP behavior tested on mobile carriers.
- Persistent config reconciliation: desired peer state vs actual node state, expiry/revoke enforcement, health heartbeat, node drain and replacement. No auto-delete of a production node without approved runbook.
- Back up control data encrypted; key backup/recovery policy documented separately. Never copy client private keys into backups. Restore test required.
- Control-plane availability: leases hard-expire, so an API/database outage longer than the worst-case lease margin (hard lifetime minus renewal interval; 10 minutes with the initial proposal) disconnects every user. Keep a restorable standby for API and database (or a managed database), keep failover/restore inside that margin, use rolling or short maintenance and alert on lease-renewal failures. Changing lease timing needs security review and owner approval.

## Threat scenarios required in design review

Stolen auth token; malicious modified APK; leaked/stale tunnel configuration; device public-key reuse; simultaneous quota grants; agent compromise; spoofed heartbeat; billing replay; privilege escalation across accounts; provider API key exposure; DNS leak; IPv6 leak; open resolver; app killed with exhausted quota; region switch during revocation; metadata service access; fake Connected state; support attachment abuse. Record mitigations and residual risk, not just a checked box.
