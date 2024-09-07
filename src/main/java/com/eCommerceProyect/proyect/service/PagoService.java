package com.eCommerceProyect.proyect.service;

import com.stripe.Stripe;
import com.stripe.model.PaymentIntent;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

@Service
public class PagoService {
    @PostConstruct
    public void init() {
        Stripe.apiKey = "tu_clave_secreta_de_stripe";
    }

    public PaymentIntent crearPaymentIntent(Double monto) throws Exception {
        Map<String, Object> params = new HashMap<>();
        params.put("amount", (int)(monto * 100));
        params.put("currency", "usd");
        return PaymentIntent.create(params);
    }

    // Métodos adicionales para verificar pagos
}