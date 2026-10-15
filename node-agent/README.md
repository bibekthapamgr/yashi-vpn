# Node agent

**Not started.** The runtime decision is open in [ADR-0007](../docs/decisions/ADR-0007-node-agent-runtime.md); implementation starts in M04.

The agent runs on each VPN node and is the only component that changes WireGuard peers and firewall rules there. Requirements from team pack 03 and M04:

- Typed commands only (grant peer, revoke peer, report usage, drain). No arbitrary shell, and user data is never interpolated into commands.
- Authenticated, encrypted control link with a per-node certificate or short-lived credential that can be rotated and revoked independently. No public admin API.
- Root only for bootstrap; runtime keeps only the capabilities needed for WireGuard and firewall changes.
- Usage counters are sampled at a bounded interval, journaled durably with an epoch and sequence, and sent as idempotent deltas. Uncertain state after a restart fails closed for new allocations.
- Enforces lease expiry and the node's quota reservation locally, so access ends even when the control plane is unreachable.
- Never logs traffic destinations, DNS queries or packet payloads.
