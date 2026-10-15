# M02 — Complete design system and validated UX

Owner: UX designer + product lead. Depends: M01. Estimated effort: 5–8 working days.

## Goal

Make the whole product visually coherent and understandable before implementation. Deliver usable screen specifications, not just one attractive home screen.

## Tasks in order

1. Review current competitor Android screens using authorized test installs where possible. Record app versions, public/reference screenshots used internally and patterns observed. Do not copy trade dress or assert speed/security superiority.
2. Build Quiet Orbit foundations and accessible dark/light tokens. Create typography, buttons, inputs, pills, usage meters, location rows, dialogs, sheets, nav, tables, pagination, empty states, alerts and focus/disabled/loading variants.
3. Design Android A01–A11; reserve A12 premium as future-only. Map every state to the API/service state enum. Include denied VPN permission, revoked account, quota exhaustion, stale usage, server maintenance and network blocked cases.
4. Design every public route and a conversion-oriented homepage; actual availability and honest limits, no invented numbers/testimonials.
5. Design customer Overview/Devices/Usage/Support/Settings with revoke/deletion and responsive variants.
6. Design admin dashboards, lists/detail forms, role visibility, audit filtering, drain/revoke dialogs, error/retry and no-data cases. Include operational semantics and last-updated timestamps.
7. Prototype key journeys; run five-person usability check. Ask users to identify actual state, free quota/reset, download vs connect and device-revoke consequences. Revise where confused.
8. Publish tokens, component states, spacing, copy and data bindings; engineers review feasibility before freeze.

## Required prototype journeys

- New account → disclosure → Android permission → connect → verified state → disconnect.
- Permission rejected → settings/help → retry without forced loops.
- Network loss → reconnect → alternate server → result.
- Quota exhausted → reset information → OS lockdown warning.
- Customer revoke device → pending → confirmed.
- Admin drain node → new grants stop → leases expire/move → node offboard.

## Acceptance tests / gate

At least 4/5 testers find Connect and identify current state without help; 4/5 understand quota/reset. No participant mistakes website for an active VPN. Contrast tested for actual component pairings. 200% font scale and 360 dp screens preserve primary actions. Figma components have variants and developer mapping, not flattened screenshots. All consumer/admin surfaces have loading/empty/error/permission states.

## Handoff evidence

Figma source + clickable prototype, token export, screen checklist, usability notes, measured contrast results and owner approval. Implementation must use approved tokens; later deviations require recorded review.
