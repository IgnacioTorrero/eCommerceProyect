package com.eCommerceProyect.proyect.controller;

import com.eCommerceProyect.proyect.service.PagoService;
import com.stripe.model.PaymentIntent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class PagoControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PagoService pagoService;

    @InjectMocks
    private PagoController pagoController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(pagoController).build();
    }

    @Test
    void crearPaymentIntent_exito() throws Exception {
        // Crea un PaymentIntent real o simulado
        PaymentIntent paymentIntent = new PaymentIntent();
        paymentIntent.setId("pi_test");
        paymentIntent.setAmount(1000L);

        // Simula que el servicio devuelve correctamente el PaymentIntent
        when(pagoService.crearPaymentIntent(anyDouble())).thenReturn(paymentIntent);

        // Realiza la solicitud y verifica que el estado es 200 OK y que el cuerpo contiene el ID del PaymentIntent
        mockMvc.perform(post("/api/pagos/create-payment-intent")
                        .param("monto", "100.0")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("pi_test"))
                .andExpect(jsonPath("$.amount").value(1000L)); // Verificar el contenido del PaymentIntent
    }

    @Test
    void crearPaymentIntent_error() throws Exception {
        // Simula un error en el servicio
        when(pagoService.crearPaymentIntent(anyDouble())).thenThrow(new RuntimeException("Error al crear PaymentIntent"));

        // Realiza la solicitud y verifica que el estado es 400 Bad Request y que el mensaje de error está presente
        mockMvc.perform(post("/api/pagos/create-payment-intent")
                        .param("monto", "100.0")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Error al crear PaymentIntent"));
    }
}