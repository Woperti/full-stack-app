package org.example.utils;

import java.util.regex.Pattern;

public class Validations {
    private static final String EMAIL_REGEX = "^[\\w.-]+@[\\w-]+\\.[a-zA-Z]{2,}$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    public static boolean isValidEmail(String email) {
        if (email == null || email.isEmpty()) {
            return false; // Considerar nulos o cadenas vacías como inválidas
        }
        return EMAIL_PATTERN.matcher(email).matches();
    }
}
