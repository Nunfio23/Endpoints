package com.example.ProyectoInventario.dto;

public class RestablecimientoResponseDTO {
    private Long restablecimientoId;
    private Long productoId;
    private Long almacenId;
    private Integer cantidad;
    private String estado;

    // Getters y Setters
    public Long getRestablecimientoId() {
        return restablecimientoId;
    }

    public void setRestablecimientoId(Long restablecimientoId) {
        this.restablecimientoId = restablecimientoId;
    }

    public Long getProductoId() {
        return productoId;
    }

    public void setProductoId(Long productoId) {
        this.productoId = productoId;
    }

    public Long getAlmacenId() {
        return almacenId;
    }

    public void setAlmacenId(Long almacenId) {
        this.almacenId = almacenId;
    }

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
