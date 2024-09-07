package com.eCommerceProyect.proyect.repository;

import com.eCommerceProyect.proyect.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
    // Métodos de búsqueda personalizados
}