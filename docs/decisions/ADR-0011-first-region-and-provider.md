# ADR-0011: First VPN region and provider

- **Status:** Open. Blocked until at least one provider confirms in writing that a public consumer VPN is allowed
- **Milestone:** M01 tasks 1 and 3; the staging node is needed for M03
- **Decider:** Owner

## Constraints

- Launch audience is global (ADR-0001), but v1 starts with **one** live region and the ability to add a second (team pack 00).
- The staging budget is US$20 per month for a VPN node and a control-plane node.
- The provider must confirm public consumer VPN use, UDP, a public IPv4 address (IPv6 if possible), abuse handling and traffic terms. The questions are in [provider-checklist.md](../ops/provider-checklist.md).
- Included traffic and overage prices differ by provider **and by region**. Re-price for the chosen region (team pack 04 v1.1).

## Options for the first region

| Option | For | Against |
|---|---|---|
| Europe | Central for Europe, Africa and the Middle East; some providers include large traffic allowances there | Higher latency for South and East Asia |
| Asia (for example Singapore or India) | Lower latency for South Asia, Southeast Asia and Japan | Traffic allowances and prices may be less generous. Verify per provider |

## Recommendation

Put the first region closest to where most beta testers are. If the early testers are mostly in South Asia, start in Asia; otherwise start in Europe. Add the second region from beta sign-up data and measured latency.

## Next steps

1. Owner sends the checklist email to two or three providers.
2. Record the written answers in `provider-checklist.md`.
3. Accept this ADR with the chosen provider and region, then rent only the staging resources (M01 task 3, M03 task 1).
