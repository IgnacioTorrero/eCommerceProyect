package com.eCommerceProyect.proyect;

import com.eCommerceProyect.proyect.model.Producto;
import com.eCommerceProyect.proyect.repository.ProductoRepository;
import com.eCommerceProyect.proyect.service.ProductoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductoServiceTest {

    @Mock
    private ProductoRepository productoRepository;

    @InjectMocks
    private ProductoService productoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void crearProducto_exito() {
        Producto producto = new Producto();
        producto.setNombre("Producto 1");

        when(productoRepository.save(any(Producto.class))).thenReturn(producto);

        Producto productoGuardado = productoService.crearProducto(producto);

        assertEquals("Producto 1", productoGuardado.getNombre());
        verify(productoRepository, times(1)).save(producto);
    }

    @Test
    void crearProducto_errorDeBD() {
        Producto producto = new Producto();
        producto.setNombre("Producto 1");

        when(productoRepository.save(any(Producto.class))).thenThrow(DataIntegrityViolationException.class);

        assertThrows(RuntimeException.class, () -> {
            productoService.crearProducto(producto);
        });
    }

    @Test
    void listarProductos_exito() {
        List<Producto> productos = Arrays.asList(new Producto(), new Producto());

        when(productoRepository.findAll()).thenReturn(productos);

        List<Producto> resultado = productoService.listarProductos();

        assertEquals(2, resultado.size());
        verify(productoRepository, times(1)).findAll();
    }

    @Test
    void listarProductos_errorDeBD() {
        when(productoRepository.findAll()).thenThrow(DataIntegrityViolationException.class);

        assertThrows(RuntimeException.class, () -> {
            productoService.listarProductos();
        });
    }

    @Test
    void obtenerProductoPorId_productoExistente() {
        Producto producto = new Producto();
        producto.setId(1L);

        when(productoRepository.findById(1L)).thenReturn(Optional.of(producto));

        Optional<Producto> resultado = productoService.obtenerProductoPorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals(1L, resultado.get().getId());
        verify(productoRepository, times(1)).findById(1L);
    }

    @Test
    void obtenerProductoPorId_productoNoExistente() {
        when(productoRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Producto> resultado = productoService.obtenerProductoPorId(1L);

        assertFalse(resultado.isPresent());
        verify(productoRepository, times(1)).findById(1L);
    }

    @Test
    void obtenerProductoPorId_errorDeBD() {
        when(productoRepository.findById(1L)).thenThrow(DataIntegrityViolationException.class);

        assertThrows(RuntimeException.class, () -> {
            productoService.obtenerProductoPorId(1L);
        });
    }

    @Test
    void actualizarProducto_exito() {
        Producto producto = new Producto();
        producto.setId(1L);
        producto.setNombre("Producto Actualizado");

        when(productoRepository.existsById(1L)).thenReturn(true);
        when(productoRepository.save(any(Producto.class))).thenReturn(producto);

        Producto productoActualizado = productoService.actualizarProducto(producto);

        assertEquals("Producto Actualizado", productoActualizado.getNombre());
        verify(productoRepository, times(1)).save(producto);
    }

    @Test
    void actualizarProducto_productoNoExistente() {
        Producto producto = new Producto();
        producto.setId(1L);

        when(productoRepository.existsById(1L)).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () -> {
            productoService.actualizarProducto(producto);
        });
    }

    @Test
    void actualizarProducto_errorDeBD() {
        Producto producto = new Producto();
        producto.setId(1L);

        when(productoRepository.existsById(1L)).thenReturn(true);
        when(productoRepository.save(any(Producto.class))).thenThrow(DataIntegrityViolationException.class);

        assertThrows(RuntimeException.class, () -> {
            productoService.actualizarProducto(producto);
        });
    }

    @Test
    void eliminarProducto_productoNoExistente() {
        when(productoRepository.existsById(1L)).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () -> {
            productoService.eliminarProducto(1L);
        });
    }

    @Test
    void eliminarProducto_exito() {
        when(productoRepository.existsById(1L)).thenReturn(true);

        productoService.eliminarProducto(1L);

        verify(productoRepository, times(1)).deleteById(1L);
    }

    @Test
    void eliminarProducto_errorDeBD() {
        when(productoRepository.existsById(1L)).thenReturn(true);
        doThrow(DataIntegrityViolationException.class).when(productoRepository).deleteById(1L);

        assertThrows(RuntimeException.class, () -> {
            productoService.eliminarProducto(1L);
        });
    }
}
