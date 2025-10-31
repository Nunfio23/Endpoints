package com.example.ProyectoInventario.controller;

import com.example.ProyectoInventario.dto.EntregaCreateDTO;
import com.example.ProyectoInventario.dto.EntregaUpdateDTO;
import com.example.ProyectoInventario.dto.EntregaResponseDTO;
import com.example.ProyectoInventario.service.EntregaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/entregas")
@Tag(name = "Entregas", description = "CRUD de entregas con validaciones y DTOs")
@CrossOrigin(origins = "*")
public class EntregaController {

    private final EntregaService service;

    public EntregaController(EntregaService service) {
        this.service = service;
    }

    // ============================================
    // LISTAR
    // ============================================
    @Operation(summary = "Listar entregas")
    @GetMapping
    public ResponseEntity<List<EntregaResponseDTO>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    // ============================================
    // OBTENER
    // ============================================
    @Operation(summary = "Obtener entrega por ID")
    @GetMapping("/{id}")
    public ResponseEntity<?> obtener(@PathVariable Long id) {
        try {
            EntregaResponseDTO entrega = service.obtener(id);
            return ResponseEntity.ok(entrega);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(404).body(ex.getMessage());
        }
    }

    // ============================================
    // CREAR
    // ============================================
    @Operation(summary = "Crear entrega")
    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody EntregaCreateDTO dto) {
        try {
            EntregaResponseDTO creada = service.crear(dto);
            return ResponseEntity.ok(creada);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.internalServerError().body("Error al crear la entrega: " + ex.getMessage());
        }
    }

    // ============================================
    // ACTUALIZAR
    // ============================================
    @Operation(summary = "Actualizar entrega")
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @Valid @RequestBody EntregaUpdateDTO dto) {
        try {
            EntregaResponseDTO actualizada = service.actualizar(id, dto);
            return ResponseEntity.ok(actualizada);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        } catch (RuntimeException ex) {
            return ResponseEntity.status(404).body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.internalServerError().body("Error al actualizar la entrega: " + ex.getMessage());
        }
    }

    // ============================================
    // ELIMINAR
    // ============================================
    @Operation(summary = "Eliminar entrega")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            service.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(404).body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.internalServerError().body("Error al eliminar la entrega: " + ex.getMessage());
        }
    }
}
