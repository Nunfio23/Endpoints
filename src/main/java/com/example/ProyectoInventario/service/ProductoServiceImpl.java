package com.example.ProyectoInventario.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.ProyectoInventario.entity.Producto;
import com.example.ProyectoInventario.repository.ProductoRepository;

@Service
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository repo;

    public ProductoServiceImpl(ProductoRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<Producto> listar() {
        return repo.findAll();
    }

    @Override
    public Producto obtener(Long id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public Producto crear(Producto p) {
        return repo.save(p);
    }

    @Override
    public Producto actualizar(Long id, Producto datos) {
        return repo.findById(id).map(p -> {
            p.setSku(datos.getSku());
            p.setNombre(datos.getNombre());
            p.setCategoriaId(datos.getCategoriaId());
            p.setCodigoBarras(datos.getCodigoBarras());
            p.setStockMinimo(datos.getStockMinimo());
            p.setStockMaximo(datos.getStockMaximo());
            p.setActivo(datos.getActivo());
            return repo.save(p);
        }).orElse(null);
    }

    @Override
    public void eliminar(Long id) {
        repo.deleteById(id);
    }
}