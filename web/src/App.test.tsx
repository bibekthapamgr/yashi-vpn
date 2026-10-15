import { render, screen } from "@testing-library/react";
import { describe, expect, it } from "vitest";
import { App } from "./App";

describe("App shell", () => {
  it("labels itself as an engineering shell, not a live product", () => {
    render(<App />);
    expect(screen.getByRole("status")).toHaveTextContent(/not a live product/i);
  });

  it("never renders a VPN connect control in the browser (team pack 02, M06)", () => {
    render(<App />);
    expect(screen.queryByRole("button", { name: /connect/i })).toBeNull();
    expect(screen.queryByText(/^connected$/i)).toBeNull();
  });

  it("has exactly one top-level heading", () => {
    render(<App />);
    expect(screen.getAllByRole("heading", { level: 1 })).toHaveLength(1);
  });
});
