package com.sena.serviciologin.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Entidad que representa a un usuario del sistema.
 * Se guarda en la tabla "usuarios" de la base de datos serviciologin_db.
 *
 * La contrasena NO se guarda en texto plano: se guarda encriptada con BCrypt.
 *
 * @author Manuel David Rodriguez Garcia - SENA ficha 3336117
 */
@Entity
@Table(name = "usuarios")
public class Usuario {

    /** Identificador unico del usuario (se genera automaticamente). */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Nombre de usuario: obligatorio y no se puede repetir. */
    @Column(nullable = false, unique = true, length = 50)
    private String usuario;

    /** Contrasena encriptada con BCrypt. */
    @Column(nullable = false, length = 100)
    private String contrasena;

    /** Constructor vacio requerido por JPA. */
    public Usuario() {
    }

    /**
     * Constructor con datos.
     *
     * @param usuario    nombre de usuario
     * @param contrasena contrasena ya encriptada
     */
    public Usuario(String usuario, String contrasena) {
        this.usuario = usuario;
        this.contrasena = contrasena;
    }

    // ===== Getters y Setters =====

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
}