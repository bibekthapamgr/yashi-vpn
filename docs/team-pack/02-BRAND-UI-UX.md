# Yashi VPN — Brand and UI/UX specification

## Creative direction: Quiet Orbit

A calm premium utility: midnight navy, glacier cyan, warm white, precise typography and generous whitespace. Connection is the hero; decorative maps are secondary. Do not copy competitor logos, maps, screen arrangements or trade dress. Do not overload v1 with security-suite upsells.

Owner-selected brand; see naming clearance note in START-HERE. Logo brief: a simple original Y formed from two offset tunnel arcs that converge into one stem, readable in monochrome and at 24 px. No generic stock shield, flag or competitor mountain mark. Deliver editable SVG, monochrome mark, vector wordmark, Play adaptive foreground/background and monochrome Android icon; verify current Play asset dimensions before export. This pack specifies the logo; it does not include a finished logo asset.

## Design tokens

| Token | Dark | Light | Use |
|---|---|---|---|
| Background | #081321 | #F5F8FC | Main canvas |
| Surface | #122238 | #FFFFFF | Cards/nav |
| Elevated surface | #1B304A | #EAF0F7 | Bottom sheets/secondary areas |
| Primary text | #F4F8FF | #122238 | Headings/body |
| Secondary text | #B5C4D6 | #4A5E75 | Supporting labels |
| Brand/action | #68E0E8 | #006872 | Buttons/links |
| On-brand button text | #081321 | #FFFFFF | Never white on pale cyan |
| Success | #76E3B1 | #17643B | Verified connection |
| Warning | #FFD38A | #805000 | Reconnect/low quota |
| Error | #FFADB5 | #A52238 | Failures/revocation |
| Decorative border | #2B405A | #CFD9E7 | Card separation only |

These are proposed tokens, not a certified contrast report. Designer/QA must measure actual text/background pairings for WCAG AA, including disabled/hover/focus states. Use a stronger border for input boundaries/focus where required. Never encode connection state by color alone.

Typography: Android uses system Roboto/Roboto Flex; website uses self-hosted Inter with system fallback. H1 web 48–60 px desktop/32–38 mobile; Android hero status 28–32 sp; body 16 sp/px; supporting labels 14; metadata 12 only when noncritical. Use tabular digits for usage/timers. Preserve system font scaling up to 200% without hiding the primary action.

Spacing: 4/8/12/16/24/32/48/64. Phone horizontal padding 20 dp; page max-width 1200 px; desktop portal sidebar 240 px. Card radii 20 px consumer, 12 px admin; button radius 14. Primary controls at least 48 dp Android, 44 px web. Tables use 48–56 px rows. Use one outlined icon family with consistent 20–24 px optical size.

Motion: 150–250 ms for local transitions; no indefinite pulsing globe or battery-heavy particle scene. Reduced-motion disables ornamental movement. No animation can imply a real connection before verification.

Themes: Android defaults to system appearance, offers System/Light/Dark. Website starts light with a midnight hero; account supports both; admin defaults light for data density. All share semantic tokens.

## Android information architecture

Bottom navigation: **Connect / Locations / Account**. Settings and Help reachable from Account and a discreet home toolbar action. Do not introduce a dead “Premium” tab at free launch.

### Screen inventory and behavior

| ID | Screen | Required content and interactions |
|---|---|---|
| A01 | Welcome | Original logo, one-sentence benefit, Get started, Sign in, visible free limit; no forced carousel |
| A02 | Account | Email/password signup, verification, sign-in, reset, clear validation; password manager support |
| A03 | Privacy + permission explanation | Explain actual metadata/processor use, privacy link, affirmative choice, then Android VPN consent |
| A04 | Connect home | Verified status, large Connect/Disconnect, region picker, quota and reset date, latest health notice |
| A05 | Locations | Search, Recommended, Favorites, country/city list, availability; only real locations |
| A06 | Location detail sheet | Country/city, protocol, observed latency age, free eligibility, Connect |
| A07 | Connection detail sheet | Protocol, verified exit IP (reveal on tap), session duration, byte counts, diagnostic explanation |
| A08 | Settings | Theme, auto-reconnect, OS always-on/lockdown guide, privacy choices, app version |
| A09 | Account/usage | Plan, monthly used/remaining, UTC reset, enrolled device, sign out, delete account |
| A10 | Help | Searchable FAQs, incident status, contact ticket, opt-in redacted diagnostics preview |
| A11 | Quota exhausted | Honest reset date, blocked access explanation, Help; later premium option only when live |
| A12 | Premium (M10) | Localized actual Play price, renewal/trial terms, restore/manage, purchase status |

### Home layout — top to bottom

1. Compact Yashi wordmark left; help/settings action right.
2. Status pill with icon, using the pill label defined for the current state in the State contract below.
3. A quiet two-arc connection illustration around a 144–160 dp central connect control. The semantic action is an accessible button; decorative rings are hidden from TalkBack.
4. Primary status headline plus one sentence. Example connected: “Connected to Germany” / “VPN connection verified”. Avoid “100% safe”.
5. Tappable location card: country name, city, actual free tier badge, chevron. Recommended server may change with real availability; do not label “fastest” without measurement.
6. Usage card: “1.2 GiB of 5 GiB used”, progress indicator, “Resets 1 Nov, 00:00 UTC”. Usage from server; stale/offline indicator if cached. Optional advanced detail gives measurement timestamp.
7. Compact details/incident row. No ad between status and disconnect. No mandatory upgrade modal on app open.

### State contract and copy

| State | Pill label | Headline | CTA | Safety behavior |
|---|---|---|---|---|
| Disconnected | Not connected | Not connected | Connect | Explain traffic not using VPN |
| Permission pending | Permission needed | Allow VPN connection | Continue / Not now | Do not fake Android consent |
| Connecting | Connecting | Connecting to Germany… | Cancel | Timeout with specific recovery |
| Verifying | Checking connection | Checking your connection… | Cancel | Do not show Connected yet |
| Connected | Connected | Connected to Germany | Disconnect | Show when verification was last current |
| Reconnecting | Reconnecting | Reconnecting… | Cancel | OS lockdown state separately explained |
| Offline | Offline | No internet connection | Retry when online | Avoid repeated retries |
| Degraded | Connection issue | Connection needs attention | Retry / Choose location | No green success icon |
| Exhausted | Limit reached | Monthly free data used | View reset / Help | Revoke node access; describe OS blocking |
| Suspended | Unavailable | Access unavailable | Contact support | Do not reveal abuse-detection internals |

Back navigation, notification Disconnect, cancellation during connect, server switch while connected, token expiry and app process recreation all need explicit designs. Country flags are optional decorative cues, never the only labels.

## Public website

Routes: `/`, `/download/android`, `/features`, `/locations`, `/plans`, `/privacy`, `/terms`, `/acceptable-use`, `/support`, `/status`, `/account/delete`. Illustrative future hostnames use `example.com`; no real domain assumed.

Homepage: sticky slim nav; midnight hero with headline “A clearer way to connect.”; supporting line states Android/free quota honestly; primary Get Android app, secondary How it works; static product screen (not a fake live tunnel); 3 benefit cards; three-step onboarding; actual available locations; privacy/data-use explanation; free plan card; FAQ; footer with company/support/legal/status links. Only show Google Play CTA when listing is live; before then use a clear beta interest flow with consent. No fake live-user counts, fake testimonials, urgency timers or review badges.

Public location cards come from sanitized server catalog, never node management IPs/credentials. Status reports honest component incidents and last update. Browser cannot turn on an Android system VPN; web CTA opens download/instructions, not “Connected”.

## Customer web portal

Desktop left nav: Overview / Devices / Usage / Support / Settings. Mobile top bar + accessible drawer. Overview: free plan and reset, usage meter, enrolled device, Android download link and service incident. Device screen supports revoke with confirmation and actual node removal. Usage chart is aggregate daily bytes only, not browsing activity. Support ticket screen shows status and replies. Settings includes password/session management, privacy choices and authenticated account deletion. Billing/receipts/manage subscription appear only after M10; Play-bought subscriptions link to appropriate management flow.

Design every page in loading, empty, error, offline/stale, permission-denied and success variants. Authentication redirect preserves intended destination without allowing arbitrary external redirects.

## Admin web portal

Separate admin entry and MFA. Desktop sidebar: Overview / Users / Devices / VPN Nodes / Usage & Costs / Plans / Support / Incidents / Audit / Settings. Hide navigation by role but enforce authorization server-side.

Overview shows actual node health, recent provisioning failures, quota capacity, estimated transfer spend and open incidents. No customer browsing analytics. Nodes table: region, lifecycle status, heartbeat age, measured utilization, active leases, traffic allowance remaining, provider billing period. Clearly separate “live peer”, “recent handshake” and “active account”; do not count them interchangeably.

User detail: masked email by default for limited roles, account state, plan, devices, quota aggregate, support history. Allowed actions depend on role. Suspend/revoke/delete/bulk changes require reason and confirmation; destructive errors show actionable recovery. Do not display private keys, full tokens or raw WireGuard configs. Node “Drain” stops new leases; emergency “Revoke” has a distinct warning. Admin plan edits are versioned and audited; reducing a quota does not retroactively consume undisclosed data.

## Figma handoff and UX pass criteria

Deliver pages: Foundations; Components; Android; Public web; Customer; Admin; Prototype; Handoff. Include variants, spacing, API field mapping and responsive specs at 360/390/430 dp phones, 768 px tablet and 1440 px desktop. Use Auto Layout and reusable components. Supply clickable connect, permission denial, failure, exhausted-quota, revoke-device and admin-drain flows.

Usability target (proposed, measured in M02/M09): at least 4 of 5 first-time testers can locate Connect and identify current state without prompting; at least 4 of 5 understand free limit and reset; no tester mistakes the web portal for an active device VPN. At least 48 dp targets, keyboard navigation, visible focus, TalkBack labels, reduced motion and contrast checks pass. “Better UI” is a tested objective, not a claim of beating mature networks.
