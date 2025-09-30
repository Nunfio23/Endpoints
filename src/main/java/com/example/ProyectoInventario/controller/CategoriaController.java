package com.example.ProyectoInventario.controller;

import com.example.ProyectoInventario.entity.Categoria;
import com.example.ProyectoInventario.service.CategoriaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/categorias")
@Tag(name = "Categorías")
public class CategoriaController {

    private final CategoriaService service;

    public CategoriaController(CategoriaService service) { this.service = service; }

    @Operation(summary = "Listar categorías")
    @GetMapping public List<Categoria> listar() { return service.listar(); }

    @Operation(summary = "Obtener categoría por ID")
    @GetMapping("/{id}") public Categoria obtener(@PathVariable Long id) { return service.obtener(id); }

    @Operation(summary = "Crear categoría")
    @PostMapping public Categoria crear(@RequestBody Categoria c) { return service.crear(c); }

    @Operation(summary = "Actualizar categoría")
    @PutMapping("/{id}") public Categoria actualizar(@PathVariable Long id, @RequestBody Categoria c) { return service.actualizar(id, c); }

    @Operation(summary = "Eliminar categoría")
    @DeleteMapping("/{id}") public void eliminar(@PathVariable Long id) { service.eliminar(id); }
}
