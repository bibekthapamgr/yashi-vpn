Draft — M01 — 15 Oct 2026 — pending review

# Access matrix

Who may access which system, by role. **Today only the owner has access.** No team members are invited (15 Oct 2026). The matrix below is the proposed default for when people join; the owner grants each access individually.

## Principles

1. **Individual accounts.** No shared accounts, passwords or tokens (01 §2, §4).
2. **MFA everywhere** it is supported. The owner keeps recovery codes offline.
3. **Least privilege.** The default is no access; grant the minimum the task needs.
4. **Time-bound and revocable.** Every grant has a reason and a review date. Production access is just-in-time and expires. Revoke access on the day a role ends.
5. **The owner retains ownership of every account:** source, design source, deployment access, domains, the Play organization account, provider accounts and signing/recovery documentation (01 §4). Nobody opens a project account with a personal email address or pays with a personal card.
6. **Second reviewer** for security-sensitive changes (00). Whoever deploys to production is never the only reviewer.
7. **Recorded.** Every grant and revoke is logged: person, system, level, reason, approver, date, expiry. Location To decide; the log never contains credentials.
8. **Reviewed** monthly (proposed) and at every milestone gate.

## Access levels

**Owner:** owns the account, billing and recovery; grants access. **Admin:** manages settings; cannot transfer ownership. **Write:** changes through review (pull request or approved process). **Read:** view only. **JIT:** time-bound and owner-approved, for one task. **—:** no access.

## Matrix (proposed)

| System | Owner | Tech lead | Android engineer | Web/backend engineer | DevOps/security | QA | Designer |
|---|---|---|---|---|---|---|---|
| GitHub repo (private, `bibekthapamgr/yashi-vpn`) | Owner | Write; reviewer | Write | Write | Write; reviewer for `infra/` and `node-agent/` | Write (tests) | Read; Write for design-token files |
| GitHub Actions secrets | Owner | — | — | — | Staging: Admin. Production: JIT | — | — |
| Google Play Console | Owner (organization account) | Testing tracks; production only with owner approval | Testing tracks; vitals | — | — | Testers, pre-launch reports, vitals | Store-listing drafts |
| Hosting provider (not chosen) | Owner (billing, recovery) | Staging: Read | — | Staging: Read (JIT) | Staging: Admin without billing. Production: JIT | — | — |
| Domain/DNS (none purchased) | Owner (registrar) | — | — | — | DNS records through a scoped token (JIT) | — | — |
| Email provider (not chosen) | Owner | — | — | Staging delivery logs (JIT) | Sending-domain records and key rotation (JIT) | — | — |
| Staging DB | Owner | Read (JIT) | — | Schema through reviewed migrations; direct access JIT | Admin | — (uses the API and admin portal) | — |
| Production DB (not created) | Break-glass only | — | — | — (the pipeline runs migrations) | JIT, owner-approved, audited | — | — |
| Admin portal, staging (M07) | Owner | Operator | Read-only | Operator | Operator | Own test account in each role | Read-only |
| Admin portal, production (M07) | Owner | Read-only; Operator when on call | — | —; Read-only when on call | Operator | — | — |

Admin-portal roles (M07):

- **Owner:** roles, provider settings, plans, budget and release decisions.
- **Operator:** node drain and maintenance, investigated access revocation; cannot grant the Owner role.
- **Support:** user support with minimal visibility; no fleet secrets or plan grants. Given only to named support staff the owner approves; no such person exists yet.
- **Read-only:** sanitized metrics and audit, as permitted.

Admin accounts (AdminUser) are separate from customer accounts, require MFA, and are authorized on every endpoint, not by hiding UI. The owner approves production operators and the support scope (M07). QA's per-role test accounts each belong to one named person.

## To verify

- **GitHub plan.** On a repository owned by a personal account, collaborator permissions may not be granular, and branch protection, rulesets and environment approvals for private repositories may depend on the plan. If so, move the repo to a GitHub organization that the owner owns.
- **Actions secrets** cannot be read back once saved, but anyone who can change a workflow can make it use the secrets that workflow can reach. Production secrets go in a protected environment that needs owner approval (availability To verify), and workflow changes need code-owner review.
- **Play Console** permission names get mapped once the organization account exists. Its verification status is To verify (M01 task 4; D-U-N-S number first).
- **Third-party GitHub Actions** are referenced by version tag in CI; pinning them to commit SHAs is proposed ([threat model](../security/threat-model.md) §5).

## Other owner-owned accounts

Design source (Figma or equivalent), crash reporting (if adopted), status page and monitoring, backup storage and the secret store: all To decide, all owner-owned, all under the same principles.

## Access register

| Person | Role | Systems | Level | Since | Review |
|---|---|---|---|---|---|
| Owner (`@bibekthapamgr`) | Owner | GitHub repository. Play Console status To verify; no other accounts created | Owner | — | — |

No other person has access.

## Joining and leaving

- **Joining:** the owner invites from the owner account; the person turns on MFA before any grant; access follows this matrix; the register is updated.
- **Leaving or changing role:** revoke everything the same day; rotate every secret the person could read ([secrets inventory](secrets-inventory.md)); update the register.
