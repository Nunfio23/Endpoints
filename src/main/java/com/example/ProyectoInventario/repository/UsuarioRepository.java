package com.example.ProyectoInventario.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.ProyectoInventario.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Usuario findByUsername(String username);
}
