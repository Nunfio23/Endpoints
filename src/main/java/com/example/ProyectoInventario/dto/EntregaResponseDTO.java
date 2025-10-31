package com.example.ProyectoInventario.dto;

import java.time.LocalDateTime;

public class EntregaResponseDTO {
    private Long entregaId;
    private Long productoId;
    private Long almacenId;
    private Integer cantidad;
    private LocalDateTime fechaEntrega;

    // Getters y Setters
    public Long getEntregaId() { return entregaId; }
    public void setEntregaId(Long entregaId) { this.entregaId = entregaId; }

    public Long getProductoId() { return productoId; }
    public void setProductoId(Long productoId) { this.productoId = productoId; }

    public Long getAlmacenId() { return almacenId; }
    public void setAlmacenId(Long almacenId) { this.almacenId = almacenId; }

    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }

    public LocalDateTime getFechaEntrega() { return fechaEntrega; }
    public void setFechaEntrega(LocalDateTime fechaEntrega) { this.fechaEntrega = fechaEntrega; }
}
