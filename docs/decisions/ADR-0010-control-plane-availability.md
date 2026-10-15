# ADR-0010: Control-plane availability

- **Status:** Proposed. Production design is finalised in M07 and M08
- **Milestone:** M01 task 7
- **Deciders:** DevOps lead + owner (budget)

## Context

Leases renew every 5 minutes and hard-expire after 15 minutes ([ADR-0005](ADR-0005-lease-and-quota-enforcement.md)). In the worst case a peer has about 10 minutes left when renewal stops (hard lifetime minus renewal interval). If the API or database is down longer than that, **every connected user is disconnected** (team pack 03 v1.1).

## Decision (proposed)

| Environment | Setup | Recovery target |
|---|---|---|
| Staging (M03–M08) | One small control-plane node; encrypted nightly database backups to a separate location; restore tested monthly | Best effort; restore drills are timed |
| Production (M09+) | At least two API instances or a warm standby, plus a replicated or managed PostgreSQL | Failover or restore within 10 minutes, without mass disconnection |

Other rules:

- Maintenance is rolling or shorter than the lease margin.
- Alert on lease-renewal error rates, not just on uptime.
- After a restore, reconcile the usage ledger against the node agents' journals so restored data never re-grants quota that was already used.

## Consequences

- Production costs more than "one small control node". The owner approves the production budget before M09 (ADR-0001).
- The M08 go/no-go gate includes a timed failover or restore drill (team pack 04 v1.1).
