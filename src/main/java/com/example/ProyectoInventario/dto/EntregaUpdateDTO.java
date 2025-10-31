package com.example.ProyectoInventario.dto;

import java.time.LocalDateTime;

public class EntregaUpdateDTO {
    private Integer cantidad;
    private LocalDateTime fechaEntrega;

    // Getters y Setters
    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }

    public LocalDateTime getFechaEntrega() { return fechaEntrega; }
    public void setFechaEntrega(LocalDateTime fechaEntrega) { this.fechaEntrega = fechaEntrega; }
}
