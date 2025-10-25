package com.example.ProyectoInventario.service;

import com.example.ProyectoInventario.entity.Gestion;
import com.example.ProyectoInventario.entity.Restablecimiento;
import com.example.ProyectoInventario.repository.GestionRepository;
import com.example.ProyectoInventario.repository.RestablecimientoRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class GestionServiceImpl implements GestionService {

    private final GestionRepository gestionRepository;
    private final RestablecimientoRepository restablecimientoRepository;

    public GestionServiceImpl(GestionRepository gestionRepository, RestablecimientoRepository restablecimientoRepository) {
        this.gestionRepository = gestionRepository;
        this.restablecimientoRepository = restablecimientoRepository;
    }

    @Override
    public List<Gestion> listar() {
        return gestionRepository.findAll();
    }

    @Override
    public Gestion obtenerPorId(Long id) {
        return gestionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró la gestión con ID: " + id));
    }

    @Override
    public Gestion crear(Gestion g) {
        // Validar existencia del restablecimiento asociado
        if (g.getRestablecimiento() == null || g.getRestablecimiento().getRestablecimientoId() == null) {
            throw new IllegalArgumentException("Debe asociar un restablecimiento válido.");
        }

        Restablecimiento restablecimiento = restablecimientoRepository.findById(g.getRestablecimiento().getRestablecimientoId())
                .orElseThrow(() -> new IllegalArgumentException("El restablecimiento especificado no existe."));

        // Validar que no haya sido gestionado previamente
        if (!"PENDIENTE".equalsIgnoreCase(restablecimiento.getEstado())) {
            throw new IllegalArgumentException("El restablecimiento ya fue gestionado (" + restablecimiento.getEstado() + ").");
        }

        g.setRestablecimiento(restablecimiento);
        g.setAprobado(false);
        g.setFechaAprobacion(LocalDateTime.now());
        g.setObservacion("Pendiente de revisión");

        return gestionRepository.save(g);
    }

    @Override
    public Gestion aprobar(Long id, String observacion) {
        Gestion gestion = gestionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró la gestión con ID: " + id));

        Restablecimiento restablecimiento = gestion.getRestablecimiento();
        if (restablecimiento == null) {
            throw new IllegalArgumentException("Esta gestión no tiene un restablecimiento asociado.");
        }

        // Cambiar estado
        gestion.setAprobado(true);
        gestion.setFechaAprobacion(LocalDateTime.now());
        gestion.setObservacion(observacion != null ? observacion : "Aprobado correctamente");

        restablecimiento.setEstado("APROBADO");
        restablecimientoRepository.save(restablecimiento);

        return gestionRepository.save(gestion);
    }

    @Override
    public Gestion rechazar(Long id, String observacion) {
        Gestion gestion = gestionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró la gestión con ID: " + id));

        Restablecimiento restablecimiento = gestion.getRestablecimiento();
        if (restablecimiento == null) {
            throw new IllegalArgumentException("Esta gestión no tiene un restablecimiento asociado.");
        }

        // Cambiar estado
        gestion.setAprobado(false);
        gestion.setFechaAprobacion(LocalDateTime.now());
        gestion.setObservacion(observacion != null ? observacion : "Rechazado por revisión");

        restablecimiento.setEstado("RECHAZADO");
        restablecimientoRepository.save(restablecimiento);

        return gestionRepository.save(gestion);
    }

    @Override
    public void eliminar(Long id) {
        if (!gestionRepository.existsById(id)) {
            throw new IllegalArgumentException("No existe una gestión con ID: " + id);
        }
        gestionRepository.deleteById(id);
    }
}
