package com.example.ProyectoInventario.service;

import java.util.List;

import com.example.ProyectoInventario.dto.ProductoCreateDTO;
import com.example.ProyectoInventario.dto.ProductoResponseDTO;

public interface ProductoService {

    ProductoResponseDTO crear(ProductoCreateDTO dto);

    ProductoResponseDTO obtener(Long id);

    List<ProductoResponseDTO> listar();

    ProductoResponseDTO actualizar(Long id, ProductoCreateDTO dto);

    void eliminar(Long id);
}
