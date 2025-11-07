package com.example.ProyectoInventario.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "historiales")
public class Historial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "historial_id")
    private Long id;

    @NotNull(message = "El tipo de solicitud es obligatorio.")
    @Size(max = 80, message = "El tipo no debe exceder 80 caracteres.")
    @Column(nullable = false, length = 80)
    private String tipo;            // Ej: "Ingreso de producto", "Salida", etc.

    @NotNull(message = "El usuario es obligatorio.")
    @Size(max = 80, message = "El usuario no debe exceder 80 caracteres.")
    @Column(nullable = false, length = 80)
    private String usuario;         // Ej: "dev", "juanperez"

    @Size(max = 500, message = "La descripción no debe exceder 500 caracteres.")
    @Column(length = 500)
    private String descripcion;     // Detalle opcional

    @Column(name = "fecha_registro", nullable = false)
    private LocalDateTime fechaRegistro;

    // ====== Reglas / validaciones adicionales ======
    @AssertTrue(message = "La fecha de registro no puede ser futura.")
    public boolean isFechaValida() {
        if (fechaRegistro == null) return true;
        return !fechaRegistro.isAfter(LocalDateTime.now());
    }

    @PrePersist
    public void prePersist() {
        if (this.fechaRegistro == null) {
            this.fechaRegistro = LocalDateTime.now();
        }
    }

    // ====== GETTERS / SETTERS ======
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getUsuario() { return usuario; }
    public void setUsuario(String usuario) { this.usuario = usuario; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public LocalDateTime getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(LocalDateTime fechaRegistro) { this.fechaRegistro = fechaRegistro; }
}
