package org.example.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.Statement;

public class SQLExecutor {
    public static void executeSQLFile(String filePath, Connection connection) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            StringBuilder sb = new StringBuilder();
            String line;

            while ((line = br.readLine()) != null) {
                sb.append(line);
                if (line.trim().endsWith(";")) {
                    try (Statement stmt = connection.createStatement()) {
                        stmt.execute(sb.toString());
                        sb.setLength(0);
                    }
                }
            }
            System.out.println("SQL script executed successfully.");
        } catch (Exception e) {
            throw new RuntimeException("Failed to execute SQL script", e);
        }
    }
}