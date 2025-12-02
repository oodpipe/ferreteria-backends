package cl.ferreteria.backend.controller;

import cl.ferreteria.backend.model.Producto;
import cl.ferreteria.backend.service.ProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
@CrossOrigin(origins = "http://localhost:3000")
@Tag(name = "Productos", description = "API para gestión de productos de ferretería")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    // ===================== LISTAR =====================
    @GetMapping
    @Operation(summary = "Obtener todos los productos")
    public ResponseEntity<List<Producto>> obtenerTodos() {
        return ResponseEntity.ok(productoService.obtenerTodos());
    }

    // ===================== BUSCAR POR ID =====================
    @GetMapping("/{id}")
    @Operation(summary = "Obtener producto por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Producto encontrado"),
            @ApiResponse(responseCode = "404", description = "Producto no existe")
    })
    public ResponseEntity<Producto> obtenerPorId(@PathVariable Long id) {
        Producto producto = productoService.obtenerPorId(id);
        return ResponseEntity.ok(producto);
    }

    // ===================== POR CATEGORÍA =====================
    @GetMapping("/categoria/{categoria}")
    @Operation(summary = "Obtener productos por categoría")
    public ResponseEntity<List<Producto>> obtenerPorCategoria(@PathVariable String categoria) {
        return ResponseEntity.ok(productoService.obtenerPorCategoria(categoria));
    }

    // ===================== BUSCAR POR NOMBRE =====================
    @GetMapping("/buscar")
    @Operation(summary = "Buscar productos por nombre")
    public ResponseEntity<List<Producto>> buscarPorNombre(@RequestParam String nombre) {
        return ResponseEntity.ok(productoService.buscarPorNombre(nombre));
    }

    // ===================== CREAR =====================
    @PostMapping
    @Operation(summary = "Crear nuevo producto")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Producto creado")
    })
    public ResponseEntity<Producto> crearProducto(@Valid @RequestBody Producto producto) {
        Producto nuevo = productoService.crearProducto(producto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    // ===================== ACTUALIZAR =====================
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar producto existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Producto actualizado"),
            @ApiResponse(responseCode = "404", description = "Producto no existe")
    })
    public ResponseEntity<Producto> actualizarProducto(
            @PathVariable Long id,
            @Valid @RequestBody Producto producto) {

        Producto actualizado = productoService.actualizarProducto(id, producto);
        return ResponseEntity.ok(actualizado);
    }

    // ===================== ELIMINAR =====================
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar producto")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Producto eliminado"),
            @ApiResponse(responseCode = "404", description = "Producto no existe")
    })
    public ResponseEntity<Void> eliminarProducto(@PathVariable Long id) {
        productoService.eliminarProducto(id);
        return ResponseEntity.noContent().build();
    }

    // ===================== STOCK =====================
    @GetMapping("/stock")
    @Operation(summary = "Obtener productos con stock disponible")
    public ResponseEntity<List<Producto>> obtenerConStock() {
        return ResponseEntity.ok(productoService.obtenerConStock());
    }
}
