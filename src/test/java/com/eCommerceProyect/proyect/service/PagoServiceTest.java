package com.eCommerceProyect.proyect.service;

import com.stripe.Stripe;
import com.stripe.model.PaymentIntent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

class PagoServiceTest {

    @InjectMocks
    private PagoService pagoService;

    @BeforeEach
    void setUp() {
        // Inicializar los mocks con Mockito
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void init_deberiaInicializarStripeApiKey() {
        // Usar MockedStatic para simular la asignación del valor estático de Stripe.apiKey
        try (MockedStatic<Stripe> mockedStripe = mockStatic(Stripe.class)) {
            pagoService.init();  // Llamamos al metodo @PostConstruct manualmente
            mockedStripe.verify(() -> Stripe.apiKey = "tu_clave_secreta_de_stripe");
        }
    }

    @Test
    void crearPaymentIntent_exito() throws Exception {
        // Crea un PaymentIntent simulado
        PaymentIntent paymentIntent = new PaymentIntent();
        paymentIntent.setId("pi_test");
        paymentIntent.setAmount(1000L);

        // Simula la llamada a Stripe con MockedStatic
        try (MockedStatic<PaymentIntent> mockedStatic = mockStatic(PaymentIntent.class)) {
            when(PaymentIntent.create(anyMap())).thenReturn(paymentIntent);

            PaymentIntent result = pagoService.crearPaymentIntent(10.0);

            assertNotNull(result);
            assertEquals("pi_test", result.getId());
        }
    }

    @Test
    void crearPaymentIntent_error() throws Exception {
        // Simula la excepción de Stripe
        try (MockedStatic<PaymentIntent> mockedStatic = mockStatic(PaymentIntent.class)) {
            when(PaymentIntent.create(anyMap())).thenThrow(new RuntimeException("Error en Stripe"));

            // Verifica que la excepción se lance correctamente
            RuntimeException exception = assertThrows(RuntimeException.class, () -> {
                pagoService.crearPaymentIntent(10.0);
            });

            assertEquals("Error en Stripe", exception.getMessage());
        }
    }
}
