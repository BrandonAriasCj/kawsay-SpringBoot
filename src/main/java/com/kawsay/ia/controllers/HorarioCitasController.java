package com.kawsay.ia.controllers;

import com.kawsay.ia.dto.*;
import com.kawsay.ia.service.GestionHorarioService;
import com.kawsay.ia.service.UsuarioService;
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
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:5174"})
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

    @GetMapping("/psicologos")
    public ResponseEntity<List<PsicologoDTO>> obtenerPsicologos() {
        List<PsicologoDTO> psicologos = usuarioService.findAllPsicologos().stream()
                .map(p -> {
                    PsicologoDTO dto = new PsicologoDTO();
                    dto.setId(p.getId());
                    dto.setCorreo(p.getCorreoInstitucional());
                    String nombreCompleto = p.getPerfil() != null ? p.getPerfil().getNombreCompleto() : "Psicólogo";
                    dto.setNombreCompleto(nombreCompleto);

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

    // --- Endpoint Común ---

    @GetMapping("/citas/mis-citas")
    @PreAuthorize("hasAuthority('ESTUDIANTE') or hasAuthority('PSICOLOGO')")
    public ResponseEntity<List<CitaAgendadaDTO>> getMisCitas() {
        return ResponseEntity.ok(gestionHorarioService.obtenerMisCitas());
    }
}