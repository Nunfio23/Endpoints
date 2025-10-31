package com.example.ProyectoInventario.controller;

import com.example.ProyectoInventario.dto.GestionCreateDTO;
import com.example.ProyectoInventario.dto.GestionResponseDTO;
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

    @Operation(summary = "Listar todas las gestiones")
    @GetMapping
    public ResponseEntity<List<GestionResponseDTO>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @Operation(summary = "Obtener gestión por ID")
    @GetMapping("/{id}")
    public ResponseEntity<?> obtener(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(service.obtenerPorId(id));
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    // ======= NUEVOS =======
    @Operation(summary = "Crear nueva gestión (pendiente)")
    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody GestionCreateDTO dto) {
        try {
            return ResponseEntity.ok(service.crear(dto));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @Operation(summary = "Actualizar gestión (pendiente)")
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @Valid @RequestBody GestionCreateDTO dto) {
        try {
            return ResponseEntity.ok(service.actualizar(id, dto));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
    // ===== FIN NUEVOS =====

    @Operation(summary = "Aprobar un restablecimiento asociado a la gestión")
    @PutMapping("/{id}/aprobar")
    public ResponseEntity<?> aprobar(@PathVariable Long id,
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

    @Operation(summary = "Rechazar un restablecimiento asociado a la gestión")
    @PutMapping("/{id}/rechazar")
    public ResponseEntity<?> rechazar(@PathVariable Long id,
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
