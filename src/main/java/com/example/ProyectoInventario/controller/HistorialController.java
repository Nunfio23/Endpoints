package com.example.ProyectoInventario.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.ProyectoInventario.entity.Historial;
import com.example.ProyectoInventario.service.HistorialService;

@RestController
@RequestMapping("/api/historial")
public class HistorialController {

    private final HistorialService historialService;

    public HistorialController(HistorialService historialService) {
        this.historialService = historialService;
    }

    /**
     * Devuelve todos los registros del historial.
     */
    @GetMapping
    public List<Historial> listarHistoriales() {
        return historialService.listarHistoriales();
    }

    /**
     * Crea un nuevo registro en el historial.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Historial guardarHistorial(@Validated @RequestBody Historial historial) {
        return historialService.guardarHistorial(historial);
    }
}
