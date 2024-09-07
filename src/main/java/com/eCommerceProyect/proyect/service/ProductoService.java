package com.eCommerceProyect.proyect.service;

import com.eCommerceProyect.proyect.model.Producto;
import com.eCommerceProyect.proyect.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.dao.DataAccessException;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    public Producto crearProducto(Producto producto) {
        try {
            return productoRepository.save(producto);
        } catch (DataAccessException e) {
            throw new RuntimeException("Error al crear el producto", e);
        }
    }

    public List<Producto> listarProductos() {
        try {
            return productoRepository.findAll();
        } catch (DataAccessException e) {
            throw new RuntimeException("Error al listar los productos", e);
        }
    }

    public Optional<Producto> obtenerProductoPorId(Long id) {
        try {
            return productoRepository.findById(id);
        } catch (DataAccessException e) {
            throw new RuntimeException("Error al obtener el producto", e);
        }
    }

    public Producto actualizarProducto(Producto producto) {
        if (!productoRepository.existsById(producto.getId())) {
            throw new IllegalArgumentException("Producto no encontrado");
        }

        try {
            return productoRepository.save(producto);
        } catch (DataAccessException e) {
            throw new RuntimeException("Error al actualizar el producto", e);
        }
    }

    public void eliminarProducto(Long id) {
        if (!productoRepository.existsById(id)) {
            throw new IllegalArgumentException("Producto no encontrado");
        }

        try {
            productoRepository.deleteById(id);
        } catch (DataAccessException e) {
            throw new RuntimeException("Error al eliminar el producto", e);
        }
    }
}