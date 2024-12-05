package org.example.middlewares;

import io.jsonwebtoken.Claims;
import org.example.utils.JWTUtils;
import spark.Filter;
import spark.Request;
import spark.Response;

import static spark.Spark.halt;

public class AuthMiddleware {
    public static Filter authenticate = (Request req, Response res) -> {
        String authHeader = req.headers("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            res.status(401);
            halt("Unauthorized: Missing or invalid token");
        }

        String token = authHeader.substring(7); // Elimina el prefijo "Bearer "

        try {
            JWTUtils.verifyToken(token); // Método para validar el token
        } catch (Exception e) {
            res.status(401);
            halt("Unauthorized: " + e.getMessage());
        }
    };

    public static Filter checkRole(String requiredRole) {
        return (Request req, Response res) -> {
            String authHeader = req.headers("Authorization");

            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                res.status(401);
                halt("Unauthorized: Missing or invalid token");
            }

            String token = authHeader.substring(7);

            try {
                String userRole = JWTUtils.getRoleFromToken(token); // Obtén el rol del token
                if (!userRole.equals(requiredRole)) {
                    res.status(403);
                    halt("Forbidden: Insufficient permissions");
                }
            } catch (Exception e) {
                res.status(401);
                halt("Unauthorized: " + e.getMessage());
            }
        };
    };
}

