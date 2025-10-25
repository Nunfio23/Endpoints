package com.example.ProyectoInventario.controller;

import com.example.ProyectoInventario.entity.Entrega;
import com.example.ProyectoInventario.service.EntregaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/entregas")
@Tag(name = "Entregas", description = "CRUD de entregas")
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
    public ResponseEntity<List<Entrega>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    // ============================================
    // OBTENER
    // ============================================
    @Operation(summary = "Obtener entrega por ID")
    @GetMapping("/{id}")
    public ResponseEntity<?> obtener(@PathVariable Long id) {
        try {
            Entrega entrega = service.obtener(id);
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
    public ResponseEntity<?> crear(@Valid @RequestBody Entrega e) {
        try {
            Entrega creada = service.crear(e);
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
    public ResponseEntity<?> actualizar(@PathVariable Long id, @Valid @RequestBody Entrega e) {
        try {
            Entrega actualizada = service.actualizar(id, e);
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
