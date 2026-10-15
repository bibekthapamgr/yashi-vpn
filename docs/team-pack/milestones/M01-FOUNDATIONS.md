# M01 — Decisions, procurement checks and engineering foundations

Owner: product lead + technical lead. Dependencies: none. Estimated effort: 3–5 working days, excluding account verification.

## Goal

Approve a feasible product scope and establish an auditable build process before renting a fleet or designing unsupported promises.

## Tasks in order

1. Record owner decisions: proposed 5 GiB free quota, one device, signup required, Android-only, no ads, launch countries/audience, first VPN region(s) chosen for target-user latency and provider traffic terms, support hours and monthly spend ceiling. Default values in pack are proposals pending signoff.
2. Decide brand clearance route. Check trademark registries in actual markets, registrar/Play name availability and handles. Yashi is the owner-selected name; trademark and domain checks remain pending. Keep `APP_DISPLAY_NAME` configurable and domain placeholders uncommitted to production identity.
3. Confirm provider permissions for public free VPN operation, root/sudo control, UDP, public IPv4, bandwidth terms, abuse handling, regions, billing and account verification. Rent only approved development resources. Choose one VPN node and separate control-plane node, not ten locations.
4. Start Google Play Organization verification in owner's company account. Request the company's D-U-N-S number first if it has none; Google requires it for organization accounts and issuance can take up to 30 days. Confirm signing/recovery ownership; do not create accounts to evade restrictions.
5. Create monorepo/module boundaries and protected branches; assign reviewers. Setup CI smoke builds, tests, lint, secret/dependency scanning and infrastructure checks. Write build/release commands.
6. Establish staging/prod isolation, secrets inventory, backup destinations, access matrix, threat-model draft and data inventory. Review actual host/proxy logging defaults.
7. Draft OpenAPI, entity diagrams and decision records for architecture, min Android, account flow, lease/quota enforcement, DNS/IPv6 strategy, control-plane availability, node-agent language/runtime, transactional email provider and privacy-reviewed crash reporting/analytics. Identify technical uncertainties for M03.

## UI deliverables

Approved screen inventory from brand spec; navigation diagram; content hierarchy; first-time signup-to-connect and returning-user flow. Designer confirms error/offline/permission variants are included before high-fidelity work.

## Acceptance tests / gate

- Clean clone builds a starter Android app, web shell and API health test via documented CI commands.
- No committed secrets; team can access staging under individual permissions; database not public.
- Owner-approved scope/budget and unresolved decisions list exists. Provider permission is documented or procurement remains explicitly blocked.
- One approved threat/data inventory and versioned API draft reviewed by Android/backend/ops leads.

## Handoff evidence

Repo/CI links, decision records, access inventory (no credentials), provider configuration checklist, Play verification status and owner signoff. M02/M03 may proceed together once scope/contracts are agreed. Do not deploy publicly at this gate.
