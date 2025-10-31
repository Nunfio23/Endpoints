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

    // ======================================================
    // LISTAR TODAS LAS GESTIONES
    // ======================================================
    @Override
    public List<GestionResponseDTO> listar() {
        return gestionRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // ======================================================
    // OBTENER GESTIÓN POR ID
    // ======================================================
    @Override
    public GestionResponseDTO obtenerPorId(Long id) {
        Gestion g = gestionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró la gestión con ID: " + id));
        return toResponse(g);
    }

    // ======================================================
    // APROBAR UNA GESTIÓN (Y SU RESTABLECIMIENTO ASOCIADO)
    // ======================================================
    @Override
    public GestionResponseDTO aprobar(Long id, String observacion) {
        Gestion g = gestionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró la gestión con ID: " + id));

        Restablecimiento r = g.getRestablecimiento();
        if (r == null) {
            throw new IllegalArgumentException("Esta gestión no tiene un restablecimiento asociado.");
        }

        if (!"PENDIENTE".equalsIgnoreCase(r.getEstado())) {
            throw new IllegalArgumentException("Solo se pueden aprobar solicitudes en estado PENDIENTE.");
        }

        // Actualiza gestión
        g.setAprobado(true);
        g.setObservacion(observacion != null ? observacion : "Aprobado correctamente");
        g.setFechaAprobacion(LocalDateTime.now());

        // Actualiza restablecimiento
        r.setEstado("APROBADO");
        restablecimientoRepository.save(r);
        Gestion guardada = gestionRepository.save(g);

        return toResponse(guardada);
    }

    // ======================================================
    // RECHAZAR UNA GESTIÓN (Y SU RESTABLECIMIENTO ASOCIADO)
    // ======================================================
    @Override
    public GestionResponseDTO rechazar(Long id, String observacion) {
        Gestion g = gestionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró la gestión con ID: " + id));

        Restablecimiento r = g.getRestablecimiento();
        if (r == null) {
            throw new IllegalArgumentException("Esta gestión no tiene un restablecimiento asociado.");
        }

        if (!"PENDIENTE".equalsIgnoreCase(r.getEstado())) {
            throw new IllegalArgumentException("Solo se pueden rechazar solicitudes en estado PENDIENTE.");
        }

        // Validación observación
        if (observacion == null || observacion.trim().isEmpty()) {
            observacion = "Gestión rechazada";
        }

        // Actualiza gestión
        g.setAprobado(false);
        g.setObservacion(observacion);
        g.setFechaAprobacion(LocalDateTime.now());

        // Actualiza restablecimiento
        r.setEstado("RECHAZADO");
        restablecimientoRepository.save(r);
        Gestion guardada = gestionRepository.save(g);

        return toResponse(guardada);
    }

    // ======================================================
    // ELIMINAR GESTIÓN
    // ======================================================
    @Override
    public void eliminar(Long id) {
        if (!gestionRepository.existsById(id)) {
            throw new IllegalArgumentException("No existe una gestión con el ID proporcionado.");
        }
        gestionRepository.deleteById(id);
    }

    // ======================================================
    // MAPEADOR ENTIDAD → DTO
    // ======================================================
    private GestionResponseDTO toResponse(Gestion g) {
        GestionResponseDTO dto = new GestionResponseDTO();
        dto.setGestionId(g.getId());
        dto.setRestablecimientoId(
                g.getRestablecimiento() != null ? g.getRestablecimiento().getRestablecimientoId() : null
        );
        dto.setAprobado(g.getAprobado());
        dto.setFechaAprobacion(g.getFechaAprobacion());
        dto.setObservacion(g.getObservacion());
        return dto;
    }
}
