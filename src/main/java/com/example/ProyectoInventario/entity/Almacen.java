package com.example.ProyectoInventario.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "almacenes")
public class Almacen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long almacenId;

    @NotBlank(message = "El código del almacén es obligatorio.")
    @Size(max = 50, message = "El código no debe superar los 50 caracteres.")
    @Column(nullable = false, unique = true, length = 50)
    private String codigo;

    @NotBlank(message = "El nombre del almacén es obligatorio.")
    @Size(max = 100, message = "El nombre no debe superar los 100 caracteres.")
    @Column(nullable = false, length = 100)
    private String nombre;

    @NotBlank(message = "El tipo del almacén es obligatorio.")
    @Size(max = 50, message = "El tipo no debe superar los 50 caracteres.")
    @Column(nullable = false, length = 50)
    private String tipo;

    @NotBlank(message = "La dirección del almacén es obligatoria.")
    @Size(max = 150, message = "La dirección no debe superar los 150 caracteres.")
    @Column(nullable = false, length = 150)
    private String direccion;

    @NotBlank(message = "La ciudad es obligatoria.")
    @Size(max = 100, message = "La ciudad no debe superar los 100 caracteres.")
    @Column(nullable = false, length = 100)
    private String ciudad;

    @NotBlank(message = "El país es obligatorio.")
    @Size(max = 100, message = "El país no debe superar los 100 caracteres.")
    @Column(nullable = false, length = 100)
    private String pais;

    @Column(nullable = false)
    private Boolean activo = true;

    // ===== Getters y Setters =====
    public Long getAlmacenId() {
        return almacenId;
    }

    public void setAlmacenId(Long almacenId) {
        this.almacenId = almacenId;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
}
