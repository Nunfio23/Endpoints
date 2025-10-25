package com.example.ProyectoInventario.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ProyectoInventario.entity.Gestion;

@Repository
public interface GestionRepository extends JpaRepository<Gestion, Long> {

    // Verifica si ya existe una gestión asociada a un restablecimiento específico
    boolean existsByRestablecimiento_RestablecimientoId(Long restablecimientoId);
}
