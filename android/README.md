# Android app

Kotlin + Jetpack Compose, built with AGP 9.4 (built-in Kotlin), Gradle 9.6 and the Compose BOM 2026.09.00. Versions live in `gradle/libs.versions.toml`; see ADR-0002 and ADR-0003.

**M01 scope:** a labelled starter screen with the Quiet Orbit color tokens and a unit test that checks their text contrast. There is no VPN service, WireGuard library or network code yet; that is M03.

```bash
./gradlew assembleDebug testDebugUnitTest lintDebug
```

| Setting | Value | Why |
|---|---|---|
| `minSdk` | 29 (Android 10) | Pack proposal, subject to audience testing (ADR-0003) |
| `targetSdk` | 36 | Google Play's requirement for new apps since 31 Aug 2026. Recheck at submission |
| `compileSdk` | 37 | Required by current Compose libraries |
| `applicationId` | `com.example.yashivpn` | Placeholder until brand and domain clearance (ADR-0012). Play rejects `com.example`, which is intended |
| Display name | Gradle property `APP_DISPLAY_NAME` (default "Yashi VPN") | Rename without code changes if clearance fails |
| Backups | Off; all domains excluded from cloud backup and device transfer | The WireGuard private key must never leave the device (M03) |

Signing keys are never stored in this repository. The owner controls Play App Signing and upload keys (team pack 01 §4, M09).
