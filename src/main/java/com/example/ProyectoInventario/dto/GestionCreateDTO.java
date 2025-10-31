package com.example.ProyectoInventario.dto;

public class GestionCreateDTO {
    private Long restablecimientoId;
    private String observacion;    // requerida si aprobado=false

    public Long getRestablecimientoId() { return restablecimientoId; }
    public void setRestablecimientoId(Long restablecimientoId) { this.restablecimientoId = restablecimientoId; }


    public String getObservacion() { return observacion; }
    public void setObservacion(String observacion) { this.observacion = observacion; }
}
