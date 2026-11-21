package com.sena.authapisena.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Configuración encargada de proporcionar
 * el codificador de contraseñas.
 */
@Configuration
public class PasswordConfig {

    /**
     * Crea el componente utilizado para cifrar
     * y verificar contraseñas mediante BCrypt.
     *
     * @return codificador de contraseñas.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}