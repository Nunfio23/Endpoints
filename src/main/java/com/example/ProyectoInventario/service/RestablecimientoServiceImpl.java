package com.example.ProyectoInventario.service;

import com.example.ProyectoInventario.dto.RestablecimientoCreateDTO;
import com.example.ProyectoInventario.dto.RestablecimientoUpdateDTO;
import com.example.ProyectoInventario.dto.RestablecimientoResponseDTO;
import com.example.ProyectoInventario.entity.Almacen;
import com.example.ProyectoInventario.entity.Producto;
import com.example.ProyectoInventario.entity.Restablecimiento;
import com.example.ProyectoInventario.repository.AlmacenRepository;
import com.example.ProyectoInventario.repository.ProductoRepository;
import com.example.ProyectoInventario.repository.RestablecimientoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    // ============================
    // LISTAR Y OBTENER
    // ============================
    @Override
    public List<RestablecimientoResponseDTO> listar() {
        return repository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public RestablecimientoResponseDTO obtenerPorId(Long id) {
        Restablecimiento r = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró el restablecimiento con ID: " + id));
        return toResponseDTO(r);
    }

    // ============================
    // CREAR (con validaciones)
    // ============================
    @Override
    public RestablecimientoResponseDTO crear(RestablecimientoCreateDTO dto) {

        if (dto == null) {
            throw new IllegalArgumentException("No se recibieron datos del restablecimiento.");
        }

        if (dto.getCantidad() == null || dto.getCantidad() <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor que cero.");
        }

        Producto producto = productoRepository.findById(dto.getProductoId())
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado con ID: " + dto.getProductoId()));

        Almacen almacen = almacenRepository.findById(dto.getAlmacenId())
                .orElseThrow(() -> new IllegalArgumentException("Almacén no encontrado con ID: " + dto.getAlmacenId()));

        // Nueva validación: evitar duplicados pendientes
        boolean existePendiente = repository.existsByProducto_ProductoIdAndAlmacen_AlmacenIdAndEstado(
                producto.getProductoId(), almacen.getAlmacenId(), "PENDIENTE");

        if (existePendiente) {
            throw new IllegalArgumentException("Ya existe una solicitud de restablecimiento pendiente para este producto y almacén.");
        }

        // Crear nuevo restablecimiento
        Restablecimiento r = new Restablecimiento();
        r.setProducto(producto);
        r.setAlmacen(almacen);
        r.setCantidad(dto.getCantidad());
        r.setEstado("PENDIENTE"); // estado inicial

        Restablecimiento guardado = repository.save(r);
        return toResponseDTO(guardado);
    }

    // ============================
    // ACTUALIZAR
    // ============================
    @Override
    public RestablecimientoResponseDTO actualizar(Long id, RestablecimientoUpdateDTO dto) {
        Restablecimiento existente = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró el restablecimiento con ID: " + id));

        if (dto.getCantidad() != null && dto.getCantidad() <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor que cero.");
        }

        if (dto.getCantidad() != null) existente.setCantidad(dto.getCantidad());
        if (dto.getEstado() != null) {
            String estado = dto.getEstado().toUpperCase();
            if (!estado.equals("PENDIENTE") && !estado.equals("APROBADO") && !estado.equals("RECHAZADO")) {
                throw new IllegalArgumentException("Estado inválido. Solo se permite PENDIENTE, APROBADO o RECHAZADO.");
            }
            existente.setEstado(estado);
        }

        Restablecimiento actualizado = repository.save(existente);
        return toResponseDTO(actualizado);
    }

    // ============================
    // ELIMINAR
    // ============================
    @Override
    public void eliminar(Long id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("No existe un restablecimiento con el ID proporcionado.");
        }
        repository.deleteById(id);
    }

    // ============================
    // CONVERSIÓN: ENTIDAD → DTO
    // ============================
    private RestablecimientoResponseDTO toResponseDTO(Restablecimiento r) {
        RestablecimientoResponseDTO dto = new RestablecimientoResponseDTO();
        dto.setRestablecimientoId(r.getRestablecimientoId());
        dto.setProductoId(r.getProducto().getProductoId());
        dto.setAlmacenId(r.getAlmacen().getAlmacenId());
        dto.setCantidad(r.getCantidad());
        dto.setEstado(r.getEstado());
        return dto;
    }
}