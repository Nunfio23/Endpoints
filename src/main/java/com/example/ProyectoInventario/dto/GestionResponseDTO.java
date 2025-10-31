package com.example.ProyectoInventario.dto;

import java.time.LocalDateTime;

public class GestionResponseDTO {
    private Long gestionId;
    private Long restablecimientoId;
    private Boolean aprobado;
    private LocalDateTime fechaAprobacion;
    private String observacion;

    // Getters y Setters
    public Long getGestionId() { return gestionId; }
    public void setGestionId(Long gestionId) { this.gestionId = gestionId; }

    public Long getRestablecimientoId() { return restablecimientoId; }
    public void setRestablecimientoId(Long restablecimientoId) { this.restablecimientoId = restablecimientoId; }

    public Boolean getAprobado() { return aprobado; }
    public void setAprobado(Boolean aprobado) { this.aprobado = aprobado; }

    public LocalDateTime getFechaAprobacion() { return fechaAprobacion; }
    public void setFechaAprobacion(LocalDateTime fechaAprobacion) { this.fechaAprobacion = fechaAprobacion; }

    public String getObservacion() { return observacion; }
    public void setObservacion(String observacion) { this.observacion = observacion; }
}
