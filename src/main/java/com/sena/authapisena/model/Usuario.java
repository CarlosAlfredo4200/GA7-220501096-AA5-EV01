package com.sena.authapisena.model;

import jakarta.persistence.*;

/**
 * Entidad que representa un usuario registrado
 * en el sistema de autenticación.
 */
@Entity
@Table(
        name = "usuario",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "nombre_usuario")
        }
)
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(
            name = "nombre_usuario",
            nullable = false,
            length = 50,
            unique = true
    )
    private String nombreUsuario;

    @Column(
            nullable = false,
            length = 100
    )
    private String password;

    /**
     * Constructor vacío requerido por JPA.
     */
    public Usuario() {
    }

    /**
     * Constructor utilizado para registrar usuarios.
     *
     * @param nombreUsuario nombre del usuario.
     * @param password contraseña cifrada.
     */
    public Usuario(String nombreUsuario, String password) {
        this.nombreUsuario = nombreUsuario;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}