# ADR-0005: Lease and quota enforcement

- **Status:** Proposed, parameters to be tuned in M04 and M08 tests
- **Milestone:** M01 task 7, implemented in M04
- **Deciders:** Backend lead + DevOps lead

## Decision (proposed, from team pack 03)

- **Leases:** server-enforced, renewed every 5 minutes, with a 15-minute hard lifetime. A node removes a peer when its lease expires, even if the control plane is unreachable.
- **Quota:** account-wide. The backend hands each active node a bounded byte reservation in one transaction, and outstanding plus consumed bytes never exceed the allowance. The node enforces its reservation locally and cuts access when it is spent.
- **Usage accounting:** WireGuard peer RX+TX counts as product usage. The agent journals counters with an epoch and a sequence, and sends idempotent deltas so retries and restarts never double count. Recreating a peer or switching region never erases usage.
- **Returns:** unused reservation bytes return only after the old peer is actually revoked or its lease has expired, not when an app disconnect request fails.
- **Failure mode:** when authorization cannot be checked, no new grants are made. Existing peers continue only within their unexpired lease and reservation.
- **Tolerance:** packet and accounting overshoot is bounded and measured. The proposed target is ≤16 MiB per account per period.

## To tune with evidence

| Parameter | Starting point | Tuned in |
|---|---|---|
| Reservation size per grant | Small enough that overshoot stays under the tolerance, large enough to avoid renewal storms | M04 load tests |
| Counter sampling interval | Bounded; trade-off between overshoot and agent load | M04 |
| Renewal and hard lifetime | 5 and 15 minutes | M08 load and resilience tests |

## Consequences

- The control plane must recover within the lease margin ([ADR-0010](ADR-0010-control-plane-availability.md)).
- Adversarial tests in M04 cover parallel grants, a cloned app, reinstall, clock rollback, stale configs, node reboot, partitions, duplicate samples and switching before a revoke is acknowledged.
