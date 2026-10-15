# M05 — Polished production Android experience

Owner: Android engineer + UX/QA. Depends: M02 + M04. Estimated effort: 10–15 working days.

## Goal

Build all free-v1 customer journeys with real service state, approved visuals and robust lifecycle handling.

## Tasks in order

1. Implement Compose design components/themes and navigation. Create resource-based copy and accessibility semantics. Keep view state separate from tunnel/service state.
2. Integrate signup/sign-in/verification/reset, privacy disclosure and Android permission flow. Support password managers and session expiry.
3. Implement home A04 with real readiness, connection verification, cancel/disconnect, selected location and authoritative usage/reset. Cache only safe metadata; show stale age.
4. Implement location search/favorites/availability, deterministic recommended-node selection and controlled switch/revocation. Show measured latency only with timestamp; do not fake ping.
5. Build details, account/device/usage, settings, auto-reconnect, Help/incident and deletion. Redacted diagnostics preview and explicit consent required before upload.
6. Guide users to Android always-on/Block connections without VPN; do not conflate reconnect and lockdown. Explain quota/expiry consequences and recovery.
7. Correctly handle foreground notification, notification Disconnect, screen off, battery saver, process recreation, network handover and app upgrade. Validate current platform service requirements.
8. Remove debug/config export screens, fake data, hard-coded provider keys and insecure TLS flags. Prepare signed internal AAB using owner-controlled process.

## Acceptance tests / gate

All A01–A11 screens work against staging without mock services. UI and foreground notification show the same actual state. Connected is reported only after handshake/routed reachability validation. Loss/stale checks change status honestly. 360 dp, 200% text, TalkBack, both themes and reduced-motion checked. Denial/cancel/exhausted/offline/server-maintenance/token-expiry flows have clear recovery. Account deletion revokes actual access. No private key in crash/analytics/backup.

## Handoff evidence

Installable internal build, CI test outputs, real-device recordings, screen-to-Figma comparison, accessibility review and known device-specific issues. No Premium tab/payment prompt at free launch; entitlement model may exist invisibly for M10.
