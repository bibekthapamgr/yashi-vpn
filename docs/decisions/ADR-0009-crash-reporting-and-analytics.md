# ADR-0009: Crash reporting and analytics

- **Status:** Proposed, pending owner and Android lead review
- **Milestone:** M01 task 7
- **Deciders:** Owner + Android lead

## Decision (proposed)

1. **No analytics or advertising SDKs in v1.** This follows from team pack 01 §2: no ad identifiers and no cross-app traffic monetization.
2. **Beta crash insight from Google Play Console Android vitals.** This adds no SDK to the app.
3. **Optional crash reporter, opt-in only, and only after privacy review.** If one is added, it must never send keys, tokens, exit IPs or traffic data. It must be disclosed in the privacy policy and the Play Data safety form, and switchable in Settings.
4. **Diagnostics stay user-initiated.** Users preview a redacted report and explicitly consent before upload (`POST /diagnostics`, team pack 02 A10).

## Consequences

- The beta crash-free target (≥99.5% of sessions, team pack 04) is measured from Play vitals. Small samples are reported as small.
- Any SDK added later goes through a new ADR and an update to the data inventory.
