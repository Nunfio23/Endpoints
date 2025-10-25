package com.example.ProyectoInventario.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Entity
@Table(name = "entregas")
public class Entrega {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long entregaId;

    // 🔹 Cargar siempre el producto (EAGER)
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "producto_id", nullable = false)
    @NotNull(message = "El producto asociado es obligatorio.")
    private Producto producto;

    // 🔹 Cargar siempre el almacén (EAGER)
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "almacen_id", nullable = false)
    @NotNull(message = "El almacén asociado es obligatorio.")
    private Almacen almacen;

    @NotNull(message = "La cantidad es obligatoria.")
    @Positive(message = "La cantidad debe ser mayor que cero.")
    @Column(nullable = false)
    private Integer cantidad;

    @Column(nullable = false, updatable = false)
    private LocalDateTime fechaEntrega;

    // =======================
    // VALIDACIÓN DE FECHA
    // =======================
    @AssertTrue(message = "La fecha de entrega no puede ser futura.")
    public boolean isFechaValida() {
        if (fechaEntrega == null) return true;
        return !fechaEntrega.isAfter(LocalDateTime.now());
    }

    // =======================
    // HOOK DE PERSISTENCIA
    // =======================
    @PrePersist
    public void prePersist() {
        if (this.fechaEntrega == null) {
            this.fechaEntrega = LocalDateTime.now();
        }
    }

    // =======================
    // GETTERS Y SETTERS
    // =======================
    public Long getEntregaId() {
        return entregaId;
    }

    public void setEntregaId(Long entregaId) {
        this.entregaId = entregaId;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Almacen getAlmacen() {
        return almacen;
    }

    public void setAlmacen(Almacen almacen) {
        this.almacen = almacen;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public LocalDateTime getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(LocalDateTime fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }
}
