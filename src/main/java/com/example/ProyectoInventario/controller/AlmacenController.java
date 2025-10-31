package com.example.ProyectoInventario.controller;

import com.example.ProyectoInventario.dto.AlmacenCreateDTO;
import com.example.ProyectoInventario.dto.AlmacenUpdateDTO;
import com.example.ProyectoInventario.dto.AlmacenResponseDTO;
import com.example.ProyectoInventario.service.AlmacenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/almacenes")
@Tag(name = "Almacenes", description = "CRUD de almacenes con DTOs")
public class AlmacenController {

    private final AlmacenService service;

    public AlmacenController(AlmacenService service) {
        this.service = service;
    }

    @Operation(summary = "Listar todos los almacenes")
    @GetMapping
    public ResponseEntity<List<AlmacenResponseDTO>> listar() {
        List<AlmacenResponseDTO> almacenes = service.listar();
        return ResponseEntity.ok(almacenes);
    }

    @Operation(summary = "Obtener almacén por ID")
    @GetMapping("/{id}")
    public ResponseEntity<AlmacenResponseDTO> obtener(@PathVariable Long id) {
        AlmacenResponseDTO almacen = service.obtener(id);
        return ResponseEntity.ok(almacen);
    }

    @Operation(summary = "Crear un nuevo almacén")
    @PostMapping
    public ResponseEntity<AlmacenResponseDTO> crear(@RequestBody AlmacenCreateDTO dto) {
        AlmacenResponseDTO nuevo = service.crear(dto);
        return ResponseEntity.ok(nuevo);
    }

    @Operation(summary = "Actualizar un almacén existente")
    @PutMapping("/{id}")
    public ResponseEntity<AlmacenResponseDTO> actualizar(@PathVariable Long id, @RequestBody AlmacenUpdateDTO dto) {
        AlmacenResponseDTO actualizado = service.actualizar(id, dto);
        return ResponseEntity.ok(actualizado);
    }

    @Operation(summary = "Eliminar un almacén por ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
