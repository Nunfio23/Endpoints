package com.example.ProyectoInventario.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ProyectoInventario.entity.Producto;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    // Buscar producto por nombre
    Optional<Producto> findByNombre(String nombre);
    List<Producto> findByCategoriaId(Long categoriaId);
}
