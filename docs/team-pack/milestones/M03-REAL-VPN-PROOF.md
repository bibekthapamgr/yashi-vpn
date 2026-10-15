# M03 — Real tunnel technical proof

Owner: Android + DevOps engineers. Depends: M01. Estimated effort: 3–5 working days.

## Goal

Demonstrate an actual encrypted full-tunnel connection on a physical Android device before investing in portal polish.

## Tasks in order

1. Provision approved staging Linux VPN node with public IPv4 and root/sudo bootstrap access. Configure forwarding, WireGuard UDP, NAT and minimum firewall. Document repeatable setup.
2. Integrate the official WireGuard Android tunnel library into a minimal native app; inspect license/version. Avoid writing raw crypto or shipping a sample app unchanged.
3. Generate device private/public keys locally. Protect local storage and exclude key files from backups/debug logs. Manually enroll public key only in staging for this gate.
4. Implement Android VPN consent/service lifecycle and required foreground behavior against current docs. Use explicit connect/cancel/disconnect states.
5. Route IPv4 and DNS in tunnel. Choose full IPv6 support or tested bypass prevention; document it. Test external exit address through app-controlled endpoint and actual browsing; verify recent handshake before reporting success.
6. Test Wi-Fi/mobile switching, no-UDP network failure, process recreation and reconnection. Capture latency, throughput and battery observations honestly, not as launch guarantees.
7. Test client isolation and denied access to metadata/private management addresses. Verify resolver is not open to internet.

## UI deliverable

Temporary native state screen tied to real service events: Disconnected/Connecting/Verifying/Connected/Error. Clearly label any debug controls; they must not enter release builds.

## Acceptance tests / gate

- A real Android phone has verified tunnel egress through the staging node; screenshot/recording plus redacted logs substantiate it.
- No unintended DNS/IPv6 bypass in agreed full-tunnel tests. Unsafe routing blocks M04.
- Denial/cancellation/disconnect work without stuck notifications or green false status.
- Root credentials and private keys absent from repo/APK logs. Provisioning can be reproduced from docs.
- WireGuard blocked network shows an honest error, not silent unencrypted fallback.

## Handoff evidence

Commit/build, device/OS/network matrix, tunnel proof, routing/leak test results, benchmark context, known restrictions and infrastructure teardown/rebuild instructions. No claim of public-launch readiness yet.
