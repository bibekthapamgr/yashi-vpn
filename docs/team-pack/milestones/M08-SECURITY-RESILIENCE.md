# M08 — Security, privacy, load and resilience gate

Owner: security reviewer + QA + DevOps. Depends: M05–M07. Estimated effort: 7–12 working days.

## Goal

Prove the system is safe enough for beta under measured load and failure, not merely visually finished.

## Tasks in order

1. Execute full QA matrix and threat model; independent second reviewer examines key/auth boundaries, node agent permissions and privacy claims.
2. Inspect production-like reverse proxy, application, WireGuard, DNS, provider logging, crash reports and backups. Remove unexpected identifiers/payloads. Compare actual data to policy line by line.
3. Run DNS/IPv6/metadata/private-range/client-isolation tests on multiple real devices/networks. Test kill/lockdown behavior without claiming Android capabilities the app lacks.
4. Attack authorization/quota paths in staging: parallel enrollment/grants, stale configs, replay, counter reset, node partition, switch, clock manipulation and deletion.
5. Load test controlled staging endpoints, not public third-party services. Start at expected pilot concurrency and increase until measured safe limits found. Record CPU, throughput, loss, latency, connect success, agent load and estimated transfer cost. Set enrollment/lease cap with headroom.
6. Drill node/agent restart, DB/API loss, DNS failure, certificate rotation, backup restore, bad deploy rollback and corrupted/uncertain quota state.
7. Run UI regression/accessibility, privacy opt-out, support/deletion and admin audit tests. Fix blockers and rerun affected cases.

## Acceptance tests / gate

Zero open critical/high exploitable findings, leaks or quota bypass. Proposed connection/revocation targets in QA document measured with denominator/context. Defined quota overshoot bound passes; pending revocation never disguised as success. Finite offline lease limits verified. Restore drill and runbook signed off. Privacy/data retention and all claims approved. Capacity cap based on tests rather than VM RAM or advertised traffic alone.

## Handoff evidence

Versioned security findings with remediation, raw redacted test summaries, device/network matrix, load graphs, safe concurrency limit, retention verification, backup/rollback results and signed go/no-go. External penetration review is strongly recommended and separately budgeted; if not performed, disclose that limitation. Do not claim “audited” from an internal checklist alone.
