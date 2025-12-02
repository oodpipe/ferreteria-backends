package cl.ferreteria.backend.service;

import cl.ferreteria.backend.model.Producto;
import cl.ferreteria.backend.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;

import java.util.List;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    // ===================== LISTAR =====================
    public List<Producto> obtenerTodos() {
        return productoRepository.findAll();
    }

    // ===================== POR ID =====================
    public Producto obtenerPorId(Long id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Producto con ID " + id + " no existe"));
    }

    // ===================== POR CATEGORÍA =====================
    public List<Producto> obtenerPorCategoria(String categoria) {
        return productoRepository.findByCategoria(categoria);
    }

    // ===================== BUSCAR =====================
    public List<Producto> buscarPorNombre(String nombre) {
        return productoRepository.findByNombreContainingIgnoreCase(nombre);
    }

    // ===================== CREAR =====================
    public Producto crearProducto(Producto producto) {
        validarProducto(producto);
        return productoRepository.save(producto);
    }

    // ===================== ACTUALIZAR =====================
    public Producto actualizarProducto(Long id, Producto productoActualizado) {

        Producto existente = obtenerPorId(id); // Ya lanza excepción si no existe

        // Se actualiza campo por campo (buena práctica)
        existente.setNombre(productoActualizado.getNombre());
        existente.setCategoria(productoActualizado.getCategoria());
        existente.setPrecio(productoActualizado.getPrecio());
        existente.setStock(productoActualizado.getStock());
        existente.setDescripcion(productoActualizado.getDescripcion());

        validarProducto(existente);

        return productoRepository.save(existente);
    }

    // ===================== ELIMINAR =====================
    public void eliminarProducto(Long id) {
        Producto producto = obtenerPorId(id); // Lanza excepción si no existe
        productoRepository.delete(producto);
    }

    // ===================== STOCK =====================
    public List<Producto> obtenerConStock() {
        return productoRepository.findByStockGreaterThan(0);
    }

    // ===================== VALIDACIONES =====================
    private void validarProducto(Producto p) {

        if (p.getNombre() == null || p.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del producto es obligatorio");
        }

        if (p.getCategoria() == null || p.getCategoria().trim().isEmpty()) {
            throw new IllegalArgumentException("La categoría es obligatoria");
        }

        if (p.getPrecio() < 0) {
            throw new IllegalArgumentException("El precio no puede ser negativo");
        }

        if (p.getStock() < 0) {
            throw new IllegalArgumentException("El stock no puede ser negativo");
        }
    }
}
