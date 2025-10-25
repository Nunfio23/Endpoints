package com.example.ProyectoInventario.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.ProyectoInventario.entity.Almacen;
import com.example.ProyectoInventario.entity.Entrega;
import com.example.ProyectoInventario.entity.Producto;
import com.example.ProyectoInventario.repository.AlmacenRepository;
import com.example.ProyectoInventario.repository.EntregaRepository;
import com.example.ProyectoInventario.repository.ProductoRepository;

@Service
public class EntregaServiceImpl implements EntregaService {

    private final EntregaRepository entregaRepository;
    private final ProductoRepository productoRepository;
    private final AlmacenRepository almacenRepository;

    public EntregaServiceImpl(EntregaRepository entregaRepository,
                              ProductoRepository productoRepository,
                              AlmacenRepository almacenRepository) {
        this.entregaRepository = entregaRepository;
        this.productoRepository = productoRepository;
        this.almacenRepository = almacenRepository;
    }

    // ============================
    // LISTAR Y OBTENER
    // ============================
    @Override
    public List<Entrega> listar() {
        return entregaRepository.findAll();
    }

    @Override
    public Entrega obtener(Long id) {
        return entregaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Entrega no encontrada con id: " + id));
    }

    // ============================
    // CREAR (con validaciones)
    // ============================
    @Override
    public Entrega crear(Entrega e) {

        if (e == null) {
            throw new IllegalArgumentException("No se recibieron datos de la entrega.");
        }

        if (e.getProducto() == null || e.getProducto().getProductoId() == null) {
            throw new IllegalArgumentException("Debe especificar un producto válido.");
        }

        if (e.getAlmacen() == null || e.getAlmacen().getAlmacenId() == null) {
            throw new IllegalArgumentException("Debe especificar un almacén válido.");
        }

        if (e.getCantidad() == null || e.getCantidad() <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor que cero.");
        }

        // Validar existencia de producto y almacén
        Producto producto = productoRepository.findById(e.getProducto().getProductoId())
                .orElseThrow(() -> new IllegalArgumentException("El producto especificado no existe."));

        Almacen almacen = almacenRepository.findById(e.getAlmacen().getAlmacenId())
                .orElseThrow(() -> new IllegalArgumentException("El almacén especificado no existe."));

        // Validar fecha de entrega no futura
        if (e.getFechaEntrega() != null && e.getFechaEntrega().isAfter(LocalDateTime.now())) {
            throw new IllegalArgumentException("La fecha de entrega no puede ser futura.");
        }

        // Evitar duplicados
        if (entregaRepository.existsByProducto_ProductoIdAndAlmacen_AlmacenIdAndFechaEntrega(
                producto.getProductoId(), almacen.getAlmacenId(), e.getFechaEntrega() != null ? e.getFechaEntrega() : LocalDateTime.now()
        )) {
            throw new IllegalArgumentException("Ya existe una entrega registrada para este producto, almacén y fecha.");
        }

        // Asignar relaciones y fecha
        e.setProducto(producto);
        e.setAlmacen(almacen);
        if (e.getFechaEntrega() == null) e.setFechaEntrega(LocalDateTime.now());

        return entregaRepository.save(e);
    }

    // ============================
    // ACTUALIZAR
    // ============================
    @Override
    public Entrega actualizar(Long id, Entrega datos) {
        Entrega existente = entregaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Entrega no encontrada con id: " + id));

        if (datos.getCantidad() != null && datos.getCantidad() <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor que cero.");
        }

        if (datos.getFechaEntrega() != null && datos.getFechaEntrega().isAfter(LocalDateTime.now())) {
            throw new IllegalArgumentException("La fecha de entrega no puede ser futura.");
        }

        if (datos.getProducto() != null) {
            Producto producto = productoRepository.findById(datos.getProducto().getProductoId())
                    .orElseThrow(() -> new IllegalArgumentException("El producto especificado no existe."));
            existente.setProducto(producto);
        }

        if (datos.getAlmacen() != null) {
            Almacen almacen = almacenRepository.findById(datos.getAlmacen().getAlmacenId())
                    .orElseThrow(() -> new IllegalArgumentException("El almacén especificado no existe."));
            existente.setAlmacen(almacen);
        }

        if (datos.getCantidad() != null) existente.setCantidad(datos.getCantidad());
        if (datos.getFechaEntrega() != null) existente.setFechaEntrega(datos.getFechaEntrega());

        return entregaRepository.save(existente);
    }

    // ============================
    // ELIMINAR
    // ============================
    @Override
    public void eliminar(Long id) {
        if (!entregaRepository.existsById(id)) {
            throw new IllegalArgumentException("No existe una entrega con el ID proporcionado.");
        }
        entregaRepository.deleteById(id);
    }
}
