package com.example.ProyectoInventario.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.AssertTrue;

import java.time.LocalDateTime;

@Entity
@Table(name = "restablecimientos")
public class Restablecimiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "restablecimiento_id")
    private Long restablecimientoId;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "producto_id", nullable = false)
    @NotNull(message = "El producto es obligatorio.")
    private Producto producto;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "almacen_id", nullable = false)
    @NotNull(message = "El almacén es obligatorio.")
    private Almacen almacen;

    @NotNull(message = "La cantidad es obligatoria.")
    @Positive(message = "La cantidad debe ser mayor que cero.")
    @Column(nullable = false)
    private Integer cantidad;

    @Column(name = "fecha_solicitud", nullable = false, updatable = false)
    private LocalDateTime fechaSolicitud;

    @Column(length = 20, nullable = false)
    private String estado = "PENDIENTE"; // PENDIENTE, APROBADO, RECHAZADO

    // ===== Validaciones personalizadas =====
    @AssertTrue(message = "La fecha de solicitud no puede ser futura.")
    public boolean isFechaValida() {
        return fechaSolicitud == null || !fechaSolicitud.isAfter(LocalDateTime.now());
    }

    // ===== Hook de persistencia =====
    @PrePersist
    public void prePersist() {
        if (fechaSolicitud == null) {
            fechaSolicitud = LocalDateTime.now();
        }
        if (estado == null) {
            estado = "PENDIENTE";
        }
    }

    // ===== Getters y Setters =====
    public Long getRestablecimientoId() { return restablecimientoId; }
    public void setRestablecimientoId(Long restablecimientoId) { this.restablecimientoId = restablecimientoId; }

    public Producto getProducto() { return producto; }
    public void setProducto(Producto producto) { this.producto = producto; }

    public Almacen getAlmacen() { return almacen; }
    public void setAlmacen(Almacen almacen) { this.almacen = almacen; }

    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }

    public LocalDateTime getFechaSolicitud() { return fechaSolicitud; }
    public void setFechaSolicitud(LocalDateTime fechaSolicitud) { this.fechaSolicitud = fechaSolicitud; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
