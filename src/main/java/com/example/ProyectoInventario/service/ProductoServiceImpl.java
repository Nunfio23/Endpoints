package com.example.ProyectoInventario.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.ProyectoInventario.dto.ProductoCreateDTO;
import com.example.ProyectoInventario.dto.ProductoResponseDTO;
import com.example.ProyectoInventario.dto.ProductoUpdateDTO;
import com.example.ProyectoInventario.entity.Producto;
import com.example.ProyectoInventario.repository.ProductoRepository;

@Service
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;

    public ProductoServiceImpl(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    @Override
    public List<ProductoResponseDTO> listar() {
        return productoRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ProductoResponseDTO obtenerPorId(Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        return mapToResponse(producto);
    }



    @Override
    public ProductoResponseDTO crear(ProductoCreateDTO dto) {
        Producto producto = new Producto();
        producto.setSku(dto.getSku());
        producto.setNombre(dto.getNombre());
        producto.setCategoriaId(dto.getCategoriaId());
        producto.setCodigoBarras(dto.getCodigoBarras());
        producto.setStockMinimo(dto.getStockMinimo());
        producto.setStockMaximo(dto.getStockMaximo());
        producto.setPrecio(dto.getPrecio());
        producto.setActivo(true);
        producto.setCreadoEn(LocalDateTime.now());
        producto.setActualizadoEn(LocalDateTime.now());

        Producto guardado = productoRepository.save(producto);
        return mapToResponse(guardado);
    }

    @Override
    public ProductoResponseDTO actualizar(Long id, ProductoUpdateDTO dto) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        if (dto.getNombre() != null) producto.setNombre(dto.getNombre());
        if (dto.getCodigoBarras() != null) producto.setCodigoBarras(dto.getCodigoBarras());
        if (dto.getStockMinimo() != null) producto.setStockMinimo(dto.getStockMinimo());
        if (dto.getStockMaximo() != null) producto.setStockMaximo(dto.getStockMaximo());
        if (dto.getPrecio() != null) producto.setPrecio(dto.getPrecio());

        producto.setActualizadoEn(LocalDateTime.now());

        Producto actualizado = productoRepository.save(producto);
        return mapToResponse(actualizado);
    }

    @Override
    public void eliminar(Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        productoRepository.delete(producto);
    }

    @Override
    public ProductoResponseDTO activar(Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        producto.setActivo(true);
        producto.setActualizadoEn(LocalDateTime.now());
        return mapToResponse(productoRepository.save(producto));
    }

    @Override
    public ProductoResponseDTO desactivar(Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        producto.setActivo(false);
        producto.setActualizadoEn(LocalDateTime.now());
        return mapToResponse(productoRepository.save(producto));
    }
    
    @Override
    public List<ProductoResponseDTO> obtenerPorCategoria(Long categoriaId) {
    return productoRepository.findByCategoriaId(categoriaId)
            .stream()
            .map(this::mapToResponse)
            .collect(Collectors.toList());
}

    @Override
    public ProductoResponseDTO obtenerPorNombre(String nombre) {
        Producto producto = productoRepository.findByNombre(nombre)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con nombre: " + nombre));

        ProductoResponseDTO dto = new ProductoResponseDTO();
        dto.setProductoId(producto.getProductoId());
        dto.setSku(producto.getSku());
        dto.setNombre(producto.getNombre());
        dto.setCategoriaId(producto.getCategoriaId());
        dto.setCodigoBarras(producto.getCodigoBarras());
        dto.setStockMinimo(producto.getStockMinimo());
        dto.setStockMaximo(producto.getStockMaximo());
        dto.setPrecio(producto.getPrecio());
        dto.setActivo(producto.isActivo());
        dto.setCreadoEn(producto.getCreadoEn());
        dto.setActualizadoEn(producto.getActualizadoEn());

        return dto;
    }

        @Override
    public ProductoResponseDTO aumentarStock(Long id, int cantidad) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Producto no encontrado con id: " + id));

        producto.setStockMaximo(producto.getStockMaximo().add(java.math.BigDecimal.valueOf(cantidad)));
        Producto actualizado = productoRepository.save(producto);

        return mapToResponse(actualizado);
    }

    

    @Override
    public ProductoResponseDTO disminuirStock(Long id, int cantidad) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Producto no encontrado con id: " + id));

        int stockActual = producto.getStockMaximo().intValue();
        if (cantidad > stockActual) {
            throw new IllegalArgumentException("La cantidad a disminuir excede el stock disponible");
        }

        producto.setStockMaximo(java.math.BigDecimal.valueOf(stockActual - cantidad));
        Producto actualizado = productoRepository.save(producto);

        return mapToResponse(actualizado);
    }

    private ProductoResponseDTO mapToResponse(Producto producto) {
        ProductoResponseDTO dto = new ProductoResponseDTO();
        dto.setProductoId(producto.getProductoId());
        dto.setSku(producto.getSku());
        dto.setNombre(producto.getNombre());
        dto.setCategoriaId(producto.getCategoriaId());
        dto.setCodigoBarras(producto.getCodigoBarras());
        dto.setStockMinimo(producto.getStockMinimo());
        dto.setStockMaximo(producto.getStockMaximo());
        dto.setPrecio(producto.getPrecio());
        dto.setActivo(producto.isActivo());
        dto.setCreadoEn(producto.getCreadoEn());
        dto.setActualizadoEn(producto.getActualizadoEn());
        return dto;
    }
}
