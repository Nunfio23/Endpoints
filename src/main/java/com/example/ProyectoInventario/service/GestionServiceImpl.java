package com.example.ProyectoInventario.service;

import com.example.ProyectoInventario.dto.GestionCreateDTO;
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

    // ========= NUEVOS =========
@Override
public GestionResponseDTO crear(GestionCreateDTO dto) {
    if (dto == null) {
        throw new IllegalArgumentException("No se recibieron datos de la gestión.");
    }

    // ✅ Tomar el ID ya sea del campo plano o del objeto anidado
    Long restId = dto.getRestablecimientoId() != null
            ? dto.getRestablecimientoId()
            : (dto.getRestablecimiento() != null
                ? dto.getRestablecimiento().getRestablecimientoId()
                : null);

    if (restId == null) {
        throw new IllegalArgumentException("Debe especificar el ID del restablecimiento.");
    }

    // Buscar el restablecimiento
    Restablecimiento r = restablecimientoRepository.findById(restId)
            .orElseThrow(() -> new IllegalArgumentException("No se encontró el restablecimiento con ID: " + restId));

    // Crear la gestión pendiente
    Gestion g = new Gestion();
    g.setRestablecimiento(r);
    g.setAprobado(null); // pendiente
    g.setObservacion(dto.getObservacion());
    g.setFechaAprobacion(null);

    Gestion guardada = gestionRepository.save(g);
    return toResponse(guardada);
}

    @Override
    public GestionResponseDTO actualizar(Long id, GestionCreateDTO dto) {
        Gestion g = gestionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró la gestión con ID: " + id));

        if (dto.getRestablecimientoId() != null &&
                (g.getRestablecimiento() == null ||
                 !g.getRestablecimiento().getRestablecimientoId().equals(dto.getRestablecimientoId()))) {

            Restablecimiento r = restablecimientoRepository.findById(dto.getRestablecimientoId())
                    .orElseThrow(() -> new IllegalArgumentException(
                            "No se encontró el restablecimiento con ID: " + dto.getRestablecimientoId()));
            g.setRestablecimiento(r);
        }

        if (dto.getObservacion() != null) g.setObservacion(dto.getObservacion());
        // mantener aprobado/fecha tal cual (pendiente)

        return toResponse(gestionRepository.save(g));
    }
    // ======== FIN NUEVOS ========

    @Override
    public GestionResponseDTO aprobar(Long id, String observacion) {
        Gestion g = gestionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró la gestión con ID: " + id));

        Restablecimiento r = g.getRestablecimiento();
        if (r == null) throw new IllegalArgumentException("Esta gestión no tiene un restablecimiento asociado.");

        if (Boolean.TRUE.equals(g.getAprobado()))
            throw new IllegalArgumentException("Esta gestión ya fue aprobada.");

        g.setAprobado(true);
        g.setObservacion(observacion != null ? observacion : "Aprobado correctamente");
        g.setFechaAprobacion(LocalDateTime.now());

        r.setEstado("APROBADO");
        restablecimientoRepository.save(r);

        return toResponse(gestionRepository.save(g));
    }

    @Override
    public GestionResponseDTO rechazar(Long id, String observacion) {
        Gestion g = gestionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró la gestión con ID: " + id));

        Restablecimiento r = g.getRestablecimiento();
        if (r == null) throw new IllegalArgumentException("Esta gestión no tiene un restablecimiento asociado.");

        if (Boolean.FALSE.equals(g.getAprobado()))
            throw new IllegalArgumentException("Esta gestión ya fue rechazada.");

        g.setAprobado(false);
        g.setObservacion(observacion != null ? observacion : "Rechazado por el administrador");
        g.setFechaAprobacion(LocalDateTime.now());

        r.setEstado("RECHAZADO");
        restablecimientoRepository.save(r);

        return toResponse(gestionRepository.save(g));
    }

    @Override
    public void eliminar(Long id) {
        if (!gestionRepository.existsById(id)) {
            throw new IllegalArgumentException("No existe una gestión con el ID proporcionado.");
        }
        gestionRepository.deleteById(id);
    }

    private GestionResponseDTO toResponse(Gestion g) {
        GestionResponseDTO dto = new GestionResponseDTO();
        dto.setGestionId(g.getId());
        dto.setRestablecimientoId(
                g.getRestablecimiento() != null ? g.getRestablecimiento().getRestablecimientoId() : null);
        dto.setAprobado(g.getAprobado());
        dto.setFechaAprobacion(g.getFechaAprobacion());
        dto.setObservacion(g.getObservacion());
        return dto;
    }
}
