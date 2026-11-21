package com.sena.authapisena.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO utilizado para recibir los datos
 * necesarios para registrar un usuario.
 */
public class RegistroRequest {

    @NotBlank(message = "El usuario es obligatorio.")
    @Size(
            min = 4,
            max = 50,
            message = "El usuario debe tener entre 4 y 50 caracteres."
    )
    private String usuario;

    @NotBlank(message = "La contraseña es obligatoria.")
    @Size(
            min = 6,
            max = 100,
            message = "La contraseña debe tener entre 6 y 100 caracteres."
    )
    private String password;

    public RegistroRequest() {
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
