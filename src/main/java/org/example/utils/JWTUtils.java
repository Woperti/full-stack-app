package org.example.utils;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.Claims;
import org.example.model.User;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JWTUtils {
    private static final long EXPIRATION_TIME = 86400000; // 1 día
    private static final String SECRET_KEY = Env.get("JWT_SECRET");

    public static String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", user.getRoleAsString()); // Agrega el rol como claim

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getEmail()) // El email es el "subject" del token
                .setIssuedAt(new Date()) // Fecha de emisión
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // Expiración en 1 día
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY) // Firma con clave secreta
                .compact();
    }

    public static void verifyToken(String token) throws JwtException {
        // Verifica la validez del token (firma, expiración, etc.)
        Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
    }

    public static Claims validateToken(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    public static String getRoleFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY) // La clave para descifrar el token
                .parseClaimsJws(token)
                .getBody(); // Obtén el cuerpo del token (claims)

        // Extrae el rol del token
        return claims.get("role", String.class);
    }
}
