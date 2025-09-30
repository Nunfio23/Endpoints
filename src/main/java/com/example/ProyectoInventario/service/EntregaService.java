package com.example.ProyectoInventario.service;

import java.util.List;

import com.example.ProyectoInventario.entity.Entrega;

public interface EntregaService {
    List<Entrega> listar();
    Entrega obtener(Long id);
    Entrega crear(Entrega e);
    Entrega actualizar(Long id, Entrega e);
    void eliminar(Long id);
}