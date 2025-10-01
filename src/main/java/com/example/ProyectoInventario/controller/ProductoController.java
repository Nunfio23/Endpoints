package com.example.ProyectoInventario.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.ProyectoInventario.dto.ProductoCreateDTO;
import com.example.ProyectoInventario.dto.ProductoResponseDTO;
import com.example.ProyectoInventario.dto.ProductoUpdateDTO;
import com.example.ProyectoInventario.service.ProductoService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping
    public ResponseEntity<List<ProductoResponseDTO>> listar() {
        return ResponseEntity.ok(productoService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoResponseDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(productoService.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<ProductoResponseDTO> crear(@RequestBody ProductoCreateDTO dto) {
        return ResponseEntity.ok(productoService.crear(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductoResponseDTO> actualizar(
            @PathVariable Long id,
            @RequestBody ProductoUpdateDTO dto) {
        return ResponseEntity.ok(productoService.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        productoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/activar")
    public ResponseEntity<ProductoResponseDTO> activar(@PathVariable Long id) {
        return ResponseEntity.ok(productoService.activar(id));
    }

    @PatchMapping("/{id}/desactivar")
    public ResponseEntity<ProductoResponseDTO> desactivar(@PathVariable Long id) {
        return ResponseEntity.ok(productoService.desactivar(id));
    }

    @PutMapping("/increase-stock/{id}")
    public ProductoResponseDTO aumentarStock(
            @PathVariable Long id,
            @RequestParam int quantity) {
        return productoService.aumentarStock(id, quantity);
    }

    @PutMapping("/decrease-stock/{id}")
    public ProductoResponseDTO disminuirStock(
            @PathVariable Long id,
            @RequestParam int quantity) {
        return productoService.disminuirStock(id, quantity);
    }

    @Operation(summary = "Buscar producto por nombre", description = "Devuelve un producto filtrado por su nombre.")
    @GetMapping("/by-name")
    public ProductoResponseDTO obtenerPorNombre(@RequestParam String name) {
        return productoService.obtenerPorNombre(name);
    }
}
