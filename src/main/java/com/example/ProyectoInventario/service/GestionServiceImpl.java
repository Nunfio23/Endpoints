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
        return gestionRepository.findById(id).orElse(null);
    }

    @Override
    public Gestion crear(Gestion gestion) {
        return gestionRepository.save(gestion);
    }

    @Override
    public Gestion aprobar(Long id, String observacion) {
        Gestion gestion = gestionRepository.findById(id).orElse(null);
        if (gestion == null) return null;

        gestion.setAprobado(true);
        gestion.setFechaAprobacion(LocalDateTime.now());
        gestion.setObservacion(observacion);

        // actualizar estado del restablecimiento
        Restablecimiento r = gestion.getRestablecimiento();
        r.setEstado("APROBADO");
        restablecimientoRepository.save(r);

        return gestionRepository.save(gestion);
    }

    @Override
    public Gestion rechazar(Long id, String observacion) {
        Gestion gestion = gestionRepository.findById(id).orElse(null);
        if (gestion == null) return null;

        gestion.setAprobado(false);
        gestion.setFechaAprobacion(LocalDateTime.now());
        gestion.setObservacion(observacion);

        Restablecimiento r = gestion.getRestablecimiento();
        r.setEstado("RECHAZADO");
        restablecimientoRepository.save(r);

        return gestionRepository.save(gestion);
    }

    @Override
    public void eliminar(Long id) {
        gestionRepository.deleteById(id);
    }
}
