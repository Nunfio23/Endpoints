package com.example.ProyectoInventario.service;

import com.example.ProyectoInventario.entity.Almacen;
import com.example.ProyectoInventario.entity.Producto;
import com.example.ProyectoInventario.entity.Restablecimiento;
import com.example.ProyectoInventario.repository.AlmacenRepository;
import com.example.ProyectoInventario.repository.ProductoRepository;
import com.example.ProyectoInventario.repository.RestablecimientoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestablecimientoServiceImpl implements RestablecimientoService {

    private final RestablecimientoRepository repository;
    private final ProductoRepository productoRepository;
    private final AlmacenRepository almacenRepository;

    public RestablecimientoServiceImpl(RestablecimientoRepository repository,
                                       ProductoRepository productoRepository,
                                       AlmacenRepository almacenRepository) {
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
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró el restablecimiento con ID: " + id));
    }

    @Override
    public Restablecimiento crear(Restablecimiento r) {
        if (r.getCantidad() == null || r.getCantidad() <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor que cero.");
        }

        Producto producto = productoRepository.findById(r.getProducto().getProductoId())
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado con ID: " + r.getProducto().getProductoId()));

        Almacen almacen = almacenRepository.findById(r.getAlmacen().getAlmacenId())
                .orElseThrow(() -> new IllegalArgumentException("Almacén no encontrado con ID: " + r.getAlmacen().getAlmacenId()));

        r.setProducto(producto);
        r.setAlmacen(almacen);

        return repository.save(r);
    }

    @Override
    public Restablecimiento actualizar(Long id, Restablecimiento datos) {
        Restablecimiento existente = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró el restablecimiento con ID: " + id));

        if (datos.getCantidad() != null && datos.getCantidad() <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor que cero.");
        }

        if (datos.getProducto() != null) {
            Producto producto = productoRepository.findById(datos.getProducto().getProductoId())
                    .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado."));
            existente.setProducto(producto);
        }

        if (datos.getAlmacen() != null) {
            Almacen almacen = almacenRepository.findById(datos.getAlmacen().getAlmacenId())
                    .orElseThrow(() -> new IllegalArgumentException("Almacén no encontrado."));
            existente.setAlmacen(almacen);
        }

        if (datos.getCantidad() != null) existente.setCantidad(datos.getCantidad());
        if (datos.getEstado() != null) existente.setEstado(datos.getEstado());

        return repository.save(existente);
    }

    @Override
    public void eliminar(Long id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("No existe un restablecimiento con ID: " + id);
        }
        repository.deleteById(id);
    }
}
