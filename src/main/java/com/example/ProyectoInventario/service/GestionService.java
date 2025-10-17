package com.example.ProyectoInventario.service;

import java.util.List;

import com.example.ProyectoInventario.entity.Gestion;

public interface GestionService {

    List<Gestion> listar();

    Gestion obtenerPorId(Long id);

    Gestion crear(Gestion gestion);

    Gestion aprobar(Long id, String observacion);

    Gestion rechazar(Long id, String observacion);

    void eliminar(Long id);
}
