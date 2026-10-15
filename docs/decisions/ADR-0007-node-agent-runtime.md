# ADR-0007: Node agent language and runtime

- **Status:** Proposed. Decide before M04 starts
- **Milestone:** M01 task 7
- **Decider:** Tech lead

## Context

The agent runs on every VPN node with the privileges needed for WireGuard and firewall changes. It must be small, auditable, durable across restarts and safe to run with minimal capabilities (team pack 03).

## Options

| Option | For | Against |
|---|---|---|
| **Go** | Single static binary with no runtime on the node; small memory footprint; a mature WireGuard control library (`wgctrl`); easy to sandbox under systemd | A second language in the monorepo |
| **TypeScript on Node.js** | Same language as the API, so contract types can be shared | Needs a Node.js runtime and npm dependencies on every node, which means a larger attack surface and more patching |
| **Rust** | Memory safety and a small binary | Steeper learning curve for a small team |

## Recommendation

**Go**, unless no one on the team can review Go code, in which case TypeScript with a minimal dependency set. Contract types come from `contracts/openapi.yaml` either way.

## Consequences

- CI gains a Go build, test and vet job when the agent starts in M04.
- Agent binaries are built in CI and signed or checksummed before deployment.
