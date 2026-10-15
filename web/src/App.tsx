import { copy } from "./copy";

export function App() {
  return (
    <main className="shell">
      <header className="shell__header">
        <span className="wordmark">{copy.brand}</span>
        <span className="badge" role="status">
          {copy.prototypeBadge}
        </span>
      </header>

      <section className="hero" aria-labelledby="hero-title">
        <h1 id="hero-title">{copy.heroTitle}</h1>
        <p>{copy.heroBody}</p>
      </section>

      <section aria-labelledby="surfaces-title">
        <h2 id="surfaces-title">{copy.surfacesTitle}</h2>
        <ul className="surfaces">
          {copy.surfaces.map((surface) => (
            <li key={surface.name}>
              <strong>{surface.name}</strong>: {surface.description}
            </li>
          ))}
        </ul>
      </section>

      <footer className="shell__footer">{copy.footer}</footer>
    </main>
  );
}
