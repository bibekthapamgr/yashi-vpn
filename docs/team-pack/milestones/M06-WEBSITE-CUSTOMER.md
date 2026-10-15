# M06 — Public website and customer account portal

Owner: web engineer + designer/content reviewer. Depends: M02 + M04. Estimated effort: 6–10 working days.

## Goal

Give the product a credible public home and let users manage accounts safely without implying that a browser is running the phone VPN.

## Tasks in order

1. Build shared accessible tokens/components and mobile-first public layout. Public content should be indexable, fast and semantically structured. Self-host approved font assets.
2. Implement all public routes in design spec: home, download, features, locations, plans, privacy, terms, acceptable use, support, status and deletion request.
3. Populate only actual free-tier limits, countries and supported platform. Android download points to real beta/live distribution; never show an unverified download badge. No iOS/desktop “Download” buttons for nonexistent apps.
4. Integrate signup/login/verification/reset and customer Overview/Devices/Usage/Support/Settings. Use secure sessions and user-scoped APIs, not admin API credentials.
5. Implement device revoke with acknowledged/pending result, usage timestamps/reset, support ticket privacy and authenticated deletion. Public deletion page explains verification and available request channels without revealing account existence.
6. Build real status/incident presentation using sanitized backend data. Never expose management endpoints/keys or user information in public APIs.
7. Configure CSP/security headers, validation, CSRF protection, rate limits and restricted uploads. Validate SEO title/description/canonical links with actual approved domain only.

## Acceptance tests / gate

- Every nav/footer route resolves and legal content has owner/reviewer approval, not placeholder text.
- Browser has no fake Connect/Connected state; primary CTA downloads or explains Android setup.
- Device revoke/deletion propagates to actual node/access; unauthorized object access fails.
- Usage and location views show real data and distinguish stale/error states. No browsing history exists.
- Keyboard/contrast/responsive tests at 360/768/1440 widths; no overflow, broken focus or clipped dialogs.
- Proposed performance budget: Lighthouse mobile accessibility >=95 and performance >=90 on agreed staging conditions; report reproducible runs, not marketing guarantees.

## Handoff evidence

Staging URLs/routes, component inventory, responsive screenshots, auth/security tests, performance reports and legal-content signoff. Payments remain disabled; plans page explains current free offering and only an honest future roadmap.
