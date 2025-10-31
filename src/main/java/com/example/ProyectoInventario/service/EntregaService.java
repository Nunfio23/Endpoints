package com.example.ProyectoInventario.service;

import com.example.ProyectoInventario.dto.EntregaResponseDTO;
import com.example.ProyectoInventario.dto.EntregaCreateDTO;
import com.example.ProyectoInventario.dto.EntregaUpdateDTO;
import com.example.ProyectoInventario.entity.Entrega; // ✅ Importa la entidad

import java.util.List;

public interface EntregaService {

    // Tus métodos existentes
    List<EntregaResponseDTO> listar();
    EntregaResponseDTO obtener(Long id);
    EntregaResponseDTO crear(EntregaCreateDTO dto);
    EntregaResponseDTO actualizar(Long id, EntregaUpdateDTO dto);
    void eliminar(Long id);

    // ✅ Este es el nuevo método que agregamos
    List<Entrega> listarEntidades();
}
