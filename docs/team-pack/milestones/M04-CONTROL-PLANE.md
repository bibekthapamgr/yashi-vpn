# M04 — Accounts, devices, leases and server-enforced quota

Owner: backend + node-agent engineers. Depends: M03, approved contracts. Estimated effort: 7–12 working days.

## Goal

Turn the manual tunnel proof into a secure managed service with enforceable account policy.

## Tasks in order

1. Implement schema/migrations and indexed entities from architecture document. Build email verification/login/reset, token rotation, rate limiting and secure password storage.
2. Implement user/device ownership and one-device default enforcement in transactional operations. Private keys never enter API.
3. Build authenticated per-node agent, typed peer grant/revoke and ready acknowledgement; desired/actual states reconciled. Keep management channel separate from tunnel endpoint.
4. Implement lease API with idempotency, assigned address uniqueness, renewal and enforced expiry. Add suspension/deletion/device revoke flows and pending-error status.
5. Implement persistent usage accounting, atomic account-wide reservations, local node enforcement and UTC monthly periods. Preserve epochs across counter reset/restart; dedupe retries. Protect switching/concurrent requests from quota overspend.
6. Implement catalog eligibility/health filtering and usage/reset/entitlement API. Proposed free limits configurable as versioned plans, not arbitrary client flags.
7. Create integration fixtures, OpenAPI contract tests, staging admin seed via secure process and account deletion worker with restore tombstones.

## Required adversarial tests

Parallel grant requests; cloned modified app; reinstall; client clock rollback; stale config after revoke; node reboot; agent/backend partition; duplicate counter samples; quota exhaustion while app killed; reset boundary; switch before old node acknowledges revoke; login/API rate-limit attempts and cross-user object IDs.

## Acceptance tests / gate

- Quota/device policy enforced server-side; stale config cannot bypass expiry/revocation.
- Reachable node revoke <=60 seconds; isolated access ends by enforced hard lease expiry. Failures visibly pending, not falsely complete.
- Quota overshoot meets measured bound (proposed <=16 MiB per account/period) and no double count on retries/reboot.
- No new lease when policy/control authorization cannot be validated. Existing access respects finite lease/reservation.
- API fixtures/contracts pass for Android/web consumers; cross-user requests denied.

## Handoff evidence

OpenAPI, migrations/rollback, entity dictionary, tested concurrency/partition traces, secret-redaction report and threat-model update. M05/M06/M07 proceed against stable contracts; no live billing yet.
