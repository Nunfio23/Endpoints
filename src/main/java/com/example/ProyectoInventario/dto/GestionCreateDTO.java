package com.example.ProyectoInventario.dto;

public class GestionCreateDTO {
    private Long restablecimientoId;
    private Boolean aprobado;      // true = aprobar, false = rechazar
    private String observacion;    // requerida si aprobado=false

    public Long getRestablecimientoId() { return restablecimientoId; }
    public void setRestablecimientoId(Long restablecimientoId) { this.restablecimientoId = restablecimientoId; }

    public Boolean getAprobado() { return aprobado; }
    public void setAprobado(Boolean aprobado) { this.aprobado = aprobado; }

    public String getObservacion() { return observacion; }
    public void setObservacion(String observacion) { this.observacion = observacion; }
}
