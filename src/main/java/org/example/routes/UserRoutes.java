package org.example.routes;

// src/routes/UserRoutes.java
import org.example.controller.UserController;
import org.example.middlewares.AuthMiddleware;
import spark.Route;
import static spark.Spark.*;

public class UserRoutes {
    public static void defineRoutes() {
        path("/api/v1/users", () -> {
            post("/signup", UserController::signup);
            post("/login", UserController::login);

            before("/protected/*", AuthMiddleware.authenticate);
        });
    }
}
