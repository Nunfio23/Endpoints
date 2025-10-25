package com.example.ProyectoInventario.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.ProyectoInventario.dto.ProductoCreateDTO;
import com.example.ProyectoInventario.dto.ProductoResponseDTO;
import com.example.ProyectoInventario.dto.ProductoUpdateDTO;
import com.example.ProyectoInventario.entity.Categoria;
import com.example.ProyectoInventario.entity.Producto;
import com.example.ProyectoInventario.repository.CategoriaRepository;
import com.example.ProyectoInventario.repository.ProductoRepository;

@Service
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;

    // Límites de stock
    private static final int MAX_STOCK_LIMIT = 300; // límite máximo permitido
    private static final int MIN_STOCK_LIMIT = 4;   // límite mínimo permitido

    public ProductoServiceImpl(ProductoRepository productoRepository, CategoriaRepository categoriaRepository) {
        this.productoRepository = productoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    // ===========================
    // LISTAR Y OBTENER
    // ===========================

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
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con id: " + id));
        return mapToResponse(producto);
    }

    // ===========================
    // CREAR PRODUCTO (con validaciones completas)
    // ===========================
    @Override
    public ProductoResponseDTO crear(ProductoCreateDTO dto) {

        // === Validación de entrada ===
        if (dto == null) {
            throw new IllegalArgumentException("No se recibieron datos del producto.");
        }

        if (dto.getNombre() == null || dto.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del producto es obligatorio.");
        }

        if (dto.getCategoriaId() == null) {
            throw new IllegalArgumentException("Debe especificar la categoría del producto.");
        }

        if (dto.getPrecio() == null) {
            throw new IllegalArgumentException("Debe especificar el precio del producto.");
        }

        // Validar duplicado por nombre
        if (productoRepository.existsByNombre(dto.getNombre())) {
            throw new IllegalArgumentException("Ya existe un producto con el nombre: " + dto.getNombre());
        }

        // Validar categoría existente
        Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> new IllegalArgumentException("La categoría con ID " + dto.getCategoriaId() + " no existe."));

        // Validaciones de stock
        if (dto.getStockMaximo() != null && dto.getStockMaximo().intValue() > MAX_STOCK_LIMIT) {
            throw new IllegalArgumentException("El stock no puede exceder " + MAX_STOCK_LIMIT + " unidades.");
        }
        if (dto.getStockMinimo() != null && dto.getStockMinimo().intValue() < MIN_STOCK_LIMIT) {
            throw new IllegalArgumentException("El stock mínimo no puede ser menor a " + MIN_STOCK_LIMIT + " unidades.");
        }

        // Validación de precio no negativo
        if (dto.getPrecio() != null && dto.getPrecio().doubleValue() < 0) {
            throw new IllegalArgumentException("El precio no puede ser negativo.");
        }

        // === Creación del producto ===
        Producto producto = new Producto();
        producto.setSku(dto.getSku());
        producto.setNombre(dto.getNombre());
        producto.setCategoria(categoria); // Asociación con categoría
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

    // ===========================
    // ACTUALIZAR PRODUCTO
    // ===========================
    @Override
    public ProductoResponseDTO actualizar(Long id, ProductoUpdateDTO dto) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con id: " + id));

        if (dto.getNombre() != null) {
            if (productoRepository.existsByNombre(dto.getNombre()) && !producto.getNombre().equals(dto.getNombre())) {
                throw new IllegalArgumentException("Ya existe otro producto con el nombre: " + dto.getNombre());
            }
            producto.setNombre(dto.getNombre());
        }

        if (dto.getCodigoBarras() != null) producto.setCodigoBarras(dto.getCodigoBarras());

        if (dto.getStockMinimo() != null) {
            if (dto.getStockMinimo().intValue() < MIN_STOCK_LIMIT) {
                throw new IllegalArgumentException("El stock mínimo no puede ser menor a " + MIN_STOCK_LIMIT);
            }
            producto.setStockMinimo(dto.getStockMinimo());
        }

        if (dto.getStockMaximo() != null) {
            if (dto.getStockMaximo().intValue() > MAX_STOCK_LIMIT) {
                throw new IllegalArgumentException("El stock no puede exceder " + MAX_STOCK_LIMIT);
            }
            producto.setStockMaximo(dto.getStockMaximo());
        }

        if (dto.getPrecio() != null && dto.getPrecio().doubleValue() < 0) {
            throw new IllegalArgumentException("El precio no puede ser negativo.");
        }
        if (dto.getPrecio() != null) producto.setPrecio(dto.getPrecio());

        producto.setActualizadoEn(LocalDateTime.now());
        Producto actualizado = productoRepository.save(producto);
        return mapToResponse(actualizado);
    }

    // ===========================
    // ELIMINAR / ACTIVAR / DESACTIVAR
    // ===========================
    @Override
    public void eliminar(Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con id: " + id));
        productoRepository.delete(producto);
    }

    @Override
    public ProductoResponseDTO activar(Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con id: " + id));
        producto.setActivo(true);
        producto.setActualizadoEn(LocalDateTime.now());
        return mapToResponse(productoRepository.save(producto));
    }

    @Override
    public ProductoResponseDTO desactivar(Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con id: " + id));
        producto.setActivo(false);
        producto.setActualizadoEn(LocalDateTime.now());
        return mapToResponse(productoRepository.save(producto));
    }

    // ===========================
    // FILTROS
    // ===========================
    @Override
    public List<ProductoResponseDTO> obtenerPorCategoria(Long categoriaId) {
        return productoRepository.findByCategoria_CategoriaId(categoriaId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ProductoResponseDTO obtenerPorNombre(String nombre) {
        Producto producto = productoRepository.findByNombre(nombre)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con nombre: " + nombre));
        return mapToResponse(producto);
    }

    // ===========================
    // GESTIÓN DE STOCK
    // ===========================
    @Override
    public ProductoResponseDTO aumentarStock(Long id, int cantidad) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Producto no encontrado con id: " + id));

        int nuevoStock = producto.getStockMaximo().intValue() + cantidad;
        if (nuevoStock > MAX_STOCK_LIMIT) {
            throw new IllegalArgumentException("No se puede exceder el límite máximo de " + MAX_STOCK_LIMIT + " unidades.");
        }

        producto.setStockMaximo(java.math.BigDecimal.valueOf(nuevoStock));
        Producto actualizado = productoRepository.save(producto);
        return mapToResponse(actualizado);
    }

    @Override
    public ProductoResponseDTO disminuirStock(Long id, int cantidad) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Producto no encontrado con id: " + id));

        int stockActual = producto.getStockMaximo().intValue();
        int nuevoStock = stockActual - cantidad;

        if (nuevoStock < MIN_STOCK_LIMIT) {
            throw new IllegalArgumentException("No se puede disminuir por debajo del stock mínimo de " + MIN_STOCK_LIMIT);
        }

        producto.setStockMaximo(java.math.BigDecimal.valueOf(nuevoStock));
        Producto actualizado = productoRepository.save(producto);
        return mapToResponse(actualizado);
    }

    // ===========================
    // MAPEO DE RESPUESTA
    // ===========================
    private ProductoResponseDTO mapToResponse(Producto producto) {
        ProductoResponseDTO dto = new ProductoResponseDTO();
        dto.setProductoId(producto.getProductoId());
        dto.setSku(producto.getSku());
        dto.setNombre(producto.getNombre());
        dto.setCategoriaId(producto.getCategoria() != null ? producto.getCategoria().getCategoriaId() : null);
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
