package com.example.ProyectoInventario.dto;

import java.time.LocalDateTime;

public class RestablecimientoResponseDTO {
    private Long restablecimientoId;
    private Long productoId;
    private Long almacenId;
    private String productoNombre;
    private String almacenNombre;
    private Integer cantidad;
    private String estado;
    private LocalDateTime fechaRegistro;

    // Getters y Setters
    public Long getRestablecimientoId() { return restablecimientoId; }
    public void setRestablecimientoId(Long restablecimientoId) { this.restablecimientoId = restablecimientoId; }

    public Long getProductoId() { return productoId; }
    public void setProductoId(Long productoId) { this.productoId = productoId; }

    public Long getAlmacenId() { return almacenId; }
    public void setAlmacenId(Long almacenId) { this.almacenId = almacenId; }

    public String getProductoNombre() { return productoNombre; }
    public void setProductoNombre(String productoNombre) { this.productoNombre = productoNombre; }

    public String getAlmacenNombre() { return almacenNombre; }
    public void setAlmacenNombre(String almacenNombre) { this.almacenNombre = almacenNombre; }

    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public LocalDateTime getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(LocalDateTime fechaRegistro) { this.fechaRegistro = fechaRegistro; }
}
