Draft — M01 — 15 Oct 2026 — pending review

# Secrets inventory

Secret **types** only. Never write a secret value, any part of one, or a recovery code here, or in an issue, pull request, chat or screenshot.

**Status on 15 Oct 2026:** none of these secrets exists yet, apart from the owner's own account credentials. CI uses only GitHub's automatic token, with read access; there are no repository secrets.

## Rules

- **Never in Git, APKs or AABs, logs or admin screens** (01 §1–2, M05, M07). Also never in CI output, issues, chat, screenshots, container images, instance user-data, or Terraform state kept in Git.
- One secret per environment and purpose; staging and production never share one ([environments](environments.md)).
- Each secret lives in the secret store (To decide), in a GitHub Actions environment secret, or in a root-only file on the host that uses it.
- Access follows the [access matrix](access-matrix.md): least privilege, named people, time-bound production access.
- Rotate on any suspected leak, when someone who could read the secret leaves, and on a schedule where one is set.
- A leak is an incident: follow the runbook listed below, rotate, revoke, check audit logs, and write the postmortem (04).
- "Gap" means 04 has no runbook for that leak. Write one before the secret is created.
- Guards already in the repo: the gitleaks secret scan in CI, and `.gitignore` rules for `.env`, `*.pem`, `*.key`, `*.p12`, `*.jks`, `*.keystore` and `service-account*.json`.

04 runbooks: node down/drain/replace · leaked node key · leaked provider token · compromised admin · DNS failure · runaway transfer · quota ledger corruption · account abuse · stalled revocation · Play rejection · bad APK release · database restore.

## Core secrets

| Secret type | Where it lives | Who can access | Rotation | If it leaks (04 runbook) |
|---|---|---|---|---|
| **Play app signing key:** signs the app that Play delivers | Google Play App Signing in the owner's organization account (proposed; confirm at setup) | Held by Google; controlled by the Owner | Not routine; Play's key-upgrade options To verify | Bad APK release; Gap for signing-key compromise |
| **Play upload key:** signs AABs uploaded to Play | Owner-controlled offline storage with a backup; a CI secret only if CI uploads (To decide) | Owner; release engineer JIT (M09) | On a leak or a change of holder; reset through Play Console (process To verify) | Bad APK release; Gap for upload-key reset |
| **Play service account:** CI publishing (if used) and M10 purchase verification | Production environment secret or secret store; keyless federation if supported (To verify) | Owner; DevOps/security JIT | To decide | Leaked provider token (closest); Gap |
| **Hosting provider API tokens:** infrastructure and node lifecycle | Secret store and environment secrets; one per environment and purpose, narrowest scope (scoping To verify) | Owner; DevOps/security (staging); production JIT | To decide; immediately on a leak | Leaked provider token; runaway transfer if spend spikes |
| **Node agent certificates/credentials:** authenticate each agent to the control plane | The agent's node, root-only; the issuing CA key in the secret store | System; DevOps/security | Short-lived and automatic (lifetime To decide); revocable per node (03) | Leaked node key |
| **Node WireGuard server keys:** node identity for tunnels | On the node only, root-only; never in admin screens or the API (01 §2) | System; DevOps/security (root) | On rebuild and on a leak; clients get the new public key with their next lease | Leaked node key; node down/drain/replace |
| **Database credentials:** API, migrations, backups | Secret store, delivered to the service on the control-plane host; a separate database user per purpose | System; DevOps/security; production JIT | To decide; on staff change | Gap; database restore if data was altered |
| **Session/token signing keys:** customer tokens; admin sessions use separate keys | Secret store, delivered to the API | System only | Scheduled, with key IDs so rotation does not sign everyone out (cadence To decide) | Gap (customer realm); compromised admin (admin realm) |
| **Email provider API key:** verification, reset and notice email | Secret store; one per environment; send-only scope if offered (To verify) | System; DevOps/security JIT | To decide; on a leak | Leaked provider token (closest); account abuse if misused; Gap |
| **Crash-reporting DSN and tokens** (only if adopted) | An ingestion key may have to ship inside the app: treat it as public and rate-limit it. Management tokens stay in the secret store | Android engineer, QA (project); Owner | On abuse or a leak | Gap |
| **GitHub Actions secrets:** CI/CD access to the secrets above | Environments `staging` and `production`; production needs owner approval (availability To verify) | Owner; DevOps/security (staging) | With the underlying secret | The underlying secret's runbook |

## Also tracked

| Secret type | Where it lives | Who can access | Rotation | If it leaks (04 runbook) |
|---|---|---|---|---|
| **Backup encryption keys** | Apart from the backup destination; the owner holds a recovery copy | Owner; DevOps/security (restore) | To decide; re-encrypt on a leak | Gap; database restore |
| **Node agent CA key** | Secret store; offline if feasible (To decide) | Owner; DevOps/security JIT | To decide | Gap: rotate the CA and reissue every agent credential |
| **SSH keys** for bootstrap and break-glass access | Each engineer's own key (hardware-backed preferred); only public keys on hosts | The key holder | On staff change or a leak; reviewed at each gate | Leaked node key; rebuild the host |
| **TLS private keys** (if not managed by the platform) | Host, root-only | System; DevOps/security | Automated renewal (To decide) | Gap |
| **Admin MFA factors and recovery** | AdminUser factor references in the database (encrypted); recovery owner-controlled and audited (M07) | Each admin; Owner for recovery | On compromise | Compromised admin |
| **Registrar and DNS credentials and tokens** | Owner account; a scoped DNS token in the secret store | Owner; DevOps/security JIT | On a leak or staff change | DNS failure (closest); Gap |
| **Owner recovery codes** (GitHub, Google, provider, registrar, email) | Offline, owner only | Owner | When used or exposed | Compromised admin (closest) |
| **Play reviewer test account** (M09) | Play Console app-access section; secret store | Owner; release engineer | After each review; on a leak | Account abuse |
| **Staging admin seed credential** (M04) | Delivered through a secure process; changed on first use | The named admin | Single use | Compromised admin |
