package com.example.ProyectoInventario.controller;

import com.example.ProyectoInventario.entity.Almacen;
import com.example.ProyectoInventario.service.AlmacenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/almacenes")
@Tag(name = "Almacenes", description = "CRUD de almacenes")
public class AlmacenController {

    private final AlmacenService service;

    public AlmacenController(AlmacenService service) {
        this.service = service;
    }

    @Operation(summary = "Listar almacenes")
    @GetMapping
    public List<Almacen> listar() {
        return service.listar();
    }

    @Operation(summary = "Obtener almacén por ID")
    @GetMapping("/{id}")
    public Almacen obtener(@PathVariable Long id) {
        return service.obtener(id);
    }

    @Operation(summary = "Crear almacén")
    @PostMapping
    public Almacen crear(@RequestBody Almacen a) {
        return service.crear(a);
    }

    @Operation(summary = "Actualizar almacén")
    @PutMapping("/{id}")
    public Almacen actualizar(@PathVariable Long id, @RequestBody Almacen a) {
        return service.actualizar(id, a);
    }

    @Operation(summary = "Eliminar almacén")
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        service.eliminar(id);
    }
}
