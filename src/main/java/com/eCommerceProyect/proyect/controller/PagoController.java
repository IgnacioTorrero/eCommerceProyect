package com.eCommerceProyect.proyect.controller;

import com.eCommerceProyect.proyect.service.PagoService;
import com.stripe.model.PaymentIntent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pagos")
public class PagoController {
    @Autowired
    private PagoService pagoService;

    @PostMapping("/create-payment-intent")
    public ResponseEntity<?> crearPaymentIntent(@RequestParam Double monto) {
        try {
            PaymentIntent intent = pagoService.crearPaymentIntent(monto);
            return ResponseEntity.ok(intent);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al crear PaymentIntent");
        }
    }

    // Endpoints adicionales para verificar pagos
}