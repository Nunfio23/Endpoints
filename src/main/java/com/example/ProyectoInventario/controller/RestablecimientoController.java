package com.example.ProyectoInventario.controller;

import com.example.ProyectoInventario.entity.Restablecimiento;
import com.example.ProyectoInventario.service.RestablecimientoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restablecimientos")
@Tag(name = "Restablecimientos", description = "Endpoints para solicitudes de reposición de productos")
@CrossOrigin(origins = "*")
public class RestablecimientoController {

    private final RestablecimientoService service;

    public RestablecimientoController(RestablecimientoService service) {
        this.service = service;
    }

    // ============================================
    // LISTAR
    // ============================================
    @GetMapping
    @Operation(summary = "Listar todas las solicitudes de restablecimiento")
    public ResponseEntity<List<Restablecimiento>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    // ============================================
    // OBTENER POR ID
    // ============================================
    @GetMapping("/{id}")
    @Operation(summary = "Obtener una solicitud de restablecimiento por ID")
    public ResponseEntity<?> obtener(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(service.obtenerPorId(id));
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body("No se encontró el restablecimiento con ID: " + id);
        }
    }

    // ============================================
    // CREAR
    // ============================================
    @PostMapping
    @Operation(summary = "Crear una nueva solicitud de restablecimiento")
    public ResponseEntity<?> crear(@Valid @RequestBody Restablecimiento restablecimiento) {
        try {
            Restablecimiento creado = service.crear(restablecimiento);
            return ResponseEntity.ok(creado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error al crear el restablecimiento: " + e.getMessage());
        }
    }

    // ============================================
    // ACTUALIZAR
    // ============================================
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar una solicitud de restablecimiento existente")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @Valid @RequestBody Restablecimiento restablecimiento) {
        try {
            Restablecimiento actualizado = service.actualizar(id, restablecimiento);
            return ResponseEntity.ok(actualizado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body("Restablecimiento no encontrado: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error al actualizar: " + e.getMessage());
        }
    }

    // ============================================
    // ELIMINAR
    // ============================================
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una solicitud de restablecimiento")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            service.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body("No se pudo eliminar: " + e.getMessage());
        }
    }
}
