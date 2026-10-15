Draft — M01 — 15 Oct 2026 — pending review

# Environments

Development, staging and production are fully isolated (01 §1, M01 task 6). **Production is not created in M01.** Staging is blocked until a hosting provider confirms in writing that a public consumer VPN is allowed ([provider checklist](provider-checklist.md)).

## Current state (15 Oct 2026)

| Environment | Exists | Notes |
|---|---|---|
| Development | Yes, on developer machines | Starter Android app, web shell, API with `GET /v1/health` only |
| CI | Yes: GitHub Actions in the private repo `bibekthapamgr/yashi-vpn` | No repository secrets; only GitHub's automatic token, with read access |
| Staging | No | No provider chosen; procurement is blocked until a written yes is on file |
| Production | No | Not created in M01. Needs owner approval of provider, region and budget; planned for M09 after the M08 gate (timing To decide) |

## Isolation

| | Development | Staging | Production |
|---|---|---|---|
| Purpose | Build and unit tests | Integration, device, security and load testing (M03–M08) | Real users (M09 onward) |
| Provider project or account | None (local) | Its own project or account (mechanism To verify per provider) | Separate from staging; owner-owned |
| Network | Local machine | Its own private network; the firewall allows tunnel UDP plus restricted management only | Its own private network with the same rules; no peering or shared firewall with staging |
| Database | Local PostgreSQL (from M04) with synthetic data | Its own PostgreSQL on the control-plane node; not public | Its own PostgreSQL with a restorable standby, or a managed database (03); not public |
| Secrets | Placeholders from `.env.example`; values each developer generates locally | Staging-only set ([secrets inventory](secrets-inventory.md)) | Production-only set; using it needs owner approval |
| Node agent trust | None | Staging-only CA and credentials | Production-only CA and credentials; staging credentials are rejected |
| Hostnames (placeholders) | `localhost` | `staging.example.com`, `api.staging.example.com`, `admin.staging.example.com` | `example.com`, `api.example.com`, `admin.example.com`, `status.example.com` |
| Android build | Debug | Debug or internal builds that reach staging only | Release AAB from Play that reaches production only |
| Email | None, or a local mail catcher | Team-controlled test inboxes only | Real users |
| People | Each developer | Individual accounts per the [access matrix](access-matrix.md) | The owner; anyone else time-bound and owner-approved |
| Spend ceiling | — | US$20/month for development and staging together (owner, 15 Oct 2026) | To decide. 04 suggests reserving US$60–120/month for a small two-region launch (planning figure, not a quote) |

Rules:

- No environment shares a credential, database, network, agent CA or cookie scope with another. Whether staging gets its own registrable domain instead of a subdomain is To decide; with a subdomain, production cookies use the `__Host-` prefix so staging cannot set or overwrite them.
- No domain is purchased. Hostnames stay `example.com` placeholders, and no production identity is committed (M01 task 2).
- Release builds never contain staging endpoints, trust in user-installed certificates, or TLS bypasses (01 §2). A staging-only certificate may be trusted through debug-only configuration.
- Staging is not public: no customer sign-ups, no public links, `noindex` (the web shell already sets it). The M01 gate allows no public deployment.

## Data rules

- No real customer data outside production: not in staging, development, fixtures, CI logs, issues, pull requests, chat or screenshots (01 §1).
- Never copy a production database or backup into staging or development. Production restore tests run in a temporary, production-controlled environment that is deleted afterwards (proposed).
- Staging accounts are synthetic and visibly labelled, as is all demo data (01 §1).
- Team members' traffic through the staging VPN node gets the production privacy configuration: no destination or DNS logging. M08 verifies privacy on production-like staging.
- Production data access is owner-approved, time-bound and audited. Use admin-portal views before direct database access.

## Promotion flow

1. Issue, branch, small pull request with the template checklist.
2. CI on every pull request: lint, type-check, unit tests, builds, contract lint, dependency audit, secret scan, and the Android build, unit tests and lint (this exists today). Infrastructure checks arrive in M03 and migration checks in M04.
3. Review by the code owner, plus a second reviewer for security-sensitive paths (00).
4. Merge to a protected `main` (protection on this private repo To verify; see the [access matrix](access-matrix.md)).
5. Deploy `main` to staging automatically as a versioned artifact (proposed, from M03/M04).
6. Verify on staging: smoke tests, QA evidence for the changed areas, migration dry run.
7. Tag a release candidate. **The owner approves every production deployment.**
8. Deploy the same artifact to production; back up the database before migrations; run post-deploy checks.
9. To roll back, redeploy the previous artifact. Migrations stay backward-compatible (expand first, contract later). Record the steps for every release (01 §5).

- **Android:** CI debug build → internal testing track → closed testing → staged production rollout, each step with an owner go/no-go (M09).
- **Infrastructure:** Terraform and Ansible changes follow the same path: plan output in the pull request → staging apply → owner-approved production apply.

## Backups

| | Staging | Production |
|---|---|---|
| What | PostgreSQL | PostgreSQL, plus a standby for availability (03) |
| Encryption | Encrypted before leaving the host; key stored apart from the backups | Same, with a separate production key |
| Destination | To decide; not the same host | To decide; a different account or region from the primary |
| Frequency | To decide | To decide |
| Retention | To decide | To decide, written together with the deletion rules (04) |
| Restore test | Monthly (proposed) and after any change to backups | Drill before M08 sign-off and before launch (04, M08); then quarterly (proposed) |

Never backed up: device private keys (01 §2). Node WireGuard server keys are rebuilt and rotated rather than backed up (proposed; the key backup policy is To decide, 03). Restores must exclude deleted accounts by applying tombstones (04, M04).

## Staging footprint (target: within US$20/month)

| Component | Proposed footprint | Notes |
|---|---|---|
| VPN node | 1 small Linux VM: pinned Ubuntu LTS, public IPv4 (and IPv6 if offered), WireGuard on UDP | Team devices only (M03) |
| Control-plane node | 1 small Linux VM: API, workers, PostgreSQL, reverse proxy | Private network to the VPN node; database not public |
| Backups | Encrypted database dumps | Destination To decide |
| Left out | Load balancer, managed database, permanent standby, second region | A temporary standby only for the M08 failover drill, deleted afterwards |
| Monthly cost | To verify from written quotes | 04 plans US$20–40/month for this footprint, excluding tax and domain. The ceiling is the low end of that range: if quotes exceed it, nothing is rented until the owner decides |

Spend controls: billing alerts at 50/75/90% of the ceiling (04). Alerts are not treated as hard caps unless the provider confirms a cap in writing (checklist Q12). Nodes are rebuilt from code, so staging can be torn down when idle.

## Production (not created in M01)

Created only through owner-approved infrastructure code, after: written provider permission; owner approval of provider, region and budget; the M08 gate; approved legal and privacy texts; and a standby design that restores inside the lease margin (03).
