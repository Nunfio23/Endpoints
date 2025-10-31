package com.example.ProyectoInventario.service;

import java.util.List;
import com.example.ProyectoInventario.dto.RestablecimientoCreateDTO;
import com.example.ProyectoInventario.dto.RestablecimientoUpdateDTO;
import com.example.ProyectoInventario.dto.RestablecimientoResponseDTO;

public interface RestablecimientoService {
    List<RestablecimientoResponseDTO> listar();
    RestablecimientoResponseDTO obtenerPorId(Long id);
    RestablecimientoResponseDTO crear(RestablecimientoCreateDTO dto);
    RestablecimientoResponseDTO actualizar(Long id, RestablecimientoUpdateDTO dto);
    void eliminar(Long id);
}
