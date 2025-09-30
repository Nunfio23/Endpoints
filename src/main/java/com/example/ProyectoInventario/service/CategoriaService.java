package com.example.ProyectoInventario.service;

import java.util.List;

import com.example.ProyectoInventario.entity.Categoria;

public interface CategoriaService {
    List<Categoria> listar();
    Categoria obtener(Long id);
    Categoria crear(Categoria c);
    Categoria actualizar(Long id, Categoria c);
    void eliminar(Long id);
}
