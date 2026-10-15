// Customer-facing text lives in resources, not in components (team pack 01 §1).
// English is the default; Nepali is added only after translation review.
export const copy = {
  brand: "Yashi VPN",
  prototypeBadge: "Engineering shell, not a live product",
  heroTitle: "A clearer way to connect.",
  heroBody:
    "This is the M01 web shell. The public site and customer portal arrive in M06 and the admin portal in M07. Nothing on this page connects a VPN.",
  surfacesTitle: "Planned web surfaces",
  surfaces: [
    {
      name: "Public site",
      description: "Download, features, locations, plans, privacy, terms, support and status (M06)",
    },
    { name: "Customer portal", description: "Overview, devices, usage, support and settings (M06)" },
    { name: "Admin portal", description: "Separate entry with MFA and role-based access (M07)" },
  ],
  footer: "Prototype build. No real customer data.",
} as const;
