package com.eCommerceProyect.proyect.filter;

import com.eCommerceProyect.proyect.service.MyUserDetailsService;
import com.eCommerceProyect.proyect.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import java.io.IOException;

import static org.mockito.Mockito.*;

class JwtRequestFilterTest {

    @Mock
    private MyUserDetailsService userDetailsService;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private JwtRequestFilter jwtRequestFilter;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain chain;

    @Mock
    private UserDetails userDetails;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        SecurityContextHolder.clearContext(); // Limpia el contexto de seguridad antes de cada prueba
    }

    @Test
    void doFilterInternal_validJwt() throws ServletException, IOException {
        when(request.getHeader("Authorization")).thenReturn("Bearer validJwtToken");
        when(jwtUtil.extractUsername("validJwtToken")).thenReturn("testUser");
        when(userDetailsService.loadUserByUsername("testUser")).thenReturn(userDetails);
        when(jwtUtil.validateToken("validJwtToken", "testUser")).thenReturn(true);

        jwtRequestFilter.doFilterInternal(request, response, chain);

        verify(jwtUtil, times(1)).extractUsername("validJwtToken");
        verify(userDetailsService, times(1)).loadUserByUsername("testUser");
        verify(jwtUtil, times(1)).validateToken("validJwtToken", "testUser");
        verify(chain, times(1)).doFilter(request, response);
    }

    @Test
    void doFilterInternal_invalidJwt() throws ServletException, IOException {
        when(request.getHeader("Authorization")).thenReturn("Bearer invalidJwtToken");
        when(jwtUtil.extractUsername("invalidJwtToken")).thenReturn("testUser");
        when(jwtUtil.validateToken("invalidJwtToken", "testUser")).thenReturn(false);

        jwtRequestFilter.doFilterInternal(request, response, chain);

        verify(jwtUtil, times(1)).extractUsername("invalidJwtToken");
        verify(jwtUtil, times(1)).validateToken("invalidJwtToken", "testUser");
        verify(chain, times(1)).doFilter(request, response);
    }

    @Test
    void doFilterInternal_noJwt() throws ServletException, IOException {
        when(request.getHeader("Authorization")).thenReturn(null);

        jwtRequestFilter.doFilterInternal(request, response, chain);

        verify(jwtUtil, never()).extractUsername(anyString());
        verify(chain, times(1)).doFilter(request, response);
    }
}
