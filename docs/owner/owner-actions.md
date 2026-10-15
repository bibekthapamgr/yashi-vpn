# Owner actions

Things only the owner can do. Updated 15 Oct 2026. Start the first three now, because each one has a waiting period.

| # | Action | Why | Status |
|---|---|---|---|
| 1 | **Request a D-U-N-S number** for the company that will publish the app | Google requires it for a Play organization account, and issuance can take up to 30 days | To do now |
| 2 | **Ask providers for written VPN permission.** Send the email in [provider-checklist.md](../ops/provider-checklist.md) to two or three providers | Procurement is blocked until a provider says yes in writing (team pack 05, M01 task 3) | To do now |
| 3 | **Check the name "Yashi VPN"**: trademarks, domain, Play Store name, social handles | The name and package ID become permanent once published (ADR-0012) | To do now |
| 4 | Create the Google Play Console **organization** account after the D-U-N-S number arrives | Owner keeps the account, signing and recovery (team pack 01 §4) | After #1 |
| 5 | Protect the `main` branch on GitHub | Required by M01. Protected branches on private repositories need GitHub Pro (personal account) or a paid organization plan | To do |
| 6 | Turn on two-factor authentication for GitHub, Google and every provider account | Mandatory MFA (team pack 01 §2) | To do |
| 7 | Find a lawyer for the launch review | Global audience: VPN rules by country, sanctions and export rules for the publishing company, privacy law | Before M09 |
| 8 | Decide support hours and the support channel | ADR-0001 open item | Before M06 |
| 9 | Set billing alerts at 50%, 75% and 90% of US$20 when the provider account opens | Alerts are not hard spend caps (team pack 04) | When #2 is done |

## How to

**1. D-U-N-S number.** Google's Play Console help page *Required information to create a Play Console developer account* links to Dun & Bradstreet's request page. Use the company's exact legal name and address as registered, because Google matches them during verification.

**3. Name checks.** Search trademark databases where you plan to launch: WIPO Global Brand Database (international), USPTO (US), EUIPO (EU), IP India, J-PlatPat (Japan) and Nepal's Department of Industry. Then check domain availability, existing Play Store apps named "Yashi" and the social handles. Buy the domain only after these checks.

**5. Branch protection** (Settings → Branches or Rules on the repository): require a pull request, require the CI checks *Web, API and contract*, *Android build, unit tests and lint* and *Secret scan* to pass, and block force pushes and deletion. Add "require 1 approval" once a second person joins.
