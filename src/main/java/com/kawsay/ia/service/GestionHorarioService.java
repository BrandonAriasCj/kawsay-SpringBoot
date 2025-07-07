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

        if (reglasActivas.isEmpty()) {
            return Collections.emptyList();
        }

        Set<LocalTime> horasOcupadas = citaRepository.findByPsicologoIdAndFechaCita(psicologoId, fecha)
                .stream().map(CitaAgendada::getHoraInicio).collect(Collectors.toSet());


        Set<HorarioDisponibleDTO> horariosUnicos = new TreeSet<>(Comparator.comparing(HorarioDisponibleDTO::getHora));

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

                            // 2. Añadimos al Set. Si ya existe un DTO igual, no hará nada.
                            horariosUnicos.add(dto);
                        }
                        horaActual = horaActual.plusMinutes(regla.getDuracionCita());
                    }
                }
            }
        }

        return new ArrayList<>(horariosUnicos);
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

    // En src/main/java/com/kawsay/ia/service/GestionHorarioService.java

    public List<CitaAgendadaDTO> obtenerMisCitas() {
        Usuario usuario = authUtils.getUsuarioAutenticado();

        System.out.println("Buscando citas para el usuario ID: " + usuario.getId() + " con rol: " + usuario.getRol().getDenominacion());

        List<CitaAgendada> citas;
        if (usuario.getRol().getDenominacion() == RolTipo.PSICOLOGO) {
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


    @Autowired
    private PerfilPsicologoRepository perfilPsicologoRepository;

    private CitaAgendadaDTO convertirCitaADTO(CitaAgendada cita) {
        CitaAgendadaDTO dto = new CitaAgendadaDTO();
        dto.setId(cita.getId());

        dto.setEstudianteNombre(cita.getEstudiante().getPerfil() != null ? cita.getEstudiante().getPerfil().getNombreCompleto() : "Estudiante sin perfil");

        perfilPsicologoRepository.findByUsuarioId(cita.getPsicologo().getId())
                .ifPresentOrElse(
                        perfilPro -> dto.setPsicologoNombre(perfilPro.getNombreProfesional()),
                        () -> dto.setPsicologoNombre("N/A")
                );
        dto.setFechaCita(cita.getFechaCita().toString());
        dto.setHoraInicio(cita.getHoraInicio().format(DateTimeFormatter.ofPattern("HH:mm")));
        dto.setModalidad(cita.getModalidad().name());
        return dto;
    }

    @Transactional
    public void cancelarCita(Integer citaId) {
        Usuario usuarioLogueado = authUtils.getUsuarioAutenticado();


        CitaAgendada cita = citaRepository.findById(citaId)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró una cita con el ID: " + citaId));

        if (!cita.getEstudiante().getId().equals(usuarioLogueado.getId())) {
            throw new IllegalStateException("No tienes permiso para cancelar esta cita.");
        }

        citaRepository.delete(cita);
    }


    @Transactional
    public void eliminarRegla(Integer reglaId) {
        Usuario usuario = authUtils.getUsuarioAutenticado();
        ReglaDisponibilidad regla = reglaRepository.findById(reglaId)
                .orElseThrow(() -> new EntityNotFoundException("Regla no encontrada"));

        if (!regla.getPsicologo().getId().equals(usuario.getId())) {
            throw new IllegalStateException("Acceso denegado para eliminar esta regla.");
        }

        reglaRepository.deleteById(reglaId);
    }

    @Transactional
    public ReglaDisponibilidad actualizarRegla(Integer reglaId, ReglaDisponibilidadDTO dto) {
        Usuario psicologo = authUtils.getUsuarioAutenticado();


        ReglaDisponibilidad reglaExistente = reglaRepository.findById(reglaId)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró la regla con ID: " + reglaId));


        if (!reglaExistente.getPsicologo().getId().equals(psicologo.getId())) {
            throw new IllegalStateException("No tienes permiso para editar esta regla.");
        }

        reglaExistente.setFechaInicio(LocalDate.parse(dto.getStartDate()));
        reglaExistente.setFechaFin(LocalDate.parse(dto.getEndDate()));
        reglaExistente.setDuracionCita(dto.getAppointmentDuration());


        reglaExistente.getFranjasHorarias().clear();

        List<FranjaHoraria> nuevasFranjas = dto.getTimeSlots().stream().map(slotDto -> {
            FranjaHoraria franja = new FranjaHoraria();
            franja.setRegla(reglaExistente);
            franja.setDiaSemana(slotDto.getDayOfWeek());
            franja.setHoraInicio(LocalTime.parse(slotDto.getStartTime()));
            franja.setHoraFin(LocalTime.parse(slotDto.getEndTime()));
            franja.setModalidad(ModalidadCita.valueOf(slotDto.getModality().toUpperCase()));
            return franja;
        }).collect(Collectors.toList());

        reglaExistente.getFranjasHorarias().addAll(nuevasFranjas);

        return reglaRepository.save(reglaExistente);
    }
}