package com.example.ProyectoInventario.controller;

import com.example.ProyectoInventario.dto.RestablecimientoCreateDTO;
import com.example.ProyectoInventario.dto.RestablecimientoUpdateDTO;
import com.example.ProyectoInventario.dto.RestablecimientoResponseDTO;
import com.example.ProyectoInventario.service.RestablecimientoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restablecimientos")
@Tag(name = "Restablecimientos", description = "Gestión de solicitudes de restablecimiento de stock")
@CrossOrigin(origins = "*")
public class RestablecimientoController {

    private final RestablecimientoService service;

    public RestablecimientoController(RestablecimientoService service) {
        this.service = service;
    }

    // ============================================
    // LISTAR
    // ============================================
    @Operation(summary = "Listar todas las solicitudes de restablecimiento")
    @GetMapping
    public ResponseEntity<List<RestablecimientoResponseDTO>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    // ============================================
    // OBTENER
    // ============================================
    @Operation(summary = "Obtener un restablecimiento por ID")
    @GetMapping("/{id}")
    public ResponseEntity<?> obtener(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(service.obtenerPorId(id));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(404).body(ex.getMessage());
        }
    }

    // ============================================
    // CREAR
    // ============================================
    @Operation(summary = "Crear solicitud de restablecimiento")
    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody RestablecimientoCreateDTO dto) {
        try {
            RestablecimientoResponseDTO creado = service.crear(dto);
            return ResponseEntity.ok(creado);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.internalServerError().body("Error al crear restablecimiento: " + ex.getMessage());
        }
    }

    // ============================================
    // ACTUALIZAR
    // ============================================
    @Operation(summary = "Actualizar restablecimiento")
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @Valid @RequestBody RestablecimientoUpdateDTO dto) {
        try {
            RestablecimientoResponseDTO actualizado = service.actualizar(id, dto);
            return ResponseEntity.ok(actualizado);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.internalServerError().body("Error al actualizar restablecimiento: " + ex.getMessage());
        }
    }

    // ============================================
    // ELIMINAR
    // ============================================
    @Operation(summary = "Eliminar restablecimiento")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            service.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(404).body(ex.getMessage());
        }
    }
}
