package com.kawsay.ia.service;

import com.kawsay.ia.config.AuthUtils;
import com.kawsay.ia.dto.*;
import com.kawsay.ia.entity.*;
import com.kawsay.ia.repository.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class GestionHorarioService {

    @Autowired private AuthUtils authUtils;
    @Autowired private ReglaDisponibilidadRepository reglaRepository;
    @Autowired private CitaAgendadaRepository citaRepository;
    @Autowired private UsuarioRepository usuarioRepository;

    @Transactional
    public ReglaDisponibilidad crearRegla(ReglaDisponibilidadDTO dto) {
        Usuario psicologo = authUtils.getUsuarioAutenticado();
        if (psicologo.getRol().getDenominacion() != RolTipo.PSICOLOGO) {
            throw new IllegalStateException("Solo los psicólogos pueden crear reglas de disponibilidad.");
        }

        ReglaDisponibilidad regla = new ReglaDisponibilidad();
        regla.setPsicologo(psicologo);
        regla.setFechaInicio(LocalDate.parse(dto.getStartDate()));
        regla.setFechaFin(LocalDate.parse(dto.getEndDate()));
        regla.setDuracionCita(dto.getAppointmentDuration());

        List<FranjaHoraria> franjas = dto.getTimeSlots().stream().map(slotDto -> {
            FranjaHoraria franja = new FranjaHoraria();
            franja.setRegla(regla);
            franja.setDiaSemana(slotDto.getDayOfWeek());
            franja.setHoraInicio(LocalTime.parse(slotDto.getStartTime()));
            franja.setHoraFin(LocalTime.parse(slotDto.getEndTime()));
            franja.setModalidad(ModalidadCita.valueOf(slotDto.getModality().toUpperCase()));
            return franja;
        }).collect(Collectors.toList());

        regla.setFranjasHorarias(franjas);
        return reglaRepository.save(regla);
    }

    @Transactional(readOnly = true)
    public List<ReglaDisponibilidadDTO> obtenerReglasDelPsicologoAutenticado() {
        Usuario psicologo = authUtils.getUsuarioAutenticado();
        return reglaRepository.findByPsicologoId(psicologo.getId()).stream()
                .map(this::convertirReglaADTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<HorarioDisponibleDTO> obtenerHorariosDisponibles(Integer psicologoId, LocalDate fecha) {
        List<ReglaDisponibilidad> reglasActivas = reglaRepository.findByPsicologoId(psicologoId).stream()
                .filter(r -> !fecha.isBefore(r.getFechaInicio()) && !fecha.isAfter(r.getFechaFin()))
                .toList();

        if (reglasActivas.isEmpty()) return Collections.emptyList();

        Set<LocalTime> horasOcupadas = citaRepository.findByPsicologoIdAndFechaCita(psicologoId, fecha)
                .stream().map(CitaAgendada::getHoraInicio).collect(Collectors.toSet());

        List<HorarioDisponibleDTO> horariosDisponibles = new ArrayList<>();
        int diaSemanaNumero = fecha.getDayOfWeek().getValue();

        for (ReglaDisponibilidad regla : reglasActivas) {
            for (FranjaHoraria franja : regla.getFranjasHorarias()) {
                if (franja.getDiaSemana() == diaSemanaNumero) {
                    LocalTime horaActual = franja.getHoraInicio();
                    while (horaActual.isBefore(franja.getHoraFin())) {
                        if (!horasOcupadas.contains(horaActual)) {
                            HorarioDisponibleDTO dto = new HorarioDisponibleDTO();
                            dto.setHora(horaActual.format(DateTimeFormatter.ofPattern("HH:mm")));
                            dto.setModalidad(franja.getModalidad().toString());
                            horariosDisponibles.add(dto);
                        }
                        horaActual = horaActual.plusMinutes(regla.getDuracionCita());
                    }
                }
            }
        }
        horariosDisponibles.sort(Comparator.comparing(HorarioDisponibleDTO::getHora));
        return horariosDisponibles;
    }

    @Transactional
    public CitaAgendada agendarCita(AgendarCitaRequestDTO request) {
        Usuario estudiante = authUtils.getUsuarioAutenticado();
        if (estudiante.getRol().getDenominacion() != RolTipo.ESTUDIANTE) {
            throw new IllegalStateException("Solo los estudiantes pueden agendar citas.");
        }

        Usuario psicologo = usuarioRepository.findById(request.getPsicologoId())
                .orElseThrow(() -> new EntityNotFoundException("Psicólogo no encontrado"));
        LocalDate fecha = LocalDate.parse(request.getFecha());
        LocalTime hora = LocalTime.parse(request.getHora());


        boolean ocupado = citaRepository.existsByPsicologoIdAndFechaCitaAndHoraInicio(psicologo.getId(), fecha, hora);
        if(ocupado) throw new IllegalStateException("El horario seleccionado ya no está disponible.");


        ModalidadCita modalidad = obtenerModalidadParaHorario(psicologo.getId(), fecha, hora);

        CitaAgendada nuevaCita = new CitaAgendada();
        nuevaCita.setEstudiante(estudiante);
        nuevaCita.setPsicologo(psicologo);
        nuevaCita.setFechaCita(fecha);
        nuevaCita.setHoraInicio(hora);
        nuevaCita.setModalidad(modalidad);

        return citaRepository.save(nuevaCita);
    }

    public List<CitaAgendadaDTO> obtenerMisCitas() {
        Usuario usuario = authUtils.getUsuarioAutenticado();
        List<CitaAgendada> citas;
        if(usuario.getRol().getDenominacion() == RolTipo.PSICOLOGO){
            citas = citaRepository.findByPsicologoId(usuario.getId());
        } else {
            citas = citaRepository.findByEstudianteId(usuario.getId());
        }
        return citas.stream().map(this::convertirCitaADTO).collect(Collectors.toList());
    }




    private ModalidadCita obtenerModalidadParaHorario(Integer psicologoId, LocalDate fecha, LocalTime hora) {

        return ModalidadCita.VIRTUAL;
    }

    private ReglaDisponibilidadDTO convertirReglaADTO(ReglaDisponibilidad regla) {
        ReglaDisponibilidadDTO dto = new ReglaDisponibilidadDTO();
        dto.setId(regla.getId());
        dto.setStartDate(regla.getFechaInicio().toString());
        dto.setEndDate(regla.getFechaFin().toString());
        dto.setAppointmentDuration(regla.getDuracionCita());
        dto.setTimeSlots(regla.getFranjasHorarias().stream().map(this::convertirFranjaADTO).collect(Collectors.toList()));
        return dto;
    }

    private FranjaHorariaDTO convertirFranjaADTO(FranjaHoraria franja) {
        FranjaHorariaDTO dto = new FranjaHorariaDTO();
        dto.setDayOfWeek(franja.getDiaSemana());
        dto.setStartTime(franja.getHoraInicio().toString());
        dto.setEndTime(franja.getHoraFin().toString());
        dto.setModality(franja.getModalidad().name());
        return dto;
    }

    private CitaAgendadaDTO convertirCitaADTO(CitaAgendada cita) {
        CitaAgendadaDTO dto = new CitaAgendadaDTO();
        dto.setId(cita.getId());
        dto.setEstudianteNombre(cita.getEstudiante().getPerfil() != null ? cita.getEstudiante().getPerfil().getNombreCompleto() : "N/A");
        dto.setPsicologoNombre(cita.getPsicologo().getPerfil() != null ? cita.getPsicologo().getPerfil().getNombreCompleto() : "N/A");
        dto.setFechaCita(cita.getFechaCita().toString());
        dto.setHoraInicio(cita.getHoraInicio().format(DateTimeFormatter.ofPattern("HH:mm")));
        dto.setModalidad(cita.getModalidad().name());
        return dto;
    }
}