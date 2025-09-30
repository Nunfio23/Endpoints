package com.example.ProyectoInventario.service;

import com.example.ProyectoInventario.entity.Entrega;
import com.example.ProyectoInventario.repository.EntregaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EntregaServiceImpl implements EntregaService {

    private final EntregaRepository repo;

    public EntregaServiceImpl(EntregaRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<Entrega> listar() {
        return repo.findAll();
    }

    @Override
    public Entrega obtener(Long id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public Entrega crear(Entrega e) {
        return repo.save(e);
    }

    @Override
    public Entrega actualizar(Long id, Entrega datos) {
        return repo.findById(id).map(e -> {
            e.setCantidad(datos.getCantidad());
            e.setProducto(datos.getProducto());
            e.setAlmacen(datos.getAlmacen());
            e.setFechaEntrega(datos.getFechaEntrega());
            return repo.save(e);
        }).orElse(null);
    }

    @Override
    public void eliminar(Long id) {
        repo.deleteById(id);
    }
}