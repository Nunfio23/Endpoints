package com.example.ProyectoInventario.service;

import com.example.ProyectoInventario.dto.GestionResponseDTO;
import java.util.List;

public interface GestionService {

    List<GestionResponseDTO> listar();

    GestionResponseDTO obtenerPorId(Long id);

    GestionResponseDTO aprobar(Long id, String observacion);

    GestionResponseDTO rechazar(Long id, String observacion);

    void eliminar(Long id);
}
