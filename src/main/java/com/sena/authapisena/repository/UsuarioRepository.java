package com.sena.authapisena.repository;

import com.sena.authapisena.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositorio encargado del acceso a los datos
 * de los usuarios registrados.
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    /**
     * Verifica si existe un usuario sin diferenciar mayúsculas y minúsculas.
     *
     * @param nombreUsuario nombre de usuario a consultar.
     * @return true si el usuario ya existe.
     */
    boolean existsByNombreUsuarioIgnoreCase(String nombreUsuario);

    /**
     * Busca un usuario sin diferenciar mayúsculas y minúsculas.
     *
     * @param nombreUsuario nombre de usuario a consultar.
     * @return usuario encontrado, si existe.
     */
    Optional<Usuario> findByNombreUsuarioIgnoreCase(String nombreUsuario);
}
