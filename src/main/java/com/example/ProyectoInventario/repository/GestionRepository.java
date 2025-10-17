package com.example.ProyectoInventario.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ProyectoInventario.entity.Gestion;

public interface GestionRepository extends JpaRepository<Gestion, Long> {
}
