package com.example.ProyectoInventario.service;


import java.util.List;

import org.springframework.stereotype.Service;

import com.example.ProyectoInventario.entity.Almacen;
import com.example.ProyectoInventario.repository.AlmacenRepository;

@Service
public class AlmacenServiceImpl implements AlmacenService {

    private final AlmacenRepository repo;

    public AlmacenServiceImpl(AlmacenRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<Almacen> listar() {
        return repo.findAll();
    }

    @Override
    public Almacen obtener(Long id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public Almacen crear(Almacen a) {
        return repo.save(a);
    }

    @Override
    public Almacen actualizar(Long id, Almacen datos) {
        return repo.findById(id).map(a -> {
            a.setNombre(datos.getNombre());
            a.setTipo(datos.getTipo());
            a.setDireccion(datos.getDireccion());
            a.setCiudad(datos.getCiudad());
            a.setPais(datos.getPais());
            a.setActivo(datos.getActivo());
            return repo.save(a);
        }).orElse(null);
    }

    @Override
    public void eliminar(Long id) {
        repo.deleteById(id);
    }
}