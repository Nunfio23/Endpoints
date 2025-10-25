package com.example.ProyectoInventario.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ProyectoInventario.entity.Producto;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    // Buscar producto por nombre (usado en el servicio)
    Optional<Producto> findByNombre(String nombre);

    // Verificar si ya existe un producto con ese nombre (validación de duplicados)
    boolean existsByNombre(String nombre);

    // Buscar productos por categoría (corregido para relación ManyToOne)
    List<Producto> findByCategoria_CategoriaId(Long categoriaId);
}
