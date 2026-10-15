# M07 — Professional admin and fleet operations

Owner: web/backend/DevOps engineers. Depends: M04 + approved M02 admin design. Estimated effort: 7–10 working days.

## Goal

Operate a safe small VPN network without direct database edits, shared passwords or shell commands in a browser.

## Tasks in order

1. Build separate admin authentication, MFA, roles (Owner, Operator, Support, Read-only) and backend authorization matrix. MFA recovery procedure owner-controlled and audited.
2. Implement Overview, Users, Devices, VPN Nodes, Usage & Costs, Plans, Support, Incidents, Audit and Settings. Include filters/pagination, empty/loading/error/stale states and last-updated labels.
3. Implement user suspend/reactivate, device revoke and deletion workflow; reason/step-up confirmation and idempotency for sensitive operations. No password/private-key viewer or silent impersonation.
4. Node lifecycle: provision-approved configuration → validate → ready → drain → offline/replace. Restrict commands and show actual agent acknowledgements. Never expose cloud credentials to browser.
5. Display CPU/network/lease/heartbeat health, allowance remaining and projected cost. Mark estimates as estimates; host bills are separate from app quota counts. Set cost and capacity alerts.
6. Build versioned plan configuration/approval, enrollment gate, support responses, published incident workflow and masked-user search for limited roles.
7. Audit sensitive admin actions without traffic metadata. Test database restore, node rebuild/drain and certificate/token rotation runbooks.

## Role baseline

Owner: roles/provider settings/plans/budget/release decisions. Operator: node drain/maintenance and investigated access revocation; no owner-role grants. Support: user support and limited account state with minimal visibility; no fleet secrets or plan grants. Read-only: sanitized metrics/audit as permitted. Enforce independently on every endpoint; UI hiding is not authorization.

## Acceptance tests / gate

MFA and all role/IDOR tests pass. Draining excludes node from new grants, confirms existing lease handling and never reports complete prematurely. User revoke propagates correctly. Metrics and costs use real counters/timestamps, not random fixtures. Destructive actions require reason and audit. Operator can follow documented outage recovery without ad hoc privileged commands in UI.

## Handoff evidence

Admin staging access instructions (credentials delivered separately), role matrix test results, screenshots, audit samples redacted, alert drill and node/backup recovery records. Owner approves production operators and support scope.
