import { randomUUID } from "node:crypto";
import Fastify, { type FastifyInstance, type FastifyServerOptions } from "fastify";

/** Shared error body (`Error` in contracts/openapi.yaml). Never carries stack traces or secrets. */
export interface ApiError {
  code: string;
  message: string;
  requestId: string;
  retryAfter?: number;
}

export interface BuildAppOptions {
  /** Pino logger options. Logging is off when omitted (tests). */
  logger?: FastifyServerOptions["logger"];
  version?: string;
}

export function buildApp(options: BuildAppOptions = {}): FastifyInstance {
  const app = Fastify({
    logger: options.logger ?? false,
    // Request IDs are generated server-side; client-supplied IDs are ignored.
    requestIdHeader: false,
    genReqId: () => randomUUID(),
  });

  const version = options.version ?? "0.1.0";

  app.addHook("onRequest", async (request, reply) => {
    reply.header("x-request-id", request.id);
    reply.header("cache-control", "no-store");
  });

  // Liveness check used by CI and, later, by deployment health probes.
  app.get("/v1/health", async () => ({
    status: "ok" as const,
    service: "yashi-api",
    version,
    time: new Date().toISOString(),
  }));

  app.setNotFoundHandler(async (request, reply) => {
    const body: ApiError = {
      code: "not_found",
      message: "The requested resource was not found.",
      requestId: request.id,
    };
    return reply.code(404).send(body);
  });

  app.setErrorHandler(async (error, request, reply) => {
    const status = clientErrorStatus(error) ?? 500;
    if (status >= 500) {
      request.log.error({ err: error }, "unhandled error");
    }
    const body: ApiError = {
      code: status >= 500 ? "internal_error" : status === 404 ? "not_found" : "bad_request",
      message:
        status >= 500
          ? "Something went wrong. Please try again later."
          : "The request could not be processed.",
      requestId: request.id,
    };
    return reply.code(status).send(body);
  });

  return app;
}

/** The status of a 4xx error raised by Fastify or route code; anything else becomes a 500. */
function clientErrorStatus(error: unknown): number | undefined {
  if (typeof error !== "object" || error === null || !("statusCode" in error)) {
    return undefined;
  }
  const { statusCode } = error;
  return typeof statusCode === "number" && statusCode >= 400 && statusCode < 500 ? statusCode : undefined;
}
