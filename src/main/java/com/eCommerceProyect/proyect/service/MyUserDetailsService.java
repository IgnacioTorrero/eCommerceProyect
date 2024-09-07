package com.eCommerceProyect.proyect.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Aquí es donde buscas el usuario por su username en la base de datos
        // Debes devolver un objeto que implemente UserDetails
        return new org.springframework.security.core.userdetails.User(
                "testUser",  // Usuario hardcodeado como ejemplo
                "$2a$10$Xbq6EYgH/FAF7nnslP0cSeWjKrhX0L.RInX5OQo/k3fRBsfn.P0QS",  // Contraseña encriptada con BCrypt
                new ArrayList<>()  // Roles o permisos, aquí vacíos por simplicidad
        );
    }
}
