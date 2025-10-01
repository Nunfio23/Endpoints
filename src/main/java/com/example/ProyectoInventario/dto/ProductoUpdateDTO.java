package com.example.ProyectoInventario.dto;

import java.math.BigDecimal;

public class ProductoUpdateDTO {

    private String nombre;
    private String codigoBarras;
    private BigDecimal stockMinimo;
    private BigDecimal stockMaximo;
    private BigDecimal precio;

    // Getters y Setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getCodigoBarras() { return codigoBarras; }
    public void setCodigoBarras(String codigoBarras) { this.codigoBarras = codigoBarras; }

    public BigDecimal getStockMinimo() { return stockMinimo; }
    public void setStockMinimo(BigDecimal stockMinimo) { this.stockMinimo = stockMinimo; }

    public BigDecimal getStockMaximo() { return stockMaximo; }
    public void setStockMaximo(BigDecimal stockMaximo) { this.stockMaximo = stockMaximo; }

    public BigDecimal getPrecio() { return precio; }
    public void setPrecio(BigDecimal precio) { this.precio = precio; }
}
