package com.example.ProyectoInventario.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "gestiones")
public class Gestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "gestion_id")
    private Long id;

    @OneToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "restablecimiento_id", nullable = false)
    @NotNull(message = "Debe asociar un restablecimiento válido.")
    private Restablecimiento restablecimiento;

    @Column(nullable = false)
    private Boolean aprobado = false;

    @Column(name = "fecha_aprobacion")
    private LocalDateTime fechaAprobacion;

    @Column(length = 255)
    private String observacion;

    // =========================
    // VALIDACIONES
    // =========================
    @AssertTrue(message = "No se puede asignar una fecha de aprobación futura.")
    public boolean isFechaValida() {
        if (fechaAprobacion == null) return true;
        return !fechaAprobacion.isAfter(LocalDateTime.now());
    }

    // =========================
    // GETTERS Y SETTERS
    // =========================
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Restablecimiento getRestablecimiento() { return restablecimiento; }
    public void setRestablecimiento(Restablecimiento restablecimiento) { this.restablecimiento = restablecimiento; }

    public Boolean getAprobado() { return aprobado; }
    public void setAprobado(Boolean aprobado) { this.aprobado = aprobado; }

    public LocalDateTime getFechaAprobacion() { return fechaAprobacion; }
    public void setFechaAprobacion(LocalDateTime fechaAprobacion) { this.fechaAprobacion = fechaAprobacion; }

    public String getObservacion() { return observacion; }
    public void setObservacion(String observacion) { this.observacion = observacion; }
}
