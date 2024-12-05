package org.example;
import com.google.gson.Gson;
import org.example.config.CORSConfig;
import org.example.config.Database;
import org.example.routes.UserRoutes;
import org.example.utils.Env;
import org.example.utils.Message;

import java.sql.Connection;
import java.sql.SQLException;
import static spark.Spark.*;
import static spark.Spark.port;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        int port = Env.getInt("APP_PORT", 8080);

        port(port);
        Gson gson = new Gson();
        CORSConfig.enableCORS("*");

        try (Connection connection = Database.getConnection()) {
            if (connection != null) {
                System.out.println("¡Conexión exitosa a la base de datos!");
            }
        } catch (SQLException e) {
            System.out.println("Error al conectar a la base de datos: " + e.getMessage());
        }

        //Routes
        get("/", (req, res) -> {
            res.type("application/json");
            return gson.toJson(new Message("Welcome to Api v1"));
        });
        UserRoutes.defineRoutes();
    }
}