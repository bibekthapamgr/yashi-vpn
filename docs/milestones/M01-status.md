# M01 status: decisions, procurement checks and engineering foundations

Updated 15 Oct 2026. Gate owners: product lead and technical lead (today, the owner). Gate definition: [M01-FOUNDATIONS.md](../team-pack/milestones/M01-FOUNDATIONS.md).

## Acceptance tests

| M01 gate test | Status | Evidence |
|---|---|---|
| A clean clone builds a starter Android app, web shell and API health test via documented CI commands | Web and API pass locally. The Android build is verified in GitHub Actions only | Commands in the root README; CI workflow `.github/workflows/ci.yml` |
| No committed secrets; the team reaches staging with individual permissions; the database is not public | Secret scan runs in CI. No staging exists yet because procurement is blocked | `secret-scan` CI job; [environments](../ops/environments.md); [access matrix](../ops/access-matrix.md) |
| Owner-approved scope and budget, plus a list of unresolved decisions | Done in part | [ADR-0001](../decisions/ADR-0001-owner-scope-decisions.md) lists what is accepted and what is open |
| Provider permission documented, or procurement explicitly blocked | **Procurement explicitly blocked** until a provider confirms in writing | [provider checklist](../ops/provider-checklist.md), [ADR-0011](../decisions/ADR-0011-first-region-and-provider.md) |
| Threat model, data inventory and versioned API draft reviewed by Android, backend and ops leads | Drafts ready; **review pending** because no leads are invited yet | [threat model](../security/threat-model.md), [data inventory](../security/data-inventory.md), [`contracts/openapi.yaml`](../../contracts/openapi.yaml) v0.1.0-draft |

## Tasks

| # | Task | Status |
|---|---|---|
| 1 | Record owner decisions | Accepted: free plan, Android only, global audience, US$20/month development ceiling. Open: support hours, country exclusions, production budget, publishing entity |
| 2 | Brand clearance route | Placeholders in place ([ADR-0012](../decisions/ADR-0012-app-identity-placeholders.md)); owner checks pending |
| 3 | Provider permissions | Blocked. Checklist and email are ready for the owner to send |
| 4 | Google Play Organization verification | Not started. Needs a D-U-N-S number first |
| 5 | Monorepo, protected branches, reviewers, CI | Monorepo and CI done. Branch protection is an owner action |
| 6 | Environments, secrets, backups, access, threat model, data inventory, logging defaults | Drafts done. API request logs exclude client IPs, query strings, headers and bodies. Host and proxy logging is reviewed once a provider is chosen |
| 7 | OpenAPI, entity diagram, decision records, M03 uncertainties | Drafts done: 51 operations (1 implemented), [entity diagram](../architecture/entities.md), [12 ADRs](../decisions/README.md) |

Owner actions are tracked in [owner-actions.md](../owner/owner-actions.md).

## UI deliverables

Not started. M01 asks for an approved screen inventory, a navigation diagram, the content hierarchy, and the first-time and returning-user flows. That needs the UX designer, or the owner approving the screen inventory in team pack 02 as it stands.

## Technical uncertainties for M03

- WireGuard Android library: current version and licence check.
- DNS and IPv6 approach ([ADR-0006](../decisions/ADR-0006-dns-and-ipv6.md)), including Android Private DNS behaviour and MTU on mobile carriers.
- Provider support for UDP, dedicated IPv4 and IPv6, and its abuse-handling expectations.

## Known limits of this evidence

- The Android build could not run in the workspace that created this repository, because Google's Maven repository is blocked there. GitHub Actions is the first Android build.
- Lease switching, web session style (cookies or bearer tokens) and several retention periods are proposals in the drafts and need lead and owner review.
- Do not deploy anything publicly at this gate (M01 handoff rule).
