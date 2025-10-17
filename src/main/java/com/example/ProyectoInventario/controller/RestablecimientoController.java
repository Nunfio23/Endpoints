package com.example.ProyectoInventario.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ProyectoInventario.entity.Restablecimiento;
import com.example.ProyectoInventario.service.RestablecimientoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/restablecimientos")
@Tag(name = "Restablecimientos", description = "Endpoints para solicitudes de reposición de productos")
public class RestablecimientoController {

    private final RestablecimientoService service;

    public RestablecimientoController(RestablecimientoService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Listar todas las solicitudes de restablecimiento")
    public List<Restablecimiento> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener una solicitud de restablecimiento por ID")
    public Restablecimiento obtener(@PathVariable Long id) {
        return service.obtenerPorId(id);
    }

    @PostMapping
    @Operation(summary = "Crear una nueva solicitud de restablecimiento")
    public Restablecimiento crear(@RequestBody Restablecimiento restablecimiento) {
        return service.crear(restablecimiento);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar una solicitud de restablecimiento existente")
    public Restablecimiento actualizar(@PathVariable Long id, @RequestBody Restablecimiento restablecimiento) {
        return service.actualizar(id, restablecimiento);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una solicitud de restablecimiento")
    public void eliminar(@PathVariable Long id) {
        service.eliminar(id);
    }
}
