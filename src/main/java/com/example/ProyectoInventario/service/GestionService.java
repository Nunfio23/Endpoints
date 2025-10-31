package com.example.ProyectoInventario.service;

import com.example.ProyectoInventario.dto.GestionCreateDTO;
import com.example.ProyectoInventario.dto.GestionResponseDTO;

import java.util.List;

public interface GestionService {
    List<GestionResponseDTO> listar();
    GestionResponseDTO obtenerPorId(Long id);

    // NUEVOS:
    GestionResponseDTO crear(GestionCreateDTO dto);
    GestionResponseDTO actualizar(Long id, GestionCreateDTO dto);

    // Ya existentes:
    GestionResponseDTO aprobar(Long id, String observacion);
    GestionResponseDTO rechazar(Long id, String observacion);
    void eliminar(Long id);
}
