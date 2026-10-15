# QA, release and operating controls

## Minimum verification matrix

| Area | Cases | Evidence / pass condition |
|---|---|---|
| Connection | Consent accept/deny, cancel, handshake fail, endpoint blocked, timeout | Correct state and recovery; no premature Connected |
| Routing | IPv4/IPv6, DNS, app-controlled exit check, MTU-heavy transfers | No unintended full-tunnel leaks; documented exceptions |
| Android lifecycle | Process kill, background, screen off, battery saver, reboot, app update | Actual service and UI reconcile; no false persistence claims |
| Networks | Wi-Fi/mobile transitions, airplane mode, offline recovery, captive portal | No retry storm; honest states and lockdown behavior |
| Authorization | Expiry, suspension, signout, revoke, deletion, stale configs | Node actually rejects access within defined SLA |
| Quota | Concurrent grants, switch, reinstall, reset boundary, counter reset, duplicate reports | No reset/bypass/double counting; bounded overshoot |
| Admin | MFA, each role, CSRF, IDOR, injection, audit redaction | Unauthorized requests fail server-side |
| Resilience | API/DB down, node/agent restart, heartbeat loss, provider outage | Bound existing access; fail closed new access; recover cleanly |
| Privacy | Logs/backups/crash payload/DNS/host settings inspected | Published data claims match actual configuration |
| UI | 360–430 dp, 200% fonts, TalkBack, themes, keyboard, reduced motion | No clipped actions; state readable without color |
| Paid later | Pending, decline, cancel, expiry, hold, grace, refund, replay, restore | Verified entitlement reflects Play state |

Use physical devices from at least three OEM families (e.g. Pixel/Samsung/Xiaomi) across oldest supported, intermediate and current supported Android. Proposed min Android 10, subject to audience testing; target current Play requirement. Emulators supplement but do not replace real network/battery tests. Test real Nepal/Japan/target-market networks where volunteers are available; record gaps instead of inventing results.

## Proposed launch SLOs and go/no-go gates

These are targets for the team to validate, not product performance claims.

- Connection success >=98% in at least 200 controlled attempts across the agreed network/device matrix, excluding explicitly documented unsupported/captive-portal conditions. Report exclusions and denominator.
- Warm connect p95 <=5 seconds on healthy agreed test networks; cold permission/auth flow measured separately.
- Revocation on a reachable healthy node <=60 seconds; partitions bounded by enforced hard lease lifetime (initial proposal 15 minutes). Do not promise instant revocation during node/control outages.
- No unresolved critical/high exploitable security issues, confirmed DNS/IPv6 leaks, or quota bypass. All lower-severity issues documented/approved.
- No connection-blocking crash in release matrix; beta crash-free session target >=99.5% with sufficient measured sample and privacy-approved crash reporting. Low sample size is reported, not rounded into confidence.
- At least 5-person usability check meets design brief. Backup restore, node rebuild, incident and rollback drills completed.
- Control-plane failover/restore drill finishes inside the worst-case lease margin (10 minutes with the initial lease proposal) without disconnecting existing users.

## Cost planning (not a provider quote)

Prototype: one VPN node + one small control/API node, self-managed DB, basic backups. Planning allowance $20–40/month plus domain/Play registration, excluding labor, marketing, tax, overage and security review. Two-region small public launch: reserve $60–120/month, including the control-plane standby/backup needed for the restore gate, until measured usage supports a more accurate forecast. Annual commitments are not recommended before validation. Included traffic, overage and prices can differ by provider and region; re-price for the region(s) chosen in M01 instead of assuming an EU allowance.

At 1,000 monthly active users using 5 GiB each, product usage is about 5,000 GiB/month; host egress and overhead differ and must be measured. Downloads are not equivalent to active users or concurrent tunnels. A 20 TB allowance says nothing by itself about CPU throughput or peak simultaneous users.

Track daily: included/remaining provider transfer, projected bill, active leases, measured peak throughput, support burden, quota usage distribution and actual net revenue later. Set alerts at 50/75/90% of transfer/budget thresholds; verify provider notifications are not a hard spend cap. When capacity is low, stop new enrollments/grants transparently rather than creating uncontrolled spend. No unlimited autoscaling without a budget ceiling and owner approval.

## Pre-release policy/account checklist

Verify current Google requirements at submission: Organization account and verification (company D-U-N-S number), VpnService declaration, foreground service behavior, target SDK, privacy URL, Data safety, account deletion in app and via web, app access for reviewer, ratings/content declarations and billing rules when paid. Supply current required demo/video evidence in the format requested by Play; do not assume an outdated tutorial is sufficient. Never work around an account enforcement restriction by creating evasive accounts.

Legal review for the company and launch countries must cover VPN provision restrictions, user-data obligations, acceptable use, abuse handling, privacy processors and later subscriptions/refunds. This is a launch gate, not legal advice.

## Operations and incident runbooks

Prepare runbooks for: node down/drain/replace; leaked node key; leaked provider token; compromised admin; DNS failure; runaway transfer; quota ledger corruption; account abuse; stalled revocation; Play rejection; bad APK release; database restore. Include named human owner/on-call coverage, severity, safe steps, user communications, rollback and postmortem template.

Default privacy retention proposals for owner/legal approval: quota aggregates current + previous monthly period; redacted operational errors 7 days; admin audit 90 days; support content 90 days after close; opt-in diagnostics reports 30 days; beta-interest contacts until withdrawn or 90 days after public launch. Security exceptions, backups and legal retention must be explicitly described. Do not silently store client source IPs in reverse proxy/host access logs; inspect and disable or minimize them. For necessary security logging, document fields/purpose/retention and update privacy text before collection.

Delete workflow revokes sessions/peers promptly, deletes or anonymizes account-associated data on a documented schedule, excludes deleted identities from restore replay and explains backup expiration. M10 financial retention exceptions are documented separately. User-facing deletion must not falsely imply cancellation of a Play subscription; provide explicit management guidance.
