package com.example.ProyectoInventario.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ProyectoInventario.entity.Restablecimiento;

public interface RestablecimientoRepository extends JpaRepository<Restablecimiento, Long> {
        // ✅ Este método personalizado evita duplicados pendientes
    boolean existsByProducto_ProductoIdAndAlmacen_AlmacenIdAndEstado(
        Long productoId,
        Long almacenId,
        String estado
    );
}
