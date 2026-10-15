import { afterAll, describe, expect, it } from "vitest";
import { buildApp } from "../src/app.js";

const app = buildApp();

afterAll(async () => {
  await app.close();
});

describe("GET /v1/health", () => {
  it("returns ok with service metadata and a UTC timestamp", async () => {
    const res = await app.inject({ method: "GET", url: "/v1/health" });

    expect(res.statusCode).toBe(200);
    const body = res.json<{ status: string; service: string; version: string; time: string }>();
    expect(body).toMatchObject({ status: "ok", service: "yashi-api" });
    expect(new Date(body.time).toISOString()).toBe(body.time);
    expect(res.headers["cache-control"]).toBe("no-store");
    expect(res.headers["x-request-id"]).toEqual(expect.any(String));
  });
});

describe("error contract", () => {
  it("returns the shared error shape for unknown routes", async () => {
    const res = await app.inject({ method: "GET", url: "/v1/does-not-exist" });

    expect(res.statusCode).toBe(404);
    expect(res.json()).toEqual({
      code: "not_found",
      message: expect.any(String),
      requestId: res.headers["x-request-id"],
    });
  });

  it("ignores client-supplied request IDs", async () => {
    const res = await app.inject({
      method: "GET",
      url: "/v1/health",
      headers: { "x-request-id": "client-chosen-id" },
    });

    expect(res.headers["x-request-id"]).not.toBe("client-chosen-id");
  });

  it("does not leak internal error details", async () => {
    const local = buildApp();
    local.get("/v1/boom", async () => {
      throw new Error("sensitive internal detail");
    });

    const res = await local.inject({ method: "GET", url: "/v1/boom" });

    expect(res.statusCode).toBe(500);
    expect(res.body).not.toContain("sensitive internal detail");
    expect(res.json()).toMatchObject({ code: "internal_error" });
    await local.close();
  });
});
