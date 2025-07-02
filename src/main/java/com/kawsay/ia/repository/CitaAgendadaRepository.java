package com.kawsay.ia.repository;

import com.kawsay.ia.entity.CitaAgendada;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface CitaAgendadaRepository extends JpaRepository<CitaAgendada, Integer> {
    List<CitaAgendada> findByPsicologoIdAndFechaCita(Integer psicologoId, LocalDate fecha);
    boolean existsByPsicologoIdAndFechaCitaAndHoraInicio(Integer psicologoId, LocalDate fecha, LocalTime hora);
    List<CitaAgendada> findByEstudianteId(Integer estudianteId);
    List<CitaAgendada> findByPsicologoId(Integer psicologoId);
}