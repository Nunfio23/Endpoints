package com.example.ProyectoInventario.service;

import com.example.ProyectoInventario.dto.ProductoCreateDTO;
import com.example.ProyectoInventario.dto.ProductoResponseDTO;
import com.example.ProyectoInventario.entity.Producto;
import com.example.ProyectoInventario.repository.ProductoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository repo;

    public ProductoServiceImpl(ProductoRepository repo) {
        this.repo = repo;
    }

    @Override
    public ProductoResponseDTO crear(ProductoCreateDTO dto) {
        Producto p = new Producto();
        p.setSku(dto.getSku());
        p.setNombre(dto.getNombre());
        p.setCategoriaId(dto.getCategoriaId());
        p.setCodigoBarras(dto.getCodigoBarras());
        p.setStockMinimo(dto.getStockMinimo());
        p.setStockMaximo(dto.getStockMaximo());
        p.setPrecio(dto.getPrecio());
        p.setActivo(true);

        Producto guardado = repo.save(p);
        return toResponseDTO(guardado);
    }

    @Override
    public ProductoResponseDTO obtener(Long id) {
        return repo.findById(id)
                .map(this::toResponseDTO)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
    }

    @Override
    public List<ProductoResponseDTO> listar() {
        return repo.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ProductoResponseDTO actualizar(Long id, ProductoCreateDTO dto) {
        Producto p = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        p.setSku(dto.getSku());
        p.setNombre(dto.getNombre());
        p.setCategoriaId(dto.getCategoriaId());
        p.setCodigoBarras(dto.getCodigoBarras());
        p.setStockMinimo(dto.getStockMinimo());
        p.setStockMaximo(dto.getStockMaximo());
        p.setPrecio(dto.getPrecio());

        Producto actualizado = repo.save(p);
        return toResponseDTO(actualizado);
    }

    @Override
    public void eliminar(Long id) {
        if (!repo.existsById(id)) {
            throw new RuntimeException("Producto no encontrado");
        }
        repo.deleteById(id);
    }

    // 🔹 Método privado para convertir Entidad → DTO
    private ProductoResponseDTO toResponseDTO(Producto p) {
        ProductoResponseDTO dto = new ProductoResponseDTO();
        dto.setProductoId(p.getProductoId());
        dto.setSku(p.getSku());
        dto.setNombre(p.getNombre());
        dto.setCategoriaId(p.getCategoriaId());
        dto.setCodigoBarras(p.getCodigoBarras());
        dto.setStockMinimo(p.getStockMinimo());
        dto.setStockMaximo(p.getStockMaximo());
        dto.setPrecio(p.getPrecio());
        dto.setActivo(p.getActivo());
        dto.setCreadoEn(p.getCreadoEn());
        dto.setActualizadoEn(p.getActualizadoEn());
        return dto;
    }
}
