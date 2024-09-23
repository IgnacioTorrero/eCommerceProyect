package com.eCommerceProyect.proyect.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class JwtUtilTest {

    @InjectMocks
    private JwtUtil jwtUtil;

    private String token;

    @BeforeEach
    void setUp() {
        jwtUtil = new JwtUtil();
        token = jwtUtil.generateToken("testUser");
    }

    @Test
    void extractUsername() {
        String username = jwtUtil.extractUsername(token);
        assertEquals("testUser", username);
    }

    @Test
    void extractExpiration() {
        Date expiration = jwtUtil.extractExpiration(token);
        assertTrue(expiration.after(new Date()));
    }

    @Test
    void generateToken() {
        String token = jwtUtil.generateToken("newUser");
        assertNotNull(token);
    }

    @Test
    void validateToken_validToken() {
        boolean isValid = jwtUtil.validateToken(token, "testUser");
        assertTrue(isValid);
    }

    @Test
    void validateToken_invalidToken() {
        // Generamos un token válido
        String validToken = jwtUtil.generateToken("testUser");

        // Corruptamos el token (modificamos la última parte que es la firma)
        String invalidToken = validToken.substring(0, validToken.lastIndexOf('.') + 1) + "invalidSignature";

        // Validamos que el token corrupto sea detectado como inválido
        boolean isValid = jwtUtil.validateToken(invalidToken, "testUser");
        assertFalse(isValid);
    }
}
