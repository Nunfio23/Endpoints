package com.example.ProyectoInventario.service;

import com.example.ProyectoInventario.dto.AlmacenCreateDTO;
import com.example.ProyectoInventario.dto.AlmacenUpdateDTO;
import com.example.ProyectoInventario.dto.AlmacenResponseDTO;
import com.example.ProyectoInventario.entity.Almacen;
import com.example.ProyectoInventario.repository.AlmacenRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AlmacenServiceImpl implements AlmacenService {

    private final AlmacenRepository almacenRepository;

    public AlmacenServiceImpl(AlmacenRepository almacenRepository) {
        this.almacenRepository = almacenRepository;
    }

    // =============================
    // LISTAR
    // =============================
    @Override
    public List<AlmacenResponseDTO> listar() {
        return almacenRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    // =============================
    // OBTENER
    // =============================
    @Override
    public AlmacenResponseDTO obtener(Long id) {
        Almacen almacen = almacenRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Almacén no encontrado con ID: " + id));
        return toResponseDTO(almacen);
    }

    // =============================
    // CREAR
    // =============================
    @Override
    public AlmacenResponseDTO crear(AlmacenCreateDTO dto) {
        if (dto == null) throw new IllegalArgumentException("Los datos del almacén son obligatorios.");

        Almacen almacen = new Almacen();
        almacen.setCodigo(dto.getCodigo());
        almacen.setNombre(dto.getNombre());
        almacen.setTipo(dto.getTipo());
        almacen.setDireccion(dto.getDireccion());
        almacen.setCiudad(dto.getCiudad());
        almacen.setPais(dto.getPais());
        almacen.setActivo(dto.isActivo());

        Almacen guardado = almacenRepository.save(almacen);
        return toResponseDTO(guardado);
    }

    // =============================
    // ACTUALIZAR
    // =============================
    @Override
    public AlmacenResponseDTO actualizar(Long id, AlmacenUpdateDTO dto) {
        Almacen existente = almacenRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Almacén no encontrado con ID: " + id));

        existente.setCodigo(dto.getCodigo());
        existente.setNombre(dto.getNombre());
        existente.setTipo(dto.getTipo());
        existente.setDireccion(dto.getDireccion());
        existente.setCiudad(dto.getCiudad());
        existente.setPais(dto.getPais());
        existente.setActivo(dto.isActivo());

        Almacen actualizado = almacenRepository.save(existente);
        return toResponseDTO(actualizado);
    }

    // =============================
    // ELIMINAR
    // =============================
    @Override
    public void eliminar(Long id) {
        if (!almacenRepository.existsById(id)) {
            throw new IllegalArgumentException("No existe un almacén con el ID proporcionado.");
        }
        almacenRepository.deleteById(id);
    }

    // =============================
    // CONVERSIÓN: Entidad → DTO
    // =============================
    private AlmacenResponseDTO toResponseDTO(Almacen almacen) {
        AlmacenResponseDTO dto = new AlmacenResponseDTO();
        dto.setAlmacenId(almacen.getAlmacenId());
        dto.setCodigo(almacen.getCodigo());
        dto.setNombre(almacen.getNombre());
        dto.setTipo(almacen.getTipo());
        dto.setDireccion(almacen.getDireccion());
        dto.setCiudad(almacen.getCiudad());
        dto.setPais(almacen.getPais());
        dto.setActivo(almacen.getActivo()); // ✅ usa getActivo() si es Boolean
        return dto;
    }
}
