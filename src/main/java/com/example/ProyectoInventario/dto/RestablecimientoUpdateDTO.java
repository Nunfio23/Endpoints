package com.example.ProyectoInventario.dto;

public class RestablecimientoUpdateDTO {
    private Integer cantidad;
    private String estado; // PENDIENTE, APROBADO, RECHAZADO

    // Getters y Setters
    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
