package com.eCommerceProyect.proyect.service;

import com.eCommerceProyect.proyect.model.Usuario;
import com.eCommerceProyect.proyect.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.dao.DataIntegrityViolationException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UsuarioService usuarioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void registrarUsuario_usuarioExistente() {
        Usuario usuario = new Usuario();
        usuario.setNombre("usuarioExistente");

        // Simular que el usuario ya existe
        when(usuarioRepository.existsByNombre("usuarioExistente")).thenReturn(true);

        // Verificar que se lanza una excepción
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            usuarioService.registrarUsuario(usuario);
        });

        assertEquals("El nombre de usuario ya está en uso", exception.getMessage());
        verify(usuarioRepository, never()).save(any(Usuario.class));
    }

    @Test
    void registrarUsuario_exito() {
        Usuario usuario = new Usuario();
        usuario.setNombre("nuevoUsuario");
        usuario.setContraseña("mi_contraseña");

        // Simular que el usuario no existe
        when(usuarioRepository.existsByNombre("nuevoUsuario")).thenReturn(false);
        // Simular la codificación de la contraseña
        when(passwordEncoder.encode("mi_contraseña")).thenReturn("contraseña_codificada");
        // Simular el guardado del usuario
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        Usuario usuarioRegistrado = usuarioService.registrarUsuario(usuario);

        assertEquals("contraseña_codificada", usuarioRegistrado.getContraseña());
        verify(usuarioRepository, times(1)).save(usuario);
    }

    @Test
    void registrarUsuario_errorDeBD() {
        Usuario usuario = new Usuario();
        usuario.setNombre("nuevoUsuario");
        usuario.setContraseña("mi_contraseña");

        when(usuarioRepository.existsByNombre("nuevoUsuario")).thenReturn(false);
        when(passwordEncoder.encode("mi_contraseña")).thenReturn("contraseña_codificada");

        // Simular que ocurre un error de base de datos
        when(usuarioRepository.save(any(Usuario.class))).thenThrow(DataIntegrityViolationException.class);

        // Verificar que se lanza la excepción correcta
        assertThrows(RuntimeException.class, () -> {
            usuarioService.registrarUsuario(usuario);
        });
    }
}

