package com.example.ProyectoInventario.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.ProyectoInventario.entity.Categoria;
import com.example.ProyectoInventario.repository.CategoriaRepository;

@Service
public class CategoriaServiceImpl implements CategoriaService {

    private final CategoriaRepository repo;

    public CategoriaServiceImpl(CategoriaRepository repo) {
        this.repo = repo;
    }

    @Override public List<Categoria> listar() { return repo.findAll(); }
    @Override public Categoria obtener(Long id) { return repo.findById(id).orElse(null); }
    @Override public Categoria crear(Categoria c) { return repo.save(c); }
    @Override public Categoria actualizar(Long id, Categoria datos) {
        return repo.findById(id).map(c -> {
            c.setNombre(datos.getNombre());
            c.setDescripcion(datos.getDescripcion());
            return repo.save(c);
        }).orElse(null);
    }
    @Override public void eliminar(Long id) { repo.deleteById(id); }
}
