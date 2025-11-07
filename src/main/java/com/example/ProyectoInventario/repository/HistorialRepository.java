package com.example.ProyectoInventario.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ProyectoInventario.entity.Historial;

public interface HistorialRepository extends JpaRepository<Historial, Long> {
    // Si luego quieres filtros: List<Historial> findByUsuarioOrderByFechaRegistroDesc(String usuario);
}
