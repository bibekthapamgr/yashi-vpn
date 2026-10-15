# Yashi VPN — Development Team Pack

Version 1.1 • 15 October 2026 • Android first • Free launch, paid expansion later

## Product decision

Build a trustworthy Android VPN with one-tap connection, transparent usage limits, a professional public website, a customer account portal, and a separate operations/admin portal. Use an established WireGuard implementation; do not invent cryptography. This is a product specification, not a finished application or security certification.

Owner-selected brand: **Yashi VPN**. Pronunciation: “Ya-shi”. Suggested tagline: **Your connection. Clearly protected.** Name selected by the owner: Yashi VPN. Trademark clearance and domain availability have not been verified for this name. Owner must verify relevant trademarks, Play Store names, domains and handles before buying branding or fixing the production package ID. No domain has been purchased or confirmed available. Rename all documents/tokens consistently if clearance fails.

## Read in this order

1. `01-TEAM-INSTRUCTIONS.md` — mandatory instructions for everyone.
2. `02-BRAND-UI-UX.md` — tokens, layouts, screens, interaction rules.
3. `03-ARCHITECTURE-AND-CONTRACTS.md` — platform boundaries, entities, API and security.
4. `04-QA-RELEASE-AND-COSTS.md` — test matrix, launch blockers and operating budget. Read before M01: its budget and Play checklist feed M01 decisions.
5. `05-COMPETITORS-AND-SOURCES.md` — research, limits of comparison, official references.
6. `milestones/M01` through `M10` — deliver as sequential gates; do not claim completion without evidence.

## Release boundaries

**Free v1:** M01–M09. Android, WireGuard, account/device management, one live region initially with ability to add a second, quota enforcement, reconnect, DNS/IPv6 safety, OS lockdown guidance, public site, customer portal, admin operations, support/deletion/status pages.

**Paid v1.1:** M10 only after owner approval and unit-economics review. Google Play subscriptions, premium entitlements, more locations/devices as capacity permits. Premium infrastructure is not automatically purchased by this plan.

Default proposed free plan: 5 GiB/account/month, 1 enrolled device, no ads at launch. Owner can change this before beta. Display the actual limit and UTC reset date before signup/connect; no credit card required. Reserve quota server-side and stop access when exhausted. Do not silently turn a promised free plan into a paid-only service later.

Out of scope for v1: iOS/desktop clients, residential IPs, dedicated user IPs, multi-hop, custom encryption, antivirus, password manager, GPS spoofing, obfuscation, port forwarding, torrent/streaming guarantees, reseller platform, referral rewards and ad SDKs. Android split tunneling is post-launch unless a separate tested scope is approved.

## Proposed team allocation

Owner/product lead approves scope, brand, provider, privacy text, budget and releases. UX designer owns Figma/design tokens. Android engineer owns native VPN integration. Web/backend engineer owns API/portals. DevOps/security engineer owns node lifecycle, privacy controls and monitoring. QA owns repeatable evidence and release gates. People may cover multiple roles, but security-sensitive changes require a second reviewer. These are human team roles, not authorization to send messages or invite anyone.

## Timeline (planning range, not a commitment)

For an experienced team covering these roles: allow roughly 10–16 calendar weeks to a careful free beta/public release, plus store review/verification and procurement delays. Paid expansion adds roughly 2–4 weeks after free operations are stable. Team lead must re-estimate after M01 and M04. Dependencies can overlap only after upstream contracts are agreed; security/release gates cannot be skipped.

## Milestone map

| Gate | Outcome | Depends on | Indicative effort |
|---|---|---|---|
| M01 | Decisions, legal/provider checks, repo/CI foundations | Owner kickoff | 3–5 working days |
| M02 | Approved complete UI system and clickable flows | M01 | 5–8 days |
| M03 | Real phone-to-server tunnel proof | M01; M02 can proceed alongside | 3–5 days |
| M04 | Auth, enrollment, entitlements and enforced quotas | M03 + approved contracts | 7–12 days |
| M05 | Production Android connection experience | M02, M04 | 10–15 days |
| M06 | Public website and customer portal | M02, M04 | 6–10 days |
| M07 | Admin and fleet operations | M04 + approved M02 admin design | 7–10 days |
| M08 | Security, load, privacy and resilience verification | M05–M07 | 7–12 days |
| M09 | Beta, Play review and controlled free launch | M08 | 7–14 days plus external wait |
| M10 | Verified paid subscriptions and premium rollout | Stable M09 + owner approval | 10–20 days |

Effort ranges overlap across team members and should not be summed into a fixed calendar promise. Every milestone file includes tasks, testable acceptance criteria and handoff evidence.

## Message to forward with this pack

Team: use this pack as the single baseline for Yashi VPN. Read documents 01–05 in order (rules, UI/UX, architecture, QA/release/costs, competitors) before starting M01. Deliver real, tested functionality, not just screens. Keep v1 free with transparent limits; premium is a separate gate. Report completed work with build/commit, tests, screenshots, known issues and rollback instructions. Escalate security/privacy/provider questions before implementation. Do not claim “no logs”, “fastest”, “unlimited” or “fully anonymous” without substantiation. No production purchase/deployment occurs without the owner's approval.

## Change log

**1.1 — 15 October 2026**

- Reading order: 04 and 05 now come before the milestones, because M01 needs 04's budget and Play checklist.
- Milestone map dependencies now match the milestone files (M04, M07).
- Logo brief uses a Y for Yashi instead of a leftover T (02).
- Every app state has a defined status-pill label (02, State contract).
- Added missing entities and endpoints: admin users, verification/reset tokens, sessions, privacy choices, diagnostics and beta interest (03), with retention proposals (04).
- Added a control-plane availability requirement and restore gate (03, 04).
- M01 now records first region, email provider, crash reporting, node-agent runtime and availability decisions, plus the D-U-N-S number step (M01, 04).
