package com.example.ProyectoInventario.service;

import java.util.List;

import com.example.ProyectoInventario.entity.Restablecimiento;

public interface RestablecimientoService {

    List<Restablecimiento> listar();

    Restablecimiento obtenerPorId(Long id);

    Restablecimiento crear(Restablecimiento restablecimiento);

    Restablecimiento actualizar(Long id, Restablecimiento restablecimiento);

    void eliminar(Long id);
}
