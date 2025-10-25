package com.example.ProyectoInventario.controller;

import com.example.ProyectoInventario.entity.Gestion;
import com.example.ProyectoInventario.service.GestionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/gestiones")
@Tag(name = "Gestiones", description = "Endpoints para aprobar o rechazar solicitudes de restablecimiento")
@CrossOrigin(origins = "*")
public class GestionController {

    private final GestionService service;

    public GestionController(GestionService service) {
        this.service = service;
    }

    // =====================================
    // LISTAR TODAS
    // =====================================
    @GetMapping
    @Operation(summary = "Listar todas las gestiones")
    public ResponseEntity<List<Gestion>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    // =====================================
    // OBTENER POR ID
    // =====================================
    @GetMapping("/{id}")
    @Operation(summary = "Obtener gestión por ID")
    public ResponseEntity<?> obtener(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(service.obtenerPorId(id));
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body("Gestión no encontrada: " + e.getMessage());
        }
    }

    // =====================================
    // CREAR NUEVA GESTIÓN
    // =====================================
    @PostMapping
    @Operation(summary = "Crear nueva gestión asociada a un restablecimiento existente")
    public ResponseEntity<?> crear(@Valid @RequestBody Gestion gestion) {
        try {
            Gestion creada = service.crear(gestion);
            return ResponseEntity.ok(creada);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body("Error al crear la gestión: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error inesperado: " + e.getMessage());
        }
    }

    // =====================================
    // APROBAR GESTIÓN
    // =====================================
    @PutMapping("/{id}/aprobar")
    @Operation(summary = "Aprobar un restablecimiento asociado a la gestión")
    public ResponseEntity<?> aprobar(@PathVariable Long id, @RequestParam(required = false) String observacion) {
        try {
            Gestion aprobada = service.aprobar(id, observacion);
            return ResponseEntity.ok(aprobada);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body("No se pudo aprobar: " + e.getMessage());
        }
    }

    // =====================================
    // RECHAZAR GESTIÓN
    // =====================================
    @PutMapping("/{id}/rechazar")
    @Operation(summary = "Rechazar un restablecimiento asociado a la gestión")
    public ResponseEntity<?> rechazar(@PathVariable Long id, @RequestParam(required = false) String observacion) {
        try {
            Gestion rechazada = service.rechazar(id, observacion);
            return ResponseEntity.ok(rechazada);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body("No se pudo rechazar: " + e.getMessage());
        }
    }

    // =====================================
    // ELIMINAR
    // =====================================
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una gestión existente")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            service.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body("Error al eliminar: " + e.getMessage());
        }
    }
}
