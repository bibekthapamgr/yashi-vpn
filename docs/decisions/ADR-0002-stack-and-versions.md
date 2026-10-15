# ADR-0002: Monorepo stack and pinned versions

- **Status:** Proposed, pending tech lead review
- **Milestone:** M01 tasks 5 and 7
- **Decider:** Tech lead

## Context

Team pack 01 proposes Kotlin + Jetpack Compose with the official WireGuard Android integration, React/TypeScript web, a modular Node.js/TypeScript API, PostgreSQL and Linux nodes, deliberately small (no microservices or Kubernetes on day one). It also says to pin supported stable dependencies after checking current official docs.

## Decision

Versions were checked against official sources on 15 Oct 2026.

| Area | Choice | Version | Note |
|---|---|---|---|
| Android build | Android Gradle Plugin with built-in Kotlin | 9.4.0 | Latest stable (Sept 2026); requires Gradle 9.6.0 and JDK 17 |
| Build tool | Gradle | 9.6.0 | Wrapper committed |
| Kotlin and Compose compiler | Kotlin | 2.4.21 | Compose compiler plugin version matches Kotlin |
| Compose | Compose BOM | 2026.09.00 | Compose 1.12+ requires compileSdk 37 and AGP 9 |
| JDK | Temurin | 17 | AGP 9.4 minimum |
| Web and API runtime | Node.js | 24 LTS | Planned end of life April 2028. Move to Node 26 after it becomes LTS |
| Language | TypeScript | 6.0.x | TypeScript 7.0 is released, but typescript-eslint supports only < 6.1 |
| Web | React and Vite | 19.3 and 8.x | |
| API framework | Fastify | 5.x | Schema-first routes fit the OpenAPI-first contract; built-in request injection for tests |
| Tests | Vitest and Testing Library | 5.x and 16.x | |
| Lint | ESLint and typescript-eslint | 10.x and 8.x | |
| Contract lint | Redocly CLI | 2.x | |
| Database (M04) | PostgreSQL | Chosen in M04 | Latest supported major at the time |

Exact resolved versions are in `package-lock.json` and `android/gradle/libs.versions.toml`. Dependabot proposes updates weekly for npm, Gradle and GitHub Actions.

## Alternatives considered

- **Express:** familiar, but no schema-first validation without extra libraries.
- **NestJS:** more structure, but heavier than the pack's "deliberately small".
- **AGP 8.13:** a smaller step for older guides, but the project would start on a superseded major and need an early migration.

## Consequences

- AGP 9 removes the separate `org.jetbrains.kotlin.android` plugin. Older tutorials need adapting.
- The Android build cannot run in the cloud workspace used to set this up, because Google's Maven repository is blocked there. The first Android build evidence comes from GitHub Actions.
- Any version change goes through a pull request with green CI.
