package com.eCommerceProyect.proyect;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}

// Controlador de ejemplo para probar la API
@RestController
class HelloController {
    @GetMapping("/api/hello")
    public String hello() {
        return "¡Hola, mundo! El servidor está corriendo.";
    }
}

