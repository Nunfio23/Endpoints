package com.example.ProyectoInventario.controller;

import com.example.ProyectoInventario.dto.ProductoCreateDTO;
import com.example.ProyectoInventario.dto.ProductoResponseDTO;
import com.example.ProyectoInventario.service.ProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
@Tag(name = "Productos", description = "CRUD de productos usando DTOs")
public class ProductoController {

    private final ProductoService service;

    public ProductoController(ProductoService service) {
        this.service = service;
    }

    @Operation(summary = "Listar todos los productos")
    @GetMapping
    public List<ProductoResponseDTO> listar() {
        return service.listar();
    }

    @Operation(summary = "Obtener producto por ID")
    @GetMapping("/{id}")
    public ProductoResponseDTO obtener(@PathVariable Long id) {
        return service.obtener(id);
    }

    @Operation(summary = "Crear un nuevo producto")
    @PostMapping
    public ProductoResponseDTO crear(@RequestBody ProductoCreateDTO dto) {
        return service.crear(dto);
    }

    @Operation(summary = "Actualizar producto por ID")
    @PutMapping("/{id}")
    public ProductoResponseDTO actualizar(@PathVariable Long id, @RequestBody ProductoCreateDTO dto) {
        return service.actualizar(id, dto);
    }

    @Operation(summary = "Eliminar producto por ID")
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        service.eliminar(id);
    }
}
