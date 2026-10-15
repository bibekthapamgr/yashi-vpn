# ADR-0001: Owner decisions for the free v1 scope

- **Status:** Accepted in part by the owner on 15 Oct 2026. Open items below.
- **Milestone:** M01 task 1
- **Decider:** Owner / product lead

## Context

M01 needs signed-off scope and budget before anything is rented or designed (team pack 00, M01). The pack's defaults are proposals until the owner accepts them.

## Decision

| Topic | Decision |
|---|---|
| Free plan | 5 GiB per account per calendar month (period starts 00:00 UTC on the 1st), 1 enrolled device, signup required, no card, no ads |
| Platform | Android only for v1 |
| Launch audience | Global, minus countries excluded after legal review |
| Development and staging spend ceiling | US$20 per month of provider bills. Domain, Play registration and labour are separate |
| Source hosting | Private GitHub repository `bibekthapamgr/yashi-vpn`, owned by the owner |

## Open items (owner)

| Item | Needed by |
|---|---|
| Support hours and support channel | Before M06 |
| Country exclusion list, from legal review | Before M09 |
| First VPN region and provider ([ADR-0011](ADR-0011-first-region-and-provider.md)) | Before M03 |
| Production monthly budget | Before M09 |
| Legal entity that publishes on Google Play (needs a D-U-N-S number) | Start now |

## Consequences

- US$20 per month allows one small VPN node and one small control-plane node for staging, if a permitted provider fits. If not, procurement stays blocked rather than exceeding the ceiling.
- A global audience widens the legal review: VPN rules differ by country, and the publishing company's own jurisdiction may impose sanctions or export rules. Google Play lets the owner exclude countries. This is a launch gate, not legal advice.
- The free plan can still change before beta (team pack 00); any change is a new ADR.
