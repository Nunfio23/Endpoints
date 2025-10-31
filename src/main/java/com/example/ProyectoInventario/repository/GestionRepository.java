package com.example.ProyectoInventario.repository;

import com.example.ProyectoInventario.entity.Gestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GestionRepository extends JpaRepository<Gestion, Long> {
    boolean existsByRestablecimiento_RestablecimientoId(Long restablecimientoId);
}
