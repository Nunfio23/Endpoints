package com.example.ProyectoInventario.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.ProyectoInventario.entity.Historial;
import com.example.ProyectoInventario.repository.HistorialRepository;

@Service
public class HistorialServiceImpl implements HistorialService {

    private final HistorialRepository historialRepository;

    public HistorialServiceImpl(HistorialRepository historialRepository) {
        this.historialRepository = historialRepository;
    }

    @Override
    public List<Historial> listarHistoriales() {
        return historialRepository.findAll();
    }

    @Override
    public Historial guardarHistorial(Historial historial) {
        return historialRepository.save(historial);
    }
}
