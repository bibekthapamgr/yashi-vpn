# ADR-0004: Account, session and device enrollment flow

- **Status:** Proposed, pending backend and Android lead review
- **Milestone:** M01 task 7, implemented in M04 and M05
- **Deciders:** Backend lead + Android lead

## Decision (proposed)

1. **Sign-up:** email and password. The email must be verified before a device can enroll. No social login in v1, which keeps processors to a minimum.
2. **Responses:** register, login, reset and beta-interest give generic responses and the same timing, so they never reveal whether an account exists.
3. **Passwords:** hashed with a memory-hard algorithm (Argon2id proposed) and rate-limited per account and per source. The Android app supports password managers and autofill.
4. **Sessions:** short-lived access tokens plus rotating refresh tokens. Only a hash of each refresh token is stored. Reuse of a rotated token revokes the session family. Users can list and revoke their sessions, and a password change revokes the others.
5. **Device enrollment:** the app generates its WireGuard key pair on the device and sends only the public key. The device-limit check (1 for free) and the public-key uniqueness check run in one transaction.
6. **Deletion:** in the app and on the web, after reauthentication. Peers and sessions are revoked immediately, and data is deleted on the documented schedule (team pack 04).

## Consequences

- Verification and reset tokens are hashed, single-use and short-lived (`VerificationToken` in team pack 03).
- An email provider is needed before M04 ([ADR-0008](ADR-0008-transactional-email.md)).
- Reinstall abuse is limited with account and rate limits, not invasive device fingerprinting (team pack 01 §3).
