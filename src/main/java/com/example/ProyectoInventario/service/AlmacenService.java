package com.example.ProyectoInventario.service;

import java.util.List;
import com.example.ProyectoInventario.dto.AlmacenCreateDTO;
import com.example.ProyectoInventario.dto.AlmacenUpdateDTO;
import com.example.ProyectoInventario.dto.AlmacenResponseDTO;

public interface AlmacenService {
    List<AlmacenResponseDTO> listar();
    AlmacenResponseDTO obtener(Long id);
    AlmacenResponseDTO crear(AlmacenCreateDTO dto);
    AlmacenResponseDTO actualizar(Long id, AlmacenUpdateDTO dto);
    void eliminar(Long id);
}
