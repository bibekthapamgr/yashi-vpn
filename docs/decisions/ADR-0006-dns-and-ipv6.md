# ADR-0006: DNS and IPv6 strategy

- **Status:** Proposed. Final decision at the M03 gate, backed by leak-test evidence
- **Milestone:** M01 task 7, proven in M03
- **Deciders:** Android lead + DevOps lead

## Decision (proposed)

1. **Full tunnel for both IP versions.** The device routes all IPv4 (`0.0.0.0/0`) and all IPv6 (`::/0`) into the tunnel, so IPv6 cannot escape outside it.
2. **IPv6 egress only when proven.** If the node has working, tested IPv6, it forwards IPv6. If not, the node drops IPv6 from peers. Traffic is blocked, never leaked.
3. **DNS inside the tunnel.** Each node runs its own resolver that listens only on the tunnel interface, answers VPN peers only, validates DNSSEC and has query logging off. The app sets it as the only DNS server for the tunnel. It is never reachable from the internet.
4. **Protected ranges.** Peers cannot reach the cloud metadata service, link-local or private management ranges (IPv4 and IPv6, including ULA), or each other.

## To verify in M03

- No DNS or IPv6 leaks on Wi-Fi and mobile data, across at least three manufacturers.
- Behaviour when the user has Android Private DNS turned on.
- MTU and UDP behaviour on mobile carriers.
- The resolver is not reachable from the public internet.

## Consequences

- Using a third-party DNS service instead would need disclosure and a review of its retention terms (team pack 03).
- If any leak test fails, M04 is blocked (M03 gate).
