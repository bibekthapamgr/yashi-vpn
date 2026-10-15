# M10 — Paid subscriptions and controlled expansion (later)

Owner: product lead + Android/backend/QA. Depends: stable M09 + explicit owner approval. Estimated effort: 10–20 working days.

## Goal

Add commercially viable premium access while keeping verified free functionality and privacy intact. Downloads alone do not trigger paid rollout.

## Entry gate

Review at least one meaningful usage/cost observation period. Owner approves server budget, payment eligibility, subscription terms/refunds and margin model. Net revenue minus store fees/tax, bandwidth, support and infrastructure must be modeled. Do not assume all free users convert or ads pay for traffic.

## Tasks in order

1. Decide paid quota/device/region limits based on measured capacity; avoid “unlimited” without a sustainable policy and infrastructure. Keep the existing free tier clearly separate.
2. Implement Google Play Billing using current supported library/policy. Default to Play subscriptions for Android digital service purchases; alternative billing/external links need separate policy/region eligibility review. No Khalti/website workaround embedded by default.
3. Implement purchase verification via secure backend, acknowledgement, RTDN processing/deduplication, API re-query and scheduled reconciliation. Store sensitive provider references securely. Never trust local premium state.
4. Model pending, active, grace, on-hold, canceled-but-valid, expired, refunded/revoked, upgraded/downgraded and restored purchases correctly. Cancellation does not automatically equal immediate expiry. Protect token replay/account reassignment.
5. Build premium screen with actual localized Play prices, billing interval, auto-renew/trial/renewal terms, restore and manage/cancel links. No preselected trial dark patterns. Feature flag until tested and approved.
6. Extend customer portal with verified plan/subscription status and correct purchase management; no fake invoice/payment history. Admin sees state and diagnostic references, not full tokens/card details. Any manual grant is time-bound, permissioned and audited.
7. Add tested premium node capacity/locations, transactional multiple-device policy and account-wide quota rules. Re-run security, leak, switch and load tests. Preserve free quality/security.

## Acceptance tests / gate

Sandbox/license tests exercise all lifecycle cases, duplicate/delayed notifications, offline app, server downtime, refunds and purchase restore on another device. Entitlement matches verified Play state; replay cannot grant another account premium. Purchase metadata uses actual price/terms. Free flow remains usable with no forced upgrade. New server load fits budget. Legal/policy review signed and owner authorizes paid feature flag.

## Handoff evidence

Billing state table, verified event traces redacted, sandbox test results, pricing/margin worksheet, plan configuration versions, feature-flag rollback plan and release approval. Future iOS, split tunneling, additional protocols or independent audits are separate scoped milestones, not implied complete by M10.
