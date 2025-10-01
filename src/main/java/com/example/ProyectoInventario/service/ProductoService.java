package com.example.ProyectoInventario.service;

import java.util.List;

import com.example.ProyectoInventario.dto.ProductoCreateDTO;
import com.example.ProyectoInventario.dto.ProductoResponseDTO;
import com.example.ProyectoInventario.dto.ProductoUpdateDTO;

public interface ProductoService {
    List<ProductoResponseDTO> listar();
    ProductoResponseDTO obtenerPorId(Long id);
    ProductoResponseDTO crear(ProductoCreateDTO dto);
    ProductoResponseDTO actualizar(Long id, ProductoUpdateDTO dto);
    void eliminar(Long id);
    ProductoResponseDTO activar(Long id);
    ProductoResponseDTO desactivar(Long id);
    ProductoResponseDTO aumentarStock(Long id, int cantidad);
    ProductoResponseDTO disminuirStock(Long id, int cantidad);
    ProductoResponseDTO obtenerPorNombre(String nombre);
    List<ProductoResponseDTO> obtenerPorCategoria(Long categoriaId);

}
