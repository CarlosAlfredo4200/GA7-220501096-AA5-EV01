package com.sena.authapisena;

import com.sena.authapisena.dto.LoginRequest;
import com.sena.authapisena.dto.RegistroRequest;
import com.sena.authapisena.model.Usuario;
import com.sena.authapisena.repository.UsuarioRepository;
import com.sena.authapisena.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Pruebas unitarias del servicio de autenticación.
 */
@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    private AuthService authService;

    @BeforeEach
    void setUp() {
        authService = new AuthService(usuarioRepository, passwordEncoder);
    }

    @Test
    void debeRegistrarUsuarioNuevo() {
        RegistroRequest request = new RegistroRequest();
        request.setUsuario("carlos");
        request.setPassword("Carlos123");

        when(usuarioRepository.existsByNombreUsuarioIgnoreCase("carlos"))
                .thenReturn(false);
        when(passwordEncoder.encode("Carlos123"))
                .thenReturn("password-cifrada");

        boolean resultado = authService.registrarUsuario(request);

        assertTrue(resultado);
        verify(usuarioRepository).save(any(Usuario.class));
    }

    @Test
    void noDebeRegistrarUsuarioDuplicado() {
        RegistroRequest request = new RegistroRequest();
        request.setUsuario("carlos");
        request.setPassword("Carlos123");

        when(usuarioRepository.existsByNombreUsuarioIgnoreCase("carlos"))
                .thenReturn(true);

        boolean resultado = authService.registrarUsuario(request);

        assertFalse(resultado);
        verify(usuarioRepository, never()).save(any(Usuario.class));
    }

    @Test
    void debeAutenticarCredencialesCorrectas() {
        LoginRequest request = new LoginRequest();
        request.setUsuario("carlos");
        request.setPassword("Carlos123");

        Usuario usuario = new Usuario("carlos", "hash");

        when(usuarioRepository.findByNombreUsuarioIgnoreCase("carlos"))
                .thenReturn(Optional.of(usuario));
        when(passwordEncoder.matches("Carlos123", "hash"))
                .thenReturn(true);

        assertTrue(authService.autenticar(request));
    }

    @Test
    void debeRechazarPasswordIncorrecta() {
        LoginRequest request = new LoginRequest();
        request.setUsuario("carlos");
        request.setPassword("Incorrecta123");

        Usuario usuario = new Usuario("carlos", "hash");

        when(usuarioRepository.findByNombreUsuarioIgnoreCase("carlos"))
                .thenReturn(Optional.of(usuario));
        when(passwordEncoder.matches("Incorrecta123", "hash"))
                .thenReturn(false);

        assertFalse(authService.autenticar(request));
    }
}
