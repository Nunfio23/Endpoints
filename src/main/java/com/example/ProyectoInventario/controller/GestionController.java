package com.example.ProyectoInventario.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.ProyectoInventario.entity.Gestion;
import com.example.ProyectoInventario.service.GestionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/gestiones")
@Tag(name = "Gestiones", description = "Endpoints para aprobar o rechazar solicitudes de restablecimiento")
public class GestionController {

    private final GestionService service;

    public GestionController(GestionService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Listar todas las gestiones")
    public List<Gestion> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener gestión por ID")
    public Gestion obtener(@PathVariable Long id) {
        return service.obtenerPorId(id);
    }

    @PostMapping
    @Operation(summary = "Crear nueva gestión")
    public Gestion crear(@RequestBody Gestion gestion) {
        return service.crear(gestion);
    }

    @PutMapping("/{id}/aprobar")
    @Operation(summary = "Aprobar un restablecimiento")
    public Gestion aprobar(@PathVariable Long id, @RequestParam(required = false) String observacion) {
        return service.aprobar(id, observacion);
    }

    @PutMapping("/{id}/rechazar")
    @Operation(summary = "Rechazar un restablecimiento")
    public Gestion rechazar(@PathVariable Long id, @RequestParam(required = false) String observacion) {
        return service.rechazar(id, observacion);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una gestión")
    public void eliminar(@PathVariable Long id) {
        service.eliminar(id);
    }
}
