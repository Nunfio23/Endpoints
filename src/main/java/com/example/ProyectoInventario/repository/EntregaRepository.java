package com.example.ProyectoInventario.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ProyectoInventario.entity.Entrega;

@Repository
public interface EntregaRepository extends JpaRepository<Entrega, Long> {

    List<Entrega> findByAlmacen_AlmacenId(Long almacenId);
    List<Entrega> findByProducto_ProductoId(Long productoId);
}
