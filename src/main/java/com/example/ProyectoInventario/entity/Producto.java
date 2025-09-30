package com.example.ProyectoInventario.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "productos")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "producto_id")
    private Long productoId;

    @Column(nullable = false, unique = true, length = 64, name = "sku")
    private String sku;

    @Column(nullable = false, length = 200, name = "nombre")
    private String nombre;

    // FK -> categorias.categoria_id
    @Column(nullable = false, name = "categoria_id")
    private Long categoriaId;

    @Column(unique = true, length = 64, name = "codigo_barras")
    private String codigoBarras;

    @Column(name = "stock_minimo")
    private Double stockMinimo;

    @Column(name = "stock_maximo")
    private Double stockMaximo;

    @Column(nullable = false, name = "activo")
    private Boolean activo = true;

    @Column(updatable = false, insertable = false,
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP",
            name = "creado_en")
    private LocalDateTime creadoEn;

    @Column(insertable = false,
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP",
            name = "actualizado_en")
    private LocalDateTime actualizadoEn;

    // ===== Getters & Setters =====
    public Long getProductoId() { return productoId; }
    public void setProductoId(Long productoId) { this.productoId = productoId; }

    public String getSku() { return sku; }
    public void setSku(String sku) { this.sku = sku; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public Long getCategoriaId() { return categoriaId; }
    public void setCategoriaId(Long categoriaId) { this.categoriaId = categoriaId; }

    public String getCodigoBarras() { return codigoBarras; }
    public void setCodigoBarras(String codigoBarras) { this.codigoBarras = codigoBarras; }

    public Double getStockMinimo() { return stockMinimo; }
    public void setStockMinimo(Double stockMinimo) { this.stockMinimo = stockMinimo; }

    public Double getStockMaximo() { return stockMaximo; }
    public void setStockMaximo(Double stockMaximo) { this.stockMaximo = stockMaximo; }

    public Boolean getActivo() { return activo; }
    public void setActivo(Boolean activo) { this.activo = activo; }

    public LocalDateTime getCreadoEn() { return creadoEn; }
    public void setCreadoEn(LocalDateTime creadoEn) { this.creadoEn = creadoEn; }

    public LocalDateTime getActualizadoEn() { return actualizadoEn; }
    public void setActualizadoEn(LocalDateTime actualizadoEn) { this.actualizadoEn = actualizadoEn; }
}
