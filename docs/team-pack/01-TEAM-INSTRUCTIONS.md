# Mandatory team instructions

## 1. Delivery contract

- Use English for code, API fields, developer documents and default UI. Keep all customer text in localization resources; add Nepali only after translation review. No hard-coded copy in Compose/web components.
- Create a monorepo: `android/`, `web/`, `api/`, `node-agent/`, `infra/`, `contracts/`, `docs/`. Public, account and admin web surfaces may share components but must have separate routes and authorization boundaries.
- Proposed stack: Kotlin + Jetpack Compose + official WireGuard Android tunnel integration; React/TypeScript web; Node.js/TypeScript modular API; PostgreSQL; Linux nodes; Ansible/Terraform where supported. This is deliberately small, not microservices/Kubernetes for day one.
- Pin supported stable dependencies after checking current official docs and security advisories. Record versions/licenses in a dependency inventory. Never freeze a target SDK or billing version from this document; recheck Play deadlines at submission.
- Use issue IDs and small reviewed PRs. Protect the main branch. CI must compile/test Android, typecheck/test/build web/API, scan dependencies/secrets and validate migrations. Store signing/service-account credentials in restricted CI secrets, never in Git or APK.
- Keep staging and production separate. No real customer data in test fixtures. Supply `.env.example` with placeholders only.
- A screen with mocked data is not an integrated feature. Label prototypes and demo data visibly. Never ship random server load, invented ping, fake reviews or fake country coverage.

## 2. Non-negotiable security/privacy

- Device generates its own WireGuard private key; do not upload it, include it in analytics or put it in cloud backups. Encrypt its at-rest representation using Android Keystore-backed protection where feasible; document limits because WireGuard software needs key material in memory.
- Backend stores public keys only. Node server private keys live on their nodes/approved secret store, not admin screens. Cloud provider API tokens stay server-side with least privilege.
- Separate control plane (login/provisioning) from data plane (browsing traffic). Do not proxy browsing through the website/API server.
- No browsing history, destination IP/domain logs, DNS query logs, packet payloads, ad identifiers or cross-app traffic monetization. Usage-byte counters are permitted for transparent quota enforcement; explain them to users.
- Application privacy policy must reflect the hosting provider, email, DNS, crash and analytics processors actually used. Avoid an absolute “no logs” claim while account, quota, IP-related or operational metadata exists. Prefer a precise statement about not storing browsing history if verified true.
- No arbitrary shell textbox in admin. Server provisioning uses restricted commands, structured templates and authenticated agent operations. Validate every input; never interpolate user data into shell commands.
- Account disable/deletion/device revocation must remove real node access, not just hide an app button. Check this with an already-connected and an exported/stale configuration in staging.
- Audit administrative access and changes with actor, action, object ID, reason and UTC time; do not audit traffic destinations. Redact credentials and sensitive request bodies from logs.
- Mandatory MFA for admins, role-based authorization and step-up confirmation on disruptive actions. No shared admin accounts. Staff cannot view passwords/private keys or silently impersonate users.
- Web: secure cookies, CSRF protection where appropriate, strict validation, rate limits, CSP and authorization on every object operation. Android: short-lived auth tokens, rotated/revocable refresh tokens and no debug trust bypass in release.

## 3. Product honesty and reliability

- Connected means tunnel handshake AND an app-controlled reachability check routed inside the tunnel succeeded recently. A spinning animation or interface-up event is not proof. If verification becomes stale, show “Checking connection” or degraded state.
- Never say that a VPN makes users completely anonymous or secures every type of endpoint threat. Describe device-to-VPN-server encryption accurately; onward traffic needs HTTPS for content protection.
- Distinguish automatic reconnect from Android always-on/“Block connections without VPN”. Guide users into the system settings; do not show a false in-app “kill switch enabled” state without confirmation. With lockdown on and quota exhausted, internet may remain blocked until quota resets or the user changes OS settings; explain this beforehand.
- Enrollment/device limits and quota are enforced by backend AND node policy. Client clock changes, reinstall or app termination cannot restore quota. Avoid invasive fingerprinting to prevent reinstall abuse; use account limits/rate limits and documented risk controls.
- Do not automatically downgrade to an unencrypted connection when WireGuard fails. Explain unsupported/blocked networks; OpenVPN/obfuscation would need a future scope.
- Preserve connection status across app recreation. Switching server is a controlled transition, not simultaneous conflicting tunnels. Only one active device lease per free account.
- All network operations have timeouts, cancellation and bounded retries with jitter. Never create retry storms when the control plane is down.

## 4. Ownership and approval

Owner retains source, design source, deployment access, domains, Play organization account, provider accounts and signing/recovery documentation. Team access is individually granted and revocable. Do not buy personal consumer VPN subscriptions and redistribute them.

Before launch obtain: brand/domain approval, host approval for a public consumer VPN use case, reviewed legal/privacy/acceptable-use text for actual launch markets, organization verification and release budget. This document is engineering guidance, not a jurisdiction-specific legal opinion.

## 5. Definition of done for every milestone

Provide: commit/tag; build artifacts or staging URL; implementation summary; changed API/migrations; automated test output; physical-device evidence when relevant; UI screenshots including failure states; accessibility findings; known issues; deployment/rollback steps; reviewer signoff. Zero unresolved critical security bugs or safety/privacy misrepresentations. Owner approves gate before next release stage.
