package org.example.config;

import org.example.utils.Env;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    static String dbURL = Env.get("DATABASE_URL");
    static String dbUser = Env.get("DATABASE_USER");
    static String dbPassword = Env.get("DATABASE_PASSWORD");

    private static final String URL = dbURL;
    private static final String USER = dbUser;
    private static final String PASSWORD = dbPassword;

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
