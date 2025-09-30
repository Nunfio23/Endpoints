package com.example.ProyectoInventario.service;

import java.util.List;

import com.example.ProyectoInventario.entity.Almacen;

public interface AlmacenService {
    List<Almacen> listar();
    Almacen obtener(Long id);
    Almacen crear(Almacen a);
    Almacen actualizar(Long id, Almacen a);
    void eliminar(Long id);
}