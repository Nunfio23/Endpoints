package com.example.ProyectoInventario.service;

import java.util.List;

import com.example.ProyectoInventario.entity.Historial;

public interface HistorialService {

    /**
     * Obtiene todos los registros del historial.
     */
    List<Historial> listarHistoriales();

    /**
     * Guarda un nuevo registro de historial.
     */
    Historial guardarHistorial(Historial historial);
}
