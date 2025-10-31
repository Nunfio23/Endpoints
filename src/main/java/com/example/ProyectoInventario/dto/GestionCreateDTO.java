package com.example.ProyectoInventario.dto;

import jakarta.validation.constraints.NotNull;

public class GestionCreateDTO {

    private Long restablecimientoId;

    private RestablecimientoDTO restablecimiento;

    private String observacion;

    public Long getRestablecimientoId() { return restablecimientoId; }
    public void setRestablecimientoId(Long restablecimientoId) { this.restablecimientoId = restablecimientoId; }

    public RestablecimientoDTO getRestablecimiento() { return restablecimiento; }
    public void setRestablecimiento(RestablecimientoDTO restablecimiento) { this.restablecimiento = restablecimiento; }

    public String getObservacion() { return observacion; }
    public void setObservacion(String observacion) { this.observacion = observacion; }

    public static class RestablecimientoDTO {
        private Long restablecimientoId;
        public Long getRestablecimientoId() { return restablecimientoId; }
        public void setRestablecimientoId(Long restablecimientoId) { this.restablecimientoId = restablecimientoId; }
    }
}
