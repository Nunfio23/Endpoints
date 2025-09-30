package com.example.ProyectoInventario.service;

import java.util.List;

import com.example.ProyectoInventario.entity.Producto;

public interface ProductoService {
    List<Producto> listar();
    Producto obtener(Long id);
    Producto crear(Producto p);
    Producto actualizar(Long id, Producto p);
    void eliminar(Long id);
}
