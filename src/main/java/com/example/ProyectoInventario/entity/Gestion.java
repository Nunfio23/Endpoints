package com.example.ProyectoInventario.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "gestiones")
public class Gestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "gestion_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "restablecimiento_id", nullable = false)
    private Restablecimiento restablecimiento;

    @Column(nullable = false)
    private Boolean aprobado = false;

    @Column(name = "fecha_aprobacion")
    private LocalDateTime fechaAprobacion;

    @Column(length = 255)
    private String observacion;

    // ====== Getters y Setters ======

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Restablecimiento getRestablecimiento() {
        return restablecimiento;
    }

    public void setRestablecimiento(Restablecimiento restablecimiento) {
        this.restablecimiento = restablecimiento;
    }

    public Boolean getAprobado() {
        return aprobado;
    }

    public void setAprobado(Boolean aprobado) {
        this.aprobado = aprobado;
    }

    public LocalDateTime getFechaAprobacion() {
        return fechaAprobacion;
    }

    public void setFechaAprobacion(LocalDateTime fechaAprobacion) {
        this.fechaAprobacion = fechaAprobacion;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }
}
