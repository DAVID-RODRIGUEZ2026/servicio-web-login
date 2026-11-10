package com.sena.serviciologin.servicio;

import com.sena.serviciologin.modelo.Usuario;
import com.sena.serviciologin.repositorio.UsuarioRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Servicio con la logica de negocio del registro e inicio de sesion.
 *
 * - registrar: guarda un usuario nuevo con la contrasena encriptada.
 * - autenticar: verifica si el usuario y la contrasena son correctos.
 *
 * @author Manuel David Rodriguez Garcia - SENA ficha 3336117
 */
@Service
public class UsuarioService {

    /** Repositorio para acceder a la tabla de usuarios. */
    private final UsuarioRepository usuarioRepository;

    /** Encriptador de contrasenas con el algoritmo BCrypt. */
    private final BCryptPasswordEncoder encriptador = new BCryptPasswordEncoder();

    /**
     * Inyeccion de dependencias por constructor.
     *
     * @param usuarioRepository repositorio de usuarios
     */
    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    /**
     * Registra un usuario nuevo.
     *
     * @param usuario    nombre de usuario
     * @param contrasena contrasena en texto plano (se encripta antes de guardar)
     * @return true si se registro, false si el usuario ya existia
     */
    public boolean registrar(String usuario, String contrasena) {
        // Si el usuario ya existe, no se puede registrar de nuevo
        if (usuarioRepository.existsByUsuario(usuario)) {
            return false;
        }
        // Se encripta la contrasena y se guarda el usuario
        String contrasenaEncriptada = encriptador.encode(contrasena);
        usuarioRepository.save(new Usuario(usuario, contrasenaEncriptada));
        return true;
    }

    /**
     * Verifica las credenciales de un usuario (inicio de sesion).
     *
     * @param usuario    nombre de usuario
     * @param contrasena contrasena en texto plano
     * @return true si el usuario existe y la contrasena coincide
     */
    public boolean autenticar(String usuario, String contrasena) {
        // Se busca el usuario; si existe, se compara la contrasena con la encriptada
        return usuarioRepository.findByUsuario(usuario)
                .map(u -> encriptador.matches(contrasena, u.getContrasena()))
                .orElse(false);
    }
}