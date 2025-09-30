package com.example.ProyectoInventario.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ProyectoInventario.entity.Producto;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    Producto findBySku(String sku);
}
