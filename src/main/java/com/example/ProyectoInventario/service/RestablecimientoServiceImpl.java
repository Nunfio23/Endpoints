package com.example.ProyectoInventario.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.ProyectoInventario.entity.Restablecimiento;
import com.example.ProyectoInventario.repository.RestablecimientoRepository;

@Service
public class RestablecimientoServiceImpl implements RestablecimientoService {

    private final RestablecimientoRepository repository;

    public RestablecimientoServiceImpl(RestablecimientoRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Restablecimiento> listar() {
        return repository.findAll();
    }

    @Override
    public Restablecimiento obtenerPorId(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Restablecimiento crear(Restablecimiento restablecimiento) {
        return repository.save(restablecimiento);
    }

    @Override
    public Restablecimiento actualizar(Long id, Restablecimiento restablecimiento) {
        Restablecimiento existente = repository.findById(id).orElse(null);
        if (existente == null) {
            return null;
        }
        existente.setCantidad(restablecimiento.getCantidad());
        existente.setEstado(restablecimiento.getEstado());
        existente.setProducto(restablecimiento.getProducto());
        existente.setAlmacen(restablecimiento.getAlmacen());
        return repository.save(existente);
    }

    @Override
    public void eliminar(Long id) {
        repository.deleteById(id);
    }
}
