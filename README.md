# Yashi VPN

Monorepo for Yashi VPN: the Android app, the public website and portals, the control-plane API, the VPN node agent and infrastructure code.

> **Status: M01 foundations (15 Oct 2026).** This is a starter Android app, a web shell and an API health check. There is no VPN functionality yet; the first real tunnel is milestone M03.

The product baseline is the team pack in [`docs/team-pack/`](docs/team-pack/00-START-HERE.md). Read documents 01–05 in order before starting a milestone. Decisions are recorded in [`docs/decisions/`](docs/decisions/README.md) and M01 progress in [`docs/milestones/M01-status.md`](docs/milestones/M01-status.md).

## Repository layout

| Path | What lives here | Status |
|---|---|---|
| `android/` | Kotlin + Jetpack Compose app; WireGuard tunnel from M03 | Starter app |
| `web/` | React + TypeScript: public site, customer portal and admin portal, with separate routes and authorization | Shell |
| `api/` | Node.js + TypeScript modular API (control plane) | `GET /v1/health` only |
| `node-agent/` | Restricted agent that runs on VPN nodes | Not started ([ADR-0007](docs/decisions/ADR-0007-node-agent-runtime.md)) |
| `infra/` | Terraform/Ansible for VPN nodes and the control plane | Not started (M03) |
| `contracts/` | OpenAPI source of truth for `/v1` | Draft |
| `docs/` | Team pack, decisions, architecture, security, operations, owner actions | M01 drafts |

## Prerequisites

- Node.js 24 LTS (see `.nvmrc`; 22.12 or newer also works) and npm 10 or newer
- JDK 17 and an Android Studio release that supports AGP 9.4, for `android/`

## Commands

Run from the repository root unless noted.

| Task | Command |
|---|---|
| Install web and API dependencies | `npm ci` |
| Lint web and API | `npm run lint` |
| Type-check | `npm run typecheck` |
| Unit tests | `npm test` |
| Production builds | `npm run build` |
| Lint the OpenAPI contract | `npm run contracts:lint` |
| Run the API locally | `npm run dev -w api`, then open http://localhost:8080/v1/health |
| Run the web shell locally | `npm run dev -w web` |
| Android debug build, unit tests and lint | `cd android && ./gradlew assembleDebug testDebugUnitTest lintDebug` |

CI (`.github/workflows/ci.yml`) runs all of these on every push to `main` and on every pull request, plus a dependency audit and a secret scan. Database migration checks are added with the first migration in M04, and infrastructure checks with the first infrastructure code in M03.

## Rules for every change

From [`01-TEAM-INSTRUCTIONS.md`](docs/team-pack/01-TEAM-INSTRUCTIONS.md):

- No secrets in Git or in APKs. Copy `.env.example` to `.env` for local work; CI secrets live in GitHub Actions secrets.
- No real customer data in fixtures. Label prototypes and demo data visibly.
- Small reviewed pull requests against a protected `main`. Security-sensitive changes need a second reviewer.
- Every milestone hands over the evidence in its Definition of Done (see the pull request template).
