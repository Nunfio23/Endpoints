package com.example.ProyectoInventario.dto;

import java.time.LocalDateTime;

public class EntregaResponseDTO {
    private Long entregaId;
    private Long productoId;
    private Long almacenId;
    private String productoNombre;
    private String almacenNombre;
    private Integer cantidad;
    private LocalDateTime fechaEntrega;

    // Getters y Setters
    public Long getEntregaId() { return entregaId; }
    public void setEntregaId(Long entregaId) { this.entregaId = entregaId; }

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

    public LocalDateTime getFechaEntrega() { return fechaEntrega; }
    public void setFechaEntrega(LocalDateTime fechaEntrega) { this.fechaEntrega = fechaEntrega; }
}
