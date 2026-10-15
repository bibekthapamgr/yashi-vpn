# Infrastructure

**Not started.** The first infrastructure code arrives with the staging VPN node in M03, after a provider confirms in writing that a public consumer VPN is allowed ([provider checklist](../docs/ops/provider-checklist.md)).

Planned layout:

| Path | Purpose |
|---|---|
| `terraform/` | Provider resources: VPN node, control-plane node, firewalls, backups |
| `ansible/` | Node configuration: WireGuard, forwarding, NAT, DNS resolver, firewall, agent, observability |
| `runbooks/` | Operational runbooks from team pack 04 |

Rules from team pack 03:

- Pin a maintained Ubuntu LTS image. Use SSH keys only, with no open database ports.
- The provider firewall allows the tunnel UDP port and restricted management access only.
- Block VPN clients from cloud metadata, link-local and private management ranges, and from each other, for both IPv4 and IPv6.
- Any DNS resolver answers VPN peers only and has query logging off. Never run an internet-open resolver.
- No credentials in this directory. Provider tokens stay in the approved secret store (see `docs/ops/secrets-inventory.md`).
- Staging and production are fully separate. Production is not created in M01.
