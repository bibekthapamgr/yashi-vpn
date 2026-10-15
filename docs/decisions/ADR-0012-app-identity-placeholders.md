# ADR-0012: App identity placeholders until clearance

- **Status:** Accepted on 15 Oct 2026 (team pack 00 and M01 task 2)
- **Decider:** Owner

## Context

"Yashi VPN" is the owner's chosen name, but trademark, domain, Play Store name and social handle checks have not been done. The production package ID cannot be changed after the first Play release.

## Decision

| Item | Placeholder now | Final value |
|---|---|---|
| Android `applicationId` and namespace | `com.example.yashivpn` (Google Play rejects `com.example`, so it cannot ship by accident) | Reverse domain of the purchased domain, set before the first Play upload (M09) |
| App display name | Gradle property `APP_DISPLAY_NAME`, default "Yashi VPN" | Confirmed name after clearance |
| Domains in code and docs | `example.com` and `api.example.com` | The purchased domain |

## Consequences

- If clearance fails, rename consistently through the documents, design tokens and the `APP_DISPLAY_NAME` property, with no code changes needed for the name.
- Signing keys and the Play account are created only for the final identity, under owner control.
