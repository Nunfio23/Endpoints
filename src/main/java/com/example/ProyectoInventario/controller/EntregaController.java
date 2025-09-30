package com.example.ProyectoInventario.controller;

import com.example.ProyectoInventario.entity.Entrega;
import com.example.ProyectoInventario.service.EntregaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/entregas")
@Tag(name = "Entregas", description = "CRUD de entregas")
public class EntregaController {

    private final EntregaService service;

    public EntregaController(EntregaService service) {
        this.service = service;
    }

    @Operation(summary = "Listar entregas")
    @GetMapping
    public List<Entrega> listar() {
        return service.listar();
    }

    @Operation(summary = "Obtener entrega por ID")
    @GetMapping("/{id}")
    public Entrega obtener(@PathVariable Long id) {
        return service.obtener(id);
    }

    @Operation(summary = "Crear entrega")
    @PostMapping
    public Entrega crear(@RequestBody Entrega e) {
        return service.crear(e);
    }

    @Operation(summary = "Actualizar entrega")
    @PutMapping("/{id}")
    public Entrega actualizar(@PathVariable Long id, @RequestBody Entrega e) {
        return service.actualizar(id, e);
    }

    @Operation(summary = "Eliminar entrega")
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        service.eliminar(id);
    }
}