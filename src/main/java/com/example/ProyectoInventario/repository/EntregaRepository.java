package com.example.ProyectoInventario.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ProyectoInventario.entity.Entrega;

@Repository
public interface EntregaRepository extends JpaRepository<Entrega, Long> {

    List<Entrega> findByAlmacen_AlmacenId(Long almacenId);
    List<Entrega> findByProducto_ProductoId(Long productoId);

    // Validación: verificar duplicado (por producto, almacén y fecha)
    Optional<Entrega> findByProducto_ProductoIdAndAlmacen_AlmacenIdAndFechaEntrega(
            Long productoId, Long almacenId, java.time.LocalDateTime fechaEntrega
    );

    // Evitar múltiples registros duplicados del mismo producto en el mismo almacén en la misma fecha
    boolean existsByProducto_ProductoIdAndAlmacen_AlmacenIdAndFechaEntrega(
            Long productoId, Long almacenId, java.time.LocalDateTime fechaEntrega
    );
}
