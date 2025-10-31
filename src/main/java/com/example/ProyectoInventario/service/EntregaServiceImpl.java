package com.example.ProyectoInventario.service;

import com.example.ProyectoInventario.dto.EntregaCreateDTO;
import com.example.ProyectoInventario.dto.EntregaUpdateDTO;
import com.example.ProyectoInventario.dto.EntregaResponseDTO;
import com.example.ProyectoInventario.entity.Almacen;
import com.example.ProyectoInventario.entity.Entrega;
import com.example.ProyectoInventario.entity.Producto;
import com.example.ProyectoInventario.repository.AlmacenRepository;
import com.example.ProyectoInventario.repository.EntregaRepository;
import com.example.ProyectoInventario.repository.ProductoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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
    public List<EntregaResponseDTO> listar() {
        return entregaRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public EntregaResponseDTO obtener(Long id) {
        Entrega entrega = entregaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Entrega no encontrada con id: " + id));
        return toResponseDTO(entrega);
    }

    // ============================
    // CREAR (con validaciones)
    // ============================
    @Override
    public EntregaResponseDTO crear(EntregaCreateDTO dto) {

        if (dto == null) {
            throw new IllegalArgumentException("No se recibieron datos de la entrega.");
        }

        if (dto.getProductoId() == null) {
            throw new IllegalArgumentException("Debe especificar un producto válido.");
        }

        if (dto.getAlmacenId() == null) {
            throw new IllegalArgumentException("Debe especificar un almacén válido.");
        }

        if (dto.getCantidad() == null || dto.getCantidad() <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor que cero.");
        }

        // Validar existencia de producto y almacén
        Producto producto = productoRepository.findById(dto.getProductoId())
                .orElseThrow(() -> new IllegalArgumentException("El producto especificado no existe."));

        Almacen almacen = almacenRepository.findById(dto.getAlmacenId())
                .orElseThrow(() -> new IllegalArgumentException("El almacén especificado no existe."));

        // Evitar duplicados (mismo producto, almacén y fecha actual)
        LocalDateTime fecha = LocalDateTime.now();
        if (entregaRepository.existsByProducto_ProductoIdAndAlmacen_AlmacenIdAndFechaEntrega(
                producto.getProductoId(), almacen.getAlmacenId(), fecha)) {
            throw new IllegalArgumentException("Ya existe una entrega registrada para este producto, almacén y fecha.");
        }

        // Crear nueva entrega
        Entrega entrega = new Entrega();
        entrega.setProducto(producto);
        entrega.setAlmacen(almacen);
        entrega.setCantidad(dto.getCantidad());
        entrega.setFechaEntrega(fecha);

        Entrega guardada = entregaRepository.save(entrega);
        return toResponseDTO(guardada);
    }

    // ============================
    // ACTUALIZAR (con validaciones)
    // ============================
    @Override
    public EntregaResponseDTO actualizar(Long id, EntregaUpdateDTO dto) {
        Entrega existente = entregaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Entrega no encontrada con id: " + id));

        if (dto.getCantidad() != null && dto.getCantidad() <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor que cero.");
        }

        if (dto.getFechaEntrega() != null && dto.getFechaEntrega().isAfter(LocalDateTime.now())) {
            throw new IllegalArgumentException("La fecha de entrega no puede ser futura.");
        }

        if (dto.getCantidad() != null) existente.setCantidad(dto.getCantidad());
        if (dto.getFechaEntrega() != null) existente.setFechaEntrega(dto.getFechaEntrega());

        Entrega actualizada = entregaRepository.save(existente);
        return toResponseDTO(actualizada);
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

    // ============================
    // CONVERSIÓN ENTIDAD → DTO
    // ============================
    private EntregaResponseDTO toResponseDTO(Entrega e) {
        EntregaResponseDTO dto = new EntregaResponseDTO();
        dto.setEntregaId(e.getEntregaId());
        dto.setProductoId(e.getProducto() != null ? e.getProducto().getProductoId() : null);
        dto.setAlmacenId(e.getAlmacen() != null ? e.getAlmacen().getAlmacenId() : null);
        dto.setCantidad(e.getCantidad());
        dto.setFechaEntrega(e.getFechaEntrega());
        return dto;
    }
}
