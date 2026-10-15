Draft — M01 — 15 Oct 2026 — pending review

# Hosting provider checklist

M01 task 3. Each candidate provider must answer these questions **in writing** before anything is rented.

> **Procurement status: BLOCKED.** No provider is chosen and no written answers are on file (15 Oct 2026). Nothing is rented, staging included, until a provider answers question 1 with a written yes, the reply is filed, and the owner approves the provider, region and budget (01 §4, M01, 05). A WireGuard tutorial, a traffic allowance or a pricing page is not permission (05).

## How to use

1. The owner sends the email below to each candidate's sales or support channel. The team does not contact providers on the owner's behalf without approval (00).
2. File each reply (an email or ticket export showing date and responder) in the owner's records, and link it from the provider decision record in `docs/decisions/`.
3. Fill in the comparison table only from written replies or current official terms (with link and date). Everything else stays "To verify".
4. Recheck the terms at signup and again before production.

## Questions

| # | Question (answer in writing) | Why we ask | What we need |
|---|---|---|---|
| 1 | Is a free, public consumer VPN service permitted, with members of the public routing their internet traffic through our servers? Any conditions or excluded regions? | Host approval is a launch prerequisite (01 §4); a traffic allowance is not permission (05) | An explicit yes, citing the terms section |
| 2 | How are abuse and copyright (DMCA) complaints about our IPs delivered? What response time do you expect? When do you suspend or null-route? Is "we keep no browsing logs" an acceptable answer to a complaint? | Abuse handling (M01 task 3; 04 legal review) | Process, deadlines, thresholds |
| 3 | Are inbound and outbound UDP on a custom port allowed without filtering or rate limits? How does DDoS protection treat UDP? | WireGuard uses UDP only | Yes, with any limits stated |
| 4 | Does each server get a dedicated public IPv4 and IPv6 address? At what cost? Are addresses kept or changed on rebuild? Does this vary by region? | IPv6 routing or blocking (03); node rebuilds | A per-region answer |
| 5 | What traffic is included per server and region? Is inbound counted, is it pooled, what is the overage rate, and where is usage shown? | Cost planning per region (04) | Written figures per region |
| 6 | Which regions and cities are available? Do terms, allowances or VPN permission differ by region? | First VPN region (M01 task 1) | List and differences |
| 7 | Do we get full root access, including firewall, IP forwarding, NAT and the kernel WireGuard module? | M03 task 1 | Yes |
| 8 | Account verification and billing: do you accept company accounts where the company and its owner are in different countries? Which documents, payment methods, invoices and taxes apply? Any limits on new accounts? | M01 task 3; owner-owned accounts (01 §4) | Requirements |
| 9 | Outbound SMTP: is port 25 blocked by default? Do you expect us to block SMTP from VPN users? Any other egress restrictions? | Egress abuse controls (03) | Policy |
| 10 | Data processing: is a DPA available? Where is data stored and who are the subprocessors? Do you collect flow or network telemetry on our traffic, and for how long? How are law-enforcement requests handled, and are customers notified? | Processors must be disclosed (01 §2); legal review (04) | DPA and answers |
| 11 | IP reputation: are your ranges often blocklisted? How do you handle blocklisting, and can we get replacement IPs? | User experience; abuse | Process |
| 12 | Spending: is a hard spend cap available, or only alerts? What happens when a cap is reached? | Alerts are not caps (04) | Written answer |
| 13 | *Additional:* separate projects or accounts for staging and production, scoped API tokens, MFA, and individual team accounts? | [Environments](environments.md), [access matrix](access-matrix.md) | Yes, and how |
| 14 | *Additional:* private networking between servers, a provider-level firewall, and snapshot or backup storage (with location)? | Private database (03); backup destination To decide | Options |
| 15 | *Additional:* which cloud metadata addresses (IPv4 and IPv6) and private ranges must we block from VPN clients? | [Threat model](../security/threat-model.md) T16 | Address list |

Questions 1–12 are required by M01 task 3; 13–15 are additional.

## Candidate comparison (05)

| Question | Hetzner | DigitalOcean | Vultr |
|---|---|---|---|
| 1. Public consumer VPN permitted | To verify | To verify | To verify |
| 2. Abuse/DMCA handling and expectations | To verify | To verify | To verify |
| 3. UDP allowed | To verify | To verify | To verify |
| 4. Dedicated public IPv4 and IPv6 | To verify | To verify | To verify |
| 5. Included traffic and overage | To verify | To verify | To verify |
| 6. Regions | To verify | To verify | To verify |
| 7. Root access | To verify | To verify | To verify |
| 8. Account verification and billing | To verify | To verify | To verify |
| 9. Outbound SMTP policy | To verify | To verify | To verify |
| 10. Data-processing terms/DPA | To verify | To verify | To verify |
| 11. IP reputation/blocklist handling | To verify | To verify | To verify |
| 12. Spend caps vs alerts | To verify | To verify | To verify |
| 13–15. Additional questions | To verify | To verify | To verify |
| Written reply on file (date, link) | To verify | To verify | To verify |

Background reading listed in 05. These pages are not evidence of permission or of current prices: [Hetzner WireGuard article](https://www.hetzner.com/blog/your-own-vpn-server-with-wireguard-what-are-the-benefits/), [Hetzner cloud listing](https://www.hetzner.com/cloud/cost-optimized/), [DigitalOcean droplet pricing](https://www.digitalocean.com/pricing/droplets), [DigitalOcean bandwidth billing](https://docs.digitalocean.com/platform/billing/bandwidth/), [Vultr WireGuard guide](https://docs.vultr.com/how-to-install-wireguard-vpn-on-ubuntu-24-04).

## Email to send

Fill in the bracketed fields, then send from an owner-controlled company mailbox.

> **Subject:** Written confirmation request: free public VPN service (WireGuard) on [Provider]
>
> Hello [Provider] team,
>
> [Company legal name], registered in [country], is evaluating [Provider] to host Yashi VPN, a free consumer VPN for Android that is in development. Before we create any servers, we need your written confirmation that this use is permitted.
>
> - **Service:** members of the public install our Android app and route their internet traffic through our servers. Free plan: 5 GiB per account per month, one device, no ads.
> - **Technology:** WireGuard over UDP (one UDP port per VPN server) on Ubuntu LTS, with IP forwarding and NAT, managed by us with root access. Account and control servers are separate from the VPN servers.
> - **Expected traffic:** at first, two small servers (one VPN, one control) for internal testing with a few devices. Later, a controlled beta (proposed 100–300 testers), then a public free tier. As an illustration only, 1,000 monthly active users at the 5 GiB limit would mean about 5,000 GiB of VPN traffic per month. We will share measured figures before scaling.
> - **Abuse handling:** a monitored abuse contact, [abuse contact email], answered within [response time]. We plan to block outbound SMTP (port 25) from VPN users and to suspend accounts that break our acceptable-use policy.
> - **Logging:** we do not log users' browsing history, destination addresses, DNS queries or traffic content. We keep account data, per-account byte counters for the free quota and minimal operational records.
>
> Please confirm in writing:
>
> 1. That operating this free public consumer VPN service is permitted under your terms and acceptable-use policy, citing the relevant section and any conditions or excluded regions.
> 2. How you handle abuse and copyright (DMCA) complaints about our IP addresses, and what response times you expect.
>
> Brief answers on the following would also help: UDP filtering; dedicated public IPv4 and IPv6 per server; included traffic and overage per region; available regions; root access; verification and payment methods for company accounts; outbound SMTP policy; your data processing agreement; IP blocklist handling; and whether hard spending caps are available.
>
> We will not create any resources until we have your reply.
>
> Kind regards,
> [Name], [Role], [Company legal name]
> [Contact email]

After a reply: record the answers in the table, file the reply, and ask the owner to approve or reject the provider. A "no" or an unclear answer keeps procurement blocked for that provider.
