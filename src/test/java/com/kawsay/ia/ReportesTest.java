package com.kawsay.ia;

import com.kawsay.ia.entity.Reporte;
import com.kawsay.ia.repository.ReporteRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Disabled("Metodo manual de prueba")
public class ReportesTest {
    @Autowired
    private ReporteRepository reporteRepository;
    @Test
    public void findAllReporteService(){
        List<Reporte> reportes = reporteRepository.findAll();
        System.out.println(reportes.size());
    }
}
