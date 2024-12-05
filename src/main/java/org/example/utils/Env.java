package org.example.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import io.github.cdimascio.dotenv.Dotenv;

public class Env {
    private static final String ENV_FILE = ".env";  // Aquí apuntamos a la raíz del proyecto
    private static final Map<String, String> ENV_VARS = new HashMap<>();

    // Método para cargar el archivo .env
    private static void loadEnvFile() {
        if (ENV_VARS.isEmpty()) { // Cargar solo si no se ha cargado antes
            try {
                // Leemos las líneas del archivo .env en la raíz del proyecto
                Files.lines(Paths.get(ENV_FILE)).forEach(line -> {
                    String[] parts = line.split("=", 2);
                    if (parts.length == 2) {
                        ENV_VARS.put(parts[0].trim(), parts[1].trim());
                    }
                });
            } catch (IOException e) {
                throw new RuntimeException("Failed to load environment variables from " + ENV_FILE, e);
            }
        }
    }

    public static int getInt(String key, int defaultValue) {
        loadEnvFile();
        String value = ENV_VARS.get(key);
        try {
            return value != null ? Integer.parseInt(value) : defaultValue;
        } catch (NumberFormatException e) {
            throw new RuntimeException("Invalid number format for key: " + key, e);
        }
    }

    // Método para obtener una variable de entorno
    public static String get(String key) {
        loadEnvFile(); // Asegúrate de que las variables están cargadas
        return ENV_VARS.get(key);
    }

    // Método para obtener una variable con valor por defecto
    public static String get(String key, String defaultValue) {
        loadEnvFile();
        return ENV_VARS.getOrDefault(key, defaultValue);
    }
}

