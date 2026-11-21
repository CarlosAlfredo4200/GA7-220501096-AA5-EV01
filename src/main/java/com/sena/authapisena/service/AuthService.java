package com.sena.authapisena.service;

import com.sena.authapisena.dto.LoginRequest;
import com.sena.authapisena.dto.RegistroRequest;
import com.sena.authapisena.model.Usuario;
import com.sena.authapisena.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Servicio encargado de gestionar
 * el registro y la autenticación de usuarios.
 */
@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Constructor para inyectar las dependencias.
     *
     * @param usuarioRepository repositorio de usuarios.
     * @param passwordEncoder codificador de contraseñas.
     */
    public AuthService(
            UsuarioRepository usuarioRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Registra un nuevo usuario en el sistema.
     * La contraseña se almacena cifrada mediante BCrypt.
     *
     * @param request datos enviados por el usuario.
     * @return true si fue registrado; false si ya existe.
     */
    @Transactional
    public boolean registrarUsuario(RegistroRequest request) {

        String nombreUsuario = request.getUsuario().trim();

        if (usuarioRepository.existsByNombreUsuarioIgnoreCase(nombreUsuario)) {
            return false;
        }

        String passwordCifrada = passwordEncoder.encode(request.getPassword());

        Usuario usuario = new Usuario(nombreUsuario, passwordCifrada);
        usuarioRepository.save(usuario);

        return true;
    }

    /**
     * Verifica las credenciales recibidas durante el inicio de sesión.
     *
     * @param request usuario y contraseña.
     * @return true si la autenticación es correcta.
     */
    @Transactional(readOnly = true)
    public boolean autenticar(LoginRequest request) {

        String nombreUsuario = request.getUsuario().trim();

        return usuarioRepository
                .findByNombreUsuarioIgnoreCase(nombreUsuario)
                .map(usuario -> passwordEncoder.matches(
                        request.getPassword(),
                        usuario.getPassword()
                ))
                .orElse(false);
    }
}
