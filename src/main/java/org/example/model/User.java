package org.example.model;
import javax.persistence.*;
import org.example.utils.Roles;
import org.example.utils.Validations;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Roles role;

    @Column(name = "password", nullable = false)
    private String password;

    public User() {}

    public User(String name, String email, Roles role, String password) {
        this.name = name;
        this.email = email;
        this.role = role;
        this.password = password;
    }

    // Getters y setters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Roles getRole() {
        return role;
    }

    public String getPassword() {
        return password;
    }

    public String getRoleAsString() {
        return role != null ? role.name() : null;
    }

    // Validaciones
    public boolean isValid() {
        return isValidEmail() && isValidPassword();
    }

    private boolean isValidEmail() {
        return Validations.isValidEmail(email) && email.endsWith("@bachilleres.edu.mx");
    }

    private boolean isValidPassword() {
        return password != null && password.length() >= 8; // Validación de contraseña con mínimo 8 caracteres
    }
}