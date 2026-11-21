package com.sena.authapisena.controller;

import com.sena.authapisena.dto.LoginRequest;
import com.sena.authapisena.dto.MensajeResponse;
import com.sena.authapisena.dto.RegistroRequest;
import com.sena.authapisena.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Controlador REST encargado de exponer
 * los servicios de registro e inicio de sesión.
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    /**
     * Constructor para inyectar el servicio de autenticación.
     *
     * @param authService servicio de autenticación.
     */
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * Endpoint informativo para comprobar que la API está disponible.
     *
     * @return información básica de los servicios publicados.
     */
    @GetMapping
    public Map<String, String> estado() {
        return Map.of(
                "estado", "API disponible",
                "registro", "POST /api/auth/registro",
                "login", "POST /api/auth/login"
        );
    }

    /**
     * Registra un nuevo usuario.
     *
     * @param request datos del registro.
     * @return 201 si se registra o 409 si el usuario ya existe.
     */
    @PostMapping("/registro")
    public ResponseEntity<MensajeResponse> registrar(
            @Valid @RequestBody RegistroRequest request
    ) {

        boolean registrado = authService.registrarUsuario(request);

        if (!registrado) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(new MensajeResponse(
                            "El usuario ya se encuentra registrado."
                    ));
        }

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new MensajeResponse(
                        "Usuario registrado correctamente."
                ));
    }

    /**
     * Realiza el inicio de sesión verificando usuario y contraseña.
     *
     * @param request credenciales del usuario.
     * @return 200 si son correctas o 401 si la autenticación falla.
     */
    @PostMapping("/login")
    public ResponseEntity<MensajeResponse> login(
            @Valid @RequestBody LoginRequest request
    ) {

        boolean autenticado = authService.autenticar(request);

        if (!autenticado) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new MensajeResponse(
                            "Error en la autenticación."
                    ));
        }

        return ResponseEntity.ok(
                new MensajeResponse("Autenticación satisfactoria.")
        );
    }
}
