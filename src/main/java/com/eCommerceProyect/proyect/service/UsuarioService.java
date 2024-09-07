package com.eCommerceProyect.proyect.service;

import com.eCommerceProyect.proyect.model.Usuario;
import com.eCommerceProyect.proyect.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.dao.DataAccessException;

import java.util.HashSet;
import java.util.Optional;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Usuario registrarUsuario(Usuario usuario) {
        // Validar que el usuario no exista por nombre de usuario o email
        if (usuarioRepository.existsByNombre(usuario.getNombre())) {
            throw new IllegalArgumentException("El nombre de usuario ya está en uso");
        }

        // Codificar la contraseña
        usuario.setContraseña(passwordEncoder.encode(usuario.getContraseña()));

        // Asignar roles por defecto (siempre es recomendable extraer roles a un enum o configuración)
        usuario.setRoles(new HashSet<>()); // Aquí podrías definir el rol por defecto como "ROLE_USER"

        try {
            // Guardar el usuario en la base de datos
            return usuarioRepository.save(usuario);
        } catch (DataAccessException e) {
            throw new RuntimeException("Error al registrar el usuario", e);
        }
    }

    // Otros métodos como iniciar sesión, recuperación de contraseña, etc.
}