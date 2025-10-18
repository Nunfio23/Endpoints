package com.example.ProyectoInventario.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.ProyectoInventario.entity.Almacen;
import com.example.ProyectoInventario.entity.Producto;
import com.example.ProyectoInventario.entity.Restablecimiento;
import com.example.ProyectoInventario.repository.AlmacenRepository;
import com.example.ProyectoInventario.repository.ProductoRepository;
import com.example.ProyectoInventario.repository.RestablecimientoRepository;

@Service
public class RestablecimientoServiceImpl implements RestablecimientoService {

    private final RestablecimientoRepository repository;
    private final ProductoRepository productoRepository;
    private final AlmacenRepository almacenRepository;

    // ✅ Constructor con inyección de dependencias
    public RestablecimientoServiceImpl(
            RestablecimientoRepository repository,
            ProductoRepository productoRepository,
            AlmacenRepository almacenRepository
    ) {
        this.repository = repository;
        this.productoRepository = productoRepository;
        this.almacenRepository = almacenRepository;
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
    public Restablecimiento crear(Restablecimiento r) {
        // ✅ Buscar entidades existentes en BD
        Producto producto = productoRepository.findById(r.getProducto().getProductoId()).orElse(null);
        Almacen almacen = almacenRepository.findById(r.getAlmacen().getAlmacenId()).orElse(null);

        if (producto == null || almacen == null) {
            throw new RuntimeException("Producto o Almacén no encontrados");
        }

        r.setProducto(producto);
        r.setAlmacen(almacen);

        return repository.save(r);
    }

    @Override
    public Restablecimiento actualizar(Long id, Restablecimiento r) {
        Restablecimiento existente = repository.findById(id).orElse(null);
        if (existente == null) {
            return null;
        }

        // ✅ Cargar entidades gestionadas por JPA
        Producto producto = productoRepository.findById(r.getProducto().getProductoId()).orElse(null);
        Almacen almacen = almacenRepository.findById(r.getAlmacen().getAlmacenId()).orElse(null);

        if (producto == null || almacen == null) {
            throw new RuntimeException("Producto o Almacén no encontrados");
        }

        existente.setProducto(producto);
        existente.setAlmacen(almacen);
        existente.setCantidad(r.getCantidad());
        existente.setEstado(r.getEstado());

        return repository.save(existente);
    }

    @Override
    public void eliminar(Long id) {
        repository.deleteById(id);
    }
}
