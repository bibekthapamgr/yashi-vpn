Draft — M01 — 15 Oct 2026 — pending review

# Data inventory

Every data item Yashi VPN will process, from team pack v1.1: 01 §2, 02, 03 (entities, including AdminUser, VerificationToken, PrivacyChoice, DiagnosticsReport and BetaInterest) and 04 (retention). Nothing is collected yet: the only running code is the local `GET /v1/health` API.

- **Retention** uses the 04 proposals, which still need owner and legal approval. "To decide" means 04 has no proposal.
- **Processor:** "Hosting" means the hosting provider and "Email" the transactional email provider; neither is chosen (To decide). Other undecided vendors are marked To decide.
- **Access:** System means the API, workers or node agent, with no person involved. User means the customer, for their own data only. Owner, Operator, Support and Read-only are admin-portal roles (M07), enforced server-side; Support and Operator see masked emails by default (02). Direct host or database access follows the [access matrix](../ops/access-matrix.md); in production it is owner-approved, time-bound and audited, and it is not repeated in every row.
- T01–T19 refer to the [threat model](threat-model.md).

## 1. Accounts and identity

| Item | Purpose | Where stored | Who can access | Retention | Processor |
|---|---|---|---|---|---|
| Customer email (User) | Sign-in, verification, password reset, service notices | PostgreSQL | System; User; Support and Operator (masked); Owner | Account lifetime; post-deletion schedule To decide | Hosting; Email |
| Password hash (User) | Authentication | PostgreSQL | System only; no staff member can view it (01 §2) | Account lifetime; post-deletion schedule To decide | Hosting |
| Account status and created/deleted times (User) | Lifecycle: active, suspended, deleted | PostgreSQL | System; User; Support, Operator, Owner | Account lifetime; To decide | Hosting |
| Customer sessions (AuthSession: hashed refresh-token reference, expiry, rotation/revocation state) | Stay signed in; list and revoke sessions | PostgreSQL | System; User (own sessions) | To decide | Hosting |
| Access tokens and web session cookies | Authorize requests | Device secure storage; browser cookie | System; User | Short-lived; exact lifetimes To decide | Hosting |
| Verification and reset tokens (VerificationToken: purpose, hashed single-use token, expiry, used time) | Email verification, password reset, email change | PostgreSQL (hash only); the raw token exists only in the emailed link | System; User | To decide | Hosting; Email (carries the link) |
| Privacy choices (PrivacyChoice: setting, value, changed time, privacy-text version) | Record consent, for example for crash reports and diagnostics | PostgreSQL; mirrored in the app | System; User; Support (view) | To decide | Hosting |
| Admin accounts (AdminUser: email, password hash, MFA factor references, role, status) | Staff sign-in with MFA and roles, separate from customers | PostgreSQL | System; Owner (manages roles) | To decide | Hosting; Email |
| Admin sessions (short-lived, MFA-bound) | Authorize admin actions | PostgreSQL | System | Short-lived; To decide | Hosting |
| Admin audit (AdminAudit: actor, action, object ID, reason, UTC time; no secrets or destinations) | Accountability for staff actions | PostgreSQL | Owner; Read-only (as permitted) | 90 days | Hosting |

## 2. Devices, leases and usage

| Item | Purpose | Where stored | Who can access | Retention | Processor |
|---|---|---|---|---|---|
| Device record (Device: user, public key, friendly label, enrolled/revoked times) | Enforce the one-device limit; revoke access | PostgreSQL | System; User (own); Support, Operator, Owner | To decide | Hosting |
| Device WireGuard private key | Encrypts the user's tunnel | On the device only, with Keystore-backed protection where feasible (01 §2) | The app only; never uploaded, backed up or logged | Until device revoke, sign-out or uninstall (exact trigger To decide) | None |
| Device WireGuard public key | Identifies the peer on nodes | PostgreSQL (Device); the node's peer table while a lease is active | System (API, agent); Operator, Owner | To decide | Hosting |
| Peer lease (PeerLease: device, node, assigned tunnel addresses, expiry, state, idempotency key) | Time-limited access to one node | PostgreSQL; active peers on the node | System; Support (state), Operator, Owner | To decide. Lease history links an account to a node and a time window; on a node with few users this narrows down who used its exit IP | Hosting |
| Usage ledger (UsageLedger: account, period, node, lease, byte delta, epoch/sequence) | Quota accounting without double counting | PostgreSQL | System; Operator, Owner | Current + previous monthly period | Hosting |
| Quota reservations (QuotaReservation: allocated, consumed and returned bytes per account, period and node) | Prevent overspend across nodes | PostgreSQL | System; Operator, Owner | Current + previous monthly period | Hosting |
| Usage shown to users (monthly total; daily byte totals for the portal chart) | Transparent quota and reset date | PostgreSQL (derived) | System; User (own); Support, Operator, Owner; Read-only (aggregates without identity) | Current + previous monthly period | Hosting |
| Plan versions and entitlements (PlanVersion; Entitlement: user, plan version, start/end, source, state) | Server-derived limits | PostgreSQL | System; User (own); Support (view); Owner (edits plans) | Plan versions To decide; entitlements for the account lifetime, then To decide | Hosting |
| Connection-verification requests (the app-controlled reachability and exit-IP check, 01 §3) | Prove the tunnel works before showing Connected | Not stored; served by the control plane or the node (To decide) | System | Request-log rules only (§6) | Hosting |

## 3. On VPN nodes

| Item | Purpose | Where stored | Who can access | Retention | Processor |
|---|---|---|---|---|---|
| Live WireGuard peer state (public key, allowed IPs, current endpoint = the client's public IP and port, last handshake, bytes in/out) | WireGuard needs the endpoint to send replies; byte counts feed the quota | Node kernel memory | System (the agent reads bytes and handshake time); DevOps/security (root) | Memory only, while the peer exists. Only byte counts and handshake recency leave the node; the endpoint never does (verify in M08) | Hosting |
| Node usage journal (per-peer byte counters, epoch, sequence) | Survive restarts; send idempotent deltas | Node disk | System (agent); DevOps/security | Until the control plane acknowledges it (proposed); To decide | Hosting |
| NAT and connection-tracking state (tunnel address ↔ destination IP and port of active flows) | Forward and translate traffic | Node kernel memory | System; DevOps/security (root) | Transient (kernel timeouts); never logged; connection-tracking logging and accounting kept off | Hosting |
| Tunnel traffic contents | Forward to the destination | Node memory, in transit only | No one; never inspected or stored | Not stored | Hosting (network) |
| DNS queries from VPN users | Resolve names inside the tunnel | Resolver memory and cache; resolver on the node or a third party (To decide) | System | Not logged by us (query logging off, 03); third-party retention To verify | Self-hosted resolver or third-party DNS (To decide) |

## 4. On the Android device

Cloud backup and device-to-device transfer are already disabled for all app data (`AndroidManifest.xml`).

| Item | Purpose | Where stored | Who can access | Retention | Processor |
|---|---|---|---|---|---|
| Auth tokens | Stay signed in | Keystore-backed encrypted storage | The app | Until sign-out or expiry | None |
| Cached safe metadata (usage, reset date, locations, last verification time) | Show state offline, with its age (M05) | App storage | The app | Replaced on refresh; cleared on sign-out (To decide) | None |
| Local settings (theme, auto-reconnect, favorites) | Preferences | App storage | The app | Until uninstall; syncing favorites To decide | None |
| Local diagnostic log (redacted) | Preview before an opt-in diagnostics upload (A10) | App storage | The app; User (preview) | To decide (short) | None until the user sends it |

## 5. Support, outreach and third-party records

| Item | Purpose | Where stored | Who can access | Retention | Processor |
|---|---|---|---|---|---|
| Support tickets (Ticket: minimally necessary content, status, replies) | Help users | PostgreSQL, or a helpdesk tool (To decide) | User (own); Support, Owner | 90 days after close | Hosting; helpdesk (To decide) |
| Support attachments | Explain a problem | Storage To decide, isolated from app origins (T18) | User (own); Support, Owner | 90 days after close | To decide |
| Diagnostics reports (DiagnosticsReport: user/device, consent time, redacted payload reference, app version, retention expiry) | Debug connection problems, with explicit consent | PostgreSQL plus payload storage (To decide) | Support (linked ticket), Owner | 30 days | Hosting |
| Crash reports (only if adopted; consent recorded as a PrivacyChoice) | Fix crashes | Crash-reporting service | Android engineer, QA, Owner | To decide | Crash reporting (To decide) |
| Play Console data (installs, ratings and reviews, Android vitals) | Store operations; crash and ANR trends | Google Play Console | Owner; Android engineer, QA (Play permissions) | Set by Google (To verify) | Google |
| Beta interest (BetaInterest: email, consent-text version and time, source, confirmed/withdrawn times) | Invite testers before the Play listing | PostgreSQL | Owner; Support (To decide) | Until withdrawn or 90 days after public launch | Hosting; Email |
| Beta feedback and usability-test notes (M02, M09) | Improve the product, with consent | To decide (tickets or a survey tool) | Owner, Designer, QA | To decide | To decide |
| Email delivery records (recipient, template, status, times) | Deliver verification, reset and notice email | Email provider | Owner; Web/backend engineer (staging, time-bound) | Provider-defined (To verify) | Email |
| Abuse and copyright complaints about node IPs (exit IP, time, complaint text) | Answer the provider and complainants | To decide (incident tracker) | Owner; Operator | To decide | Hosting (forwards them) |
| Status incidents (Incident) | Honest public status | PostgreSQL; public status page | Public (sanitized); Operator, Owner (edit) | To decide | Hosting |

## 6. Operations, logs and backups

| Item | Purpose | Where stored | Who can access | Retention | Processor |
|---|---|---|---|---|---|
| API request logs: method, matched route pattern, status, server-generated request ID, time, response time. Never client IP, query string, headers or body (current code) | Operate and debug the API | Host log output (destination To decide) | DevOps/security; Web/backend engineer (staging) | To decide (04's 7 days covers redacted errors only) | Hosting |
| Redacted operational errors (API, workers, agent) | Diagnose failures | Host logs or a log service (To decide) | DevOps/security; Web/backend engineer (staging) | 7 days | Hosting; log service (To decide) |
| **Client source IP addresses in reverse proxy, load balancer, web-hosting and host access logs** | None by default | Default access logs of those components | No one by default | **Disable or minimize** (04). If security logging needs them: document fields, purpose and retention, and update the privacy text before collecting | Hosting; web hosting (To decide) |
| Rate-limit counters (keyed by account, email hash and/or IP; To decide) | Slow down brute force and sign-up abuse | API memory or a short-lived store (To decide) | System | Short expiry (To decide); IP keys only if documented first | Hosting |
| Staff access logs on hosts (SSH: staff IP, key fingerprint) | Secure management access | Host logs | DevOps/security; Owner | To decide | Hosting |
| Node catalog and health (VpnNode: provider reference, region, endpoint, server public key, health, capacity, lifecycle; CPU, network, heartbeat and lease counts) | Run the fleet; publish sanitized locations | PostgreSQL; metrics store (To decide) | Operator, Owner, Read-only; public `/locations` (sanitized) | To decide | Hosting |
| Agent commands (AgentCommand: typed command, desired generation, acknowledgement/error) | Reconcile node state | PostgreSQL | System; Operator, Owner | To decide | Hosting |
| Provider transfer and cost data | Budget and capacity alerts (04) | Provider console; admin Usage & Costs | Owner, Operator | To decide | Hosting |
| Encrypted control-plane backups (every PostgreSQL item above) | Restore after loss | Destination To decide ([environments](../ops/environments.md)) | Owner; DevOps/security (restore) | To decide; must state how deleted accounts age out of backups (04) | Backup storage (To decide) |
| Deletion workflow state and tombstones (deleted account IDs) | Finish deletions; keep deleted users out of restores (M04) | PostgreSQL | System; Support (status), Owner | While any backup still holds the account (To decide) | Hosting |

## 7. Later (M10)

| Item | Purpose | Where stored | Who can access | Retention | Processor |
|---|---|---|---|---|---|
| Subscription (provider token reference, verified purchase state, expiry, linked token, dedupe IDs) | Verify paid entitlements | PostgreSQL | System; Support (state only), Owner | To decide; financial retention exceptions are documented separately (04) | Google Play |

## Logging defaults to check before collection (M01 task 6, M08)

| Component | What to check | Target | Status |
|---|---|---|---|
| API (Fastify) | Request log fields | Method, route pattern, status and request ID only | Done (`api/src/server.ts`) |
| Reverse proxy or TLS terminator (To decide) | Default access log: client IP, full URL, user agent | Off, or a format without IP, query string and user agent | To verify |
| Provider load balancer, firewall and flow logs | Whether client IPs are recorded for our servers | Off where configurable; otherwise disclosed ([provider checklist](../ops/provider-checklist.md) Q10) | To verify |
| Web hosting or CDN for the site (To decide) | Visitor IP logs, built-in analytics | Off or minimized; no third-party analytics by default | To verify |
| journald and syslog on hosts | Retention; which services write | Within the retention above | To verify |
| WireGuard, kernel, connection tracking | Debug and connection logging | Off | To verify |
| DNS resolver | Query logging | Off (03) | To verify |
| Email provider | Open and click tracking, which records reader IPs | Off (proposed) | To verify |

## Never collected

- Browsing history, visited URLs or page content.
- Destination IP addresses or domain names of user traffic, stored or logged. Nodes handle them in memory to route traffic and never write them down.
- DNS query logs.
- Packet payloads or traffic content.
- Advertising identifiers, ad SDKs, cross-app tracking, or any sale or monetization of traffic data.
- Device WireGuard private keys; they stay on the device.
- Plaintext passwords. Staff can never view passwords or private keys.
- Hardware identifiers used for fingerprinting, such as IMEI, serial number or MAC address (01 §3).
- Contacts, SMS, call logs, photos or files (except a file a user chooses to attach to a ticket), precise location, or lists of installed apps (v1 has no split tunneling).
- Client IP addresses in API request logs or in account, session, device, lease or usage records.
- Payment card or bank details. M10 purchases go through Google Play.

Changing this list needs owner approval, an updated privacy text before collection starts, and an update to this inventory (04). If legal review finds that a launch country requires records that conflict with this list, the owner either excludes that country or revises this list and the privacy text before launch. This document is not legal advice.

For the privacy text (01 §2): say that byte counters exist for the quota, that lease records show which account used which node and when, and list processors once they are chosen. Avoid an absolute "no logs" claim.
