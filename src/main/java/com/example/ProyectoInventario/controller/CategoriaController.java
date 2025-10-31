package com.example.ProyectoInventario.controller;

import com.example.ProyectoInventario.dto.CategoriaCreateDTO;
import com.example.ProyectoInventario.dto.CategoriaUpdateDTO;
import com.example.ProyectoInventario.dto.CategoriaResponseDTO;
import com.example.ProyectoInventario.entity.Categoria;
import com.example.ProyectoInventario.service.CategoriaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/categorias")
@Tag(name = "Categorías")
public class CategoriaController {

    private final CategoriaService service;

    public CategoriaController(CategoriaService service) {
        this.service = service;
    }

    
    @Operation(summary = "Listar categorías")
    @GetMapping
    public List<CategoriaResponseDTO> listar() {
        return service.listar()
                .stream()
                .map(c -> new CategoriaResponseDTO(
                        c.getCategoriaId(),
                        c.getNombre(),
                        c.getDescripcion()))
                .collect(Collectors.toList());
    }

    @Operation(summary = "Obtener categoría por ID")
    @GetMapping("/{id}")
    public CategoriaResponseDTO obtener(@PathVariable Long id) {
        Categoria c = service.obtener(id);
        if (c == null) return null;
        return new CategoriaResponseDTO(c.getCategoriaId(), c.getNombre(), c.getDescripcion());
    }

    @Operation(summary = "Crear categoría")
    @PostMapping
    public CategoriaResponseDTO crear(@Valid @RequestBody CategoriaCreateDTO dto) {
        Categoria nueva = new Categoria();
        nueva.setNombre(dto.getNombre());
        nueva.setDescripcion(dto.getDescripcion());

        Categoria guardada = service.crear(nueva);
        return new CategoriaResponseDTO(guardada.getCategoriaId(), guardada.getNombre(), guardada.getDescripcion());
    }

    @Operation(summary = "Actualizar categoría")
    @PutMapping("/{id}")
    public CategoriaResponseDTO actualizar(@PathVariable Long id, @Valid @RequestBody CategoriaUpdateDTO dto) {
        Categoria datos = new Categoria();
        datos.setNombre(dto.getNombre());
        datos.setDescripcion(dto.getDescripcion());

        Categoria actualizada = service.actualizar(id, datos);
        return new CategoriaResponseDTO(actualizada.getCategoriaId(), actualizada.getNombre(), actualizada.getDescripcion());
    }

    // 🔹 Eliminar categoría
    @Operation(summary = "Eliminar categoría")
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        service.eliminar(id);
    }
}
