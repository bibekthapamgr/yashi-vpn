# ADR-0003: Android minimum, target and compile SDK

- **Status:** Proposed, pending tech lead and owner review
- **Milestone:** M01 task 7
- **Decider:** Tech lead + owner

## Decision

| Setting | Value | Reason |
|---|---|---|
| `minSdk` | 29 (Android 10) | Team pack 04 proposal, subject to audience testing |
| `targetSdk` | 36 (Android 16) | Google Play has required API 36 for new apps and updates since 31 Aug 2026. API 37 is expected to be next |
| `compileSdk` | 37 | Current Compose libraries require it |

## Consequences

- A global audience may include many older phones. Revisit `minSdk` once beta sign-ups show the device mix; lowering it means testing VPN behaviour on those versions on real devices (team pack 04).
- Never freeze `targetSdk` from this record. Recheck the Play requirement at every submission (team pack 01, M09).
- Physical test devices must cover the oldest supported, an intermediate and the current Android version across at least three manufacturers (team pack 04).
