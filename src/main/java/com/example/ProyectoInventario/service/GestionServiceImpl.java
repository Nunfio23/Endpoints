package com.example.ProyectoInventario.service;

import com.example.ProyectoInventario.dto.GestionResponseDTO;
import com.example.ProyectoInventario.entity.Gestion;
import com.example.ProyectoInventario.entity.Restablecimiento;
import com.example.ProyectoInventario.repository.GestionRepository;
import com.example.ProyectoInventario.repository.RestablecimientoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GestionServiceImpl implements GestionService {

    private final GestionRepository gestionRepository;
    private final RestablecimientoRepository restablecimientoRepository;

    public GestionServiceImpl(GestionRepository gestionRepository,
                              RestablecimientoRepository restablecimientoRepository) {
        this.gestionRepository = gestionRepository;
        this.restablecimientoRepository = restablecimientoRepository;
    }

    @Override
    public List<GestionResponseDTO> listar() {
        return gestionRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public GestionResponseDTO obtenerPorId(Long id) {
        Gestion g = gestionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró la gestión con ID: " + id));
        return toResponse(g);
    }

    // ======================================================
    // MÉTODO: APROBAR
    // ======================================================
    @Override
    public GestionResponseDTO aprobar(Long id, String observacion) {
        Restablecimiento r = restablecimientoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró el restablecimiento con ID: " + id));

        if (!"PENDIENTE".equalsIgnoreCase(r.getEstado())) {
            throw new IllegalArgumentException("Solo se pueden aprobar solicitudes en estado PENDIENTE.");
        }

        // Validar que no exista ya gestión
        if (gestionRepository.existsByRestablecimiento_RestablecimientoId(r.getRestablecimientoId())) {
            throw new IllegalArgumentException("Ya existe una gestión para este restablecimiento.");
        }

        Gestion g = new Gestion();
        g.setRestablecimiento(r);
        g.setAprobado(true);
        g.setObservacion(observacion);
        g.setFechaAprobacion(LocalDateTime.now());

        // Actualiza estado del restablecimiento
        r.setEstado("APROBADO");
        restablecimientoRepository.save(r);

        Gestion guardada = gestionRepository.save(g);
        return toResponse(guardada);
    }

    // ======================================================
    // MÉTODO: RECHAZAR
    // ======================================================
    @Override
    public GestionResponseDTO rechazar(Long id, String observacion) {
        Restablecimiento r = restablecimientoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró el restablecimiento con ID: " + id));

        if (!"PENDIENTE".equalsIgnoreCase(r.getEstado())) {
            throw new IllegalArgumentException("Solo se pueden rechazar solicitudes en estado PENDIENTE.");
        }

        if (observacion == null || observacion.trim().isEmpty()) {
            throw new IllegalArgumentException("Debe especificar una observación al rechazar.");
        }

        if (gestionRepository.existsByRestablecimiento_RestablecimientoId(r.getRestablecimientoId())) {
            throw new IllegalArgumentException("Ya existe una gestión para este restablecimiento.");
        }

        Gestion g = new Gestion();
        g.setRestablecimiento(r);
        g.setAprobado(false);
        g.setObservacion(observacion);
        g.setFechaAprobacion(LocalDateTime.now());

        r.setEstado("RECHAZADO");
        restablecimientoRepository.save(r);

        Gestion guardada = gestionRepository.save(g);
        return toResponse(guardada);
    }

    // ======================================================
    // ELIMINAR
    // ======================================================
    @Override
    public void eliminar(Long id) {
        if (!gestionRepository.existsById(id)) {
            throw new IllegalArgumentException("No existe una gestión con el ID proporcionado.");
        }
        gestionRepository.deleteById(id);
    }

    // ======================================================
    // MAPEO ENTIDAD → DTO
    // ======================================================
    private GestionResponseDTO toResponse(Gestion g) {
        GestionResponseDTO dto = new GestionResponseDTO();
        dto.setGestionId(g.getId());
        dto.setRestablecimientoId(g.getRestablecimiento().getRestablecimientoId());
        dto.setAprobado(g.getAprobado());
        dto.setFechaAprobacion(g.getFechaAprobacion());
        dto.setObservacion(g.getObservacion());
        return dto;
    }
}
