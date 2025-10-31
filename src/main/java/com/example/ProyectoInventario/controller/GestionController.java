package com.example.ProyectoInventario.controller;

import com.example.ProyectoInventario.dto.GestionResponseDTO;
import com.example.ProyectoInventario.service.GestionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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

    // ======================================================
    // LISTAR TODAS LAS GESTIONES
    // ======================================================
    @Operation(summary = "Listar todas las gestiones")
    @GetMapping
    public ResponseEntity<List<GestionResponseDTO>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    // ======================================================
    // OBTENER GESTIÓN POR ID
    // ======================================================
    @Operation(summary = "Obtener gestión por ID")
    @GetMapping("/{id}")
    public ResponseEntity<?> obtener(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(service.obtenerPorId(id));
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    // ======================================================
    // APROBAR GESTIÓN
    // ======================================================
    @Operation(summary = "Aprobar una gestión existente")
    @PutMapping("/{id}/aprobar")
    public ResponseEntity<?> aprobar(
            @PathVariable Long id,
            @RequestParam(required = false) String observacion) {
        try {
            GestionResponseDTO aprobada = service.aprobar(id, observacion);
            return ResponseEntity.ok(aprobada);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    // ======================================================
    // RECHAZAR GESTIÓN
    // ======================================================
    @Operation(summary = "Rechazar una gestión existente")
    @PutMapping("/{id}/rechazar")
    public ResponseEntity<?> rechazar(
            @PathVariable Long id,
            @RequestParam(required = false) String observacion) {
        try {
            GestionResponseDTO rechazada = service.rechazar(id, observacion);
            return ResponseEntity.ok(rechazada);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    // ======================================================
    // ELIMINAR GESTIÓN
    // ======================================================
    @Operation(summary = "Eliminar gestión existente")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            service.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }
}
