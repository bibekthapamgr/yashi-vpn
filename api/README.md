# API (control plane)

Node.js + TypeScript + Fastify. One modular service with background workers, per team pack 03 ("do not add distributed infrastructure without a measured need").

**M01 scope:** `GET /v1/health` and the shared error contract (`code`, `message`, `requestId`, optional `retryAfter`). Everything else in `contracts/openapi.yaml` is a draft for M04.

| Command | Purpose |
|---|---|
| `npm run dev -w api` | Run locally with reload (http://localhost:8080/v1/health) |
| `npm test -w api` | Unit tests |
| `npm run build -w api` | Compile to `dist/` |

Privacy defaults already in place: request IDs are generated server-side, responses are `no-store`, and request logs record only the method, the matched route pattern and the status. They never record client IPs, query strings, headers or bodies.
