package com.sena.serviciologin.repositorio;

import com.sena.serviciologin.modelo.Usuario;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositorio de usuarios.
 * Hereda de JpaRepository los metodos basicos (guardar, buscar, eliminar)
 * sin necesidad de escribir SQL.
 *
 * @author Manuel David Rodriguez Garcia - SENA ficha 3336117
 */
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    /**
     * Busca un usuario por su nombre de usuario.
     *
     * @param usuario nombre de usuario a buscar
     * @return el usuario si existe, o vacio si no existe
     */
    Optional<Usuario> findByUsuario(String usuario);

    /**
     * Verifica si ya existe un usuario con ese nombre.
     *
     * @param usuario nombre de usuario
     * @return true si ya existe
     */
    boolean existsByUsuario(String usuario);
}