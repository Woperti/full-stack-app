package org.example.controller;
import com.google.gson.Gson;
import org.example.model.User;
import org.example.utils.HibernateUtil;
import org.example.utils.JWTUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;
import spark.Request;
import spark.Response;

import java.util.Map;

public class UserController {
    private static final Gson gson = new Gson();

    public static String signup(Request req, Response res) {
        res.type("application/json");
        User user = gson.fromJson(req.body(), User.class);

        // Validaciones antes de guardar el usuario
        if (!user.isValid()) {
            res.status(400); // Bad request
            return gson.toJson(Map.of("error", "Invalid email or password format"));
        }

        // Verificar si el usuario ya existe por el email
        try (Session session = HibernateUtil.getSession()) {
            User existingUser = session.createQuery("FROM User WHERE email = :email", User.class)
                    .setParameter("email", user.getEmail())
                    .uniqueResult();
            if (existingUser != null) {
                res.status(409); // Conflict
                return gson.toJson(Map.of("error", "User with this email already exists"));
            }

            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                session.save(user); // Guardar nuevo usuario
                transaction.commit();

                String token = JWTUtils.generateToken(user); // Generar token JWT
                res.status(201); // Created
                return gson.toJson(Map.of("message", "User registered successfully", "token", token));
            } catch (Exception e) {
                if (transaction != null) transaction.rollback();
                res.status(500);
                return gson.toJson(Map.of("error", e.getMessage()));
            }
        }
    }

    public static String login(Request req, Response res) {
        res.type("application/json");
        Map<String, String> credentials = gson.fromJson(req.body(), Map.class);
        String email = credentials.get("email");
        String password = credentials.get("password");

        try (Session session = HibernateUtil.getSession()) {
            User user = session.createQuery("FROM User WHERE email = :email", User.class)
                    .setParameter("email", email)
                    .uniqueResult();

            if (user != null && user.getPassword().equals(password)) {
                String token = JWTUtils.generateToken(user);
                return gson.toJson(Map.of("message", "Login successful", "token", token));
            } else {
                res.status(401);
                return gson.toJson(Map.of("error", "Invalid credentials"));
            }
        }
    }

    public static String logout(Request req, Response res) {
        // Obtener el token desde la cabecera Authorization
        String token = req.headers("Authorization");

        if (token == null || token.isEmpty()) {
            res.status(400); // Bad request
            return gson.toJson(Map.of("error", "Token is required"));
        }

        // Eliminar el token del lado del cliente
        // En este caso no necesitamos hacer nada en el servidor, ya que el JWT no tiene estado.
        // Simplemente informamos al cliente de que se ha hecho logout.

        // El cliente debería eliminar el token del almacenamiento local o cookies.

        return gson.toJson(Map.of("message", "User logged out successfully", "token", token));
    }
}
