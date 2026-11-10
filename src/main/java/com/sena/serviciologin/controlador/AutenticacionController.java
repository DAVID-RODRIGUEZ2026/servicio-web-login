package com.sena.serviciologin.controlador;

import com.sena.serviciologin.servicio.UsuarioService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador REST del servicio web de registro e inicio de sesion.
 *
 * Endpoints:
 *  - POST http://localhost:8081/api/registro : registra un usuario nuevo.
 *  - POST http://localhost:8081/api/login    : autentica un usuario.
 *
 * Ambos reciben un JSON con este formato:
 *  { "usuario": "david", "contrasena": "12345" }
 *
 * @author Manuel David Rodriguez Garcia - SENA ficha 3336117
 */
@RestController
@RequestMapping("/api")
public class AutenticacionController {

    /** Servicio con la logica de registro y autenticacion. */
    private final UsuarioService usuarioService;

    /**
     * Inyeccion de dependencias por constructor.
     *
     * @param usuarioService servicio de usuarios
     */
    public AutenticacionController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    /**
     * Datos que llegan en el cuerpo (JSON) de la peticion.
     * Ambos campos son obligatorios.
     */
    public record Credenciales(
            @NotBlank(message = "El usuario es obligatorio") String usuario,
            @NotBlank(message = "La contrasena es obligatoria") String contrasena) {
    }

    /**
     * Registro de un usuario nuevo.
     *
     * @param datos usuario y contrasena
     * @return 201 si se registro, 409 si el usuario ya existe
     */
    @PostMapping("/registro")
    public ResponseEntity<Map<String, String>> registrar(@Valid @RequestBody Credenciales datos) {
        boolean registrado = usuarioService.registrar(datos.usuario(), datos.contrasena());

        if (registrado) {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(Map.of("mensaje", "Usuario registrado correctamente"));
        }
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Map.of("mensaje", "Error: el usuario ya existe"));
    }

    /**
     * Inicio de sesion.
     *
     * @param datos usuario y contrasena
     * @return 200 si la autenticacion es correcta, 401 si es incorrecta
     */
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@Valid @RequestBody Credenciales datos) {
        boolean autenticado = usuarioService.autenticar(datos.usuario(), datos.contrasena());

        if (autenticado) {
            return ResponseEntity.ok(Map.of("mensaje", "Autenticacion satisfactoria"));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("mensaje", "Error en la autenticacion"));
    }
}