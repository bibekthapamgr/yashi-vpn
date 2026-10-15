# ADR-0008: Transactional email provider

- **Status:** Open. Decide before M04 (verification and reset emails)
- **Milestone:** M01 task 7
- **Deciders:** Owner + backend lead

## Requirements

- Sends only transactional email: verification, password reset, security notices and support replies. No marketing in v1.
- A data processing agreement, and processing locations acceptable for a global audience.
- SPF, DKIM and DMARC on the owner's domain (after the domain is bought).
- Link and open tracking turned off for authentication emails.
- Delivery logs with short retention, listed in the privacy policy and the data inventory.
- Fits the budget at beta volume.

## Candidates to evaluate

Amazon SES, Postmark, Resend and Mailgun, or another provider that meets the requirements. Check each one's current terms, DPA and pricing at decision time. Nothing here is a recommendation yet.

## Consequences

- The chosen provider is added to `docs/security/data-inventory.md`, the privacy policy and the access matrix.
- Its API key goes into the secrets inventory, never into Git.
