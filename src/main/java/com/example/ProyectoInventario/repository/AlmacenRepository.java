package com.example.ProyectoInventario.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ProyectoInventario.entity.Almacen;

@Repository
public interface AlmacenRepository extends JpaRepository<Almacen, Long> {
    boolean existsByCodigo(String codigo);
    Almacen findByCodigo(String codigo);
}

