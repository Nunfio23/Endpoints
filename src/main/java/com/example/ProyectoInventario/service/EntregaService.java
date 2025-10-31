package com.example.ProyectoInventario.service;

import java.util.List;
import com.example.ProyectoInventario.dto.EntregaCreateDTO;
import com.example.ProyectoInventario.dto.EntregaUpdateDTO;
import com.example.ProyectoInventario.dto.EntregaResponseDTO;

public interface EntregaService {
    List<EntregaResponseDTO> listar();
    EntregaResponseDTO obtener(Long id);
    EntregaResponseDTO crear(EntregaCreateDTO dto);
    EntregaResponseDTO actualizar(Long id, EntregaUpdateDTO dto);
    void eliminar(Long id);
}
