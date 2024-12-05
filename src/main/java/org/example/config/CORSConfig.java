package org.example.config;
import static spark.Spark.*;

public class CORSConfig {
    public static void enableCORS(String origin) {
        options("/*", (req, res) -> {
            String accessControlRequestHeaders = req.headers("Access-Control-Request-Headers");
            String accessControlRequestMethod = req.headers("Access-Control-Request-Method");

            // Permitir el acceso a todas las solicitudes desde el origen proporcionado
            res.header("Access-Control-Allow-Origin", origin);
            res.header("Access-Control-Allow-Methods", "OPTIONS, GET, POST, PUT, DELETE");
            res.header("Access-Control-Allow-Headers", "Content-Type, Authorization, X-Requested-With, X-Custom-Header");
            res.header("Access-Control-Allow-Credentials", "true");
            res.header("Access-Control-Max-Age", "3600");

            return "OK";
        });

        // Configurar las solicitudes HTTP reales (GET, POST, PUT, DELETE, etc.)
        before((req, res) -> {
            res.header("Access-Control-Allow-Origin", origin);
            res.header("Access-Control-Allow-Methods", "OPTIONS, GET, POST, PUT, DELETE");
            res.header("Access-Control-Allow-Headers", "Content-Type, Authorization, X-Requested-With, X-Custom-Header");
            res.header("Access-Control-Allow-Credentials", "true");
        });
    }
}
