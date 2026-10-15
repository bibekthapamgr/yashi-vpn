import { buildApp } from "./app.js";

const host = process.env.API_HOST ?? "127.0.0.1";
const port = Number(process.env.API_PORT ?? 8080);

const app = buildApp({
  logger: {
    level: process.env.LOG_LEVEL ?? "info",
    // Privacy (team pack 01 §2, 04): never log client IPs, raw URLs with query strings,
    // auth headers, cookies or bodies. Log the matched route pattern only.
    serializers: {
      req: (request) => ({
        id: request.id,
        method: request.method,
        route: request.routeOptions.url ?? "unmatched",
      }),
      res: (reply) => ({ statusCode: reply.statusCode }),
    },
  },
});

const shutdown = async (signal: string): Promise<void> => {
  app.log.info({ signal }, "shutting down");
  await app.close();
  process.exit(0);
};
process.once("SIGINT", () => void shutdown("SIGINT"));
process.once("SIGTERM", () => void shutdown("SIGTERM"));

try {
  await app.listen({ host, port });
} catch (error) {
  app.log.error(error);
  process.exit(1);
}
