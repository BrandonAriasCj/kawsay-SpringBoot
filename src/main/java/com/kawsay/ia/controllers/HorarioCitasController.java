package com.kawsay.ia.controllers;

import com.kawsay.ia.dto.*;
import com.kawsay.ia.repository.PerfilPsicologoRepository;
import com.kawsay.ia.service.GestionHorarioService;
import com.kawsay.ia.service.UsuarioService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/horarios-citas")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:5174", "http://localhost:3000", "http://localhost:3001"})
public class HorarioCitasController {

    @Autowired private GestionHorarioService gestionHorarioService;
    @Autowired private UsuarioService usuarioService;

    // --- Endpoints para Psicólogos ---

    @PostMapping("/reglas")
    @PreAuthorize("hasAuthority('PSICOLOGO')")
    public ResponseEntity<ReglaDisponibilidadDTO> crearRegla(@RequestBody ReglaDisponibilidadDTO dto) {
        gestionHorarioService.crearRegla(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/reglas")
    @PreAuthorize("hasAuthority('PSICOLOGO')")
    public ResponseEntity<List<ReglaDisponibilidadDTO>> obtenerReglasPsicologo() {
        return ResponseEntity.ok(gestionHorarioService.obtenerReglasDelPsicologoAutenticado());
    }

    // --- Endpoints para Estudiantes ---


    @Autowired
    private PerfilPsicologoRepository perfilPsicologoRepository;

    @GetMapping("/psicologos")
    public ResponseEntity<List<PsicologoDTO>> obtenerPsicologos() {
        List<PsicologoDTO> psicologos = usuarioService.findAllPsicologos().stream()
                .map(p -> {
                    PsicologoDTO dto = new PsicologoDTO();
                    dto.setId(p.getId());
                    dto.setCorreo(p.getCorreoInstitucional());
                    perfilPsicologoRepository.findByUsuarioId(p.getId()).ifPresent(perfil -> {
                        dto.setNombreCompleto(perfil.getNombreProfesional());
                    });
                    return dto;
                }).collect(Collectors.toList());
        return ResponseEntity.ok(psicologos);
    }

    @GetMapping("/disponibles/{psicologoId}")
    public ResponseEntity<List<HorarioDisponibleDTO>> getHorariosDisponibles(
            @PathVariable Integer psicologoId,
            @RequestParam("fecha") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        return ResponseEntity.ok(gestionHorarioService.obtenerHorariosDisponibles(psicologoId, fecha));
    }

    @PostMapping("/citas/agendar")
    public ResponseEntity<CitaAgendadaDTO> agendarCita(@RequestBody AgendarCitaRequestDTO request) {
        gestionHorarioService.agendarCita(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @DeleteMapping("/citas/{citaId}")
    //@PreAuthorize("hasAuthority('ESTUDIANTE')")
    public ResponseEntity<Void> cancelarCita(@PathVariable Integer citaId) {
        try {
            gestionHorarioService.cancelarCita(citaId);
            return ResponseEntity.noContent().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping("/citas/mis-citas")
    //@PreAuthorize("hasAuthority('ESTUDIANTE') or hasAuthority('PSICOLOGO')")
    public ResponseEntity<List<CitaAgendadaDTO>> getMisCitas() {
        return ResponseEntity.ok(gestionHorarioService.obtenerMisCitas());
    }


    @DeleteMapping("/reglas/{reglaId}")
    @PreAuthorize("hasAuthority('PSICOLOGO')")
    public ResponseEntity<Void> eliminarRegla(@PathVariable Integer reglaId) {
        try {
            gestionHorarioService.eliminarRegla(reglaId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/reglas/{reglaId}")
    @PreAuthorize("hasAuthority('PSICOLOGO')")
    public ResponseEntity<Void> actualizarRegla(
            @PathVariable Integer reglaId,
            @RequestBody ReglaDisponibilidadDTO dto) {
        try {
            gestionHorarioService.actualizarRegla(reglaId, dto);
            return ResponseEntity.ok().build();
        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}