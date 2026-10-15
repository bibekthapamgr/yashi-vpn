# Contracts

`openapi.yaml` is the source of truth for the `/v1` API (team pack 03). Android, web and node-agent code is written against it, and contract tests check the implementation from M04.

- Each operation has `x-milestone`, the milestone in which it is implemented. Only `GET /v1/health` exists in M01.
- Lint: `npm run contracts:lint` (Redocly, rules in `redocly.yaml`).
- Any change to a published contract needs review by the Android, backend and ops leads.
