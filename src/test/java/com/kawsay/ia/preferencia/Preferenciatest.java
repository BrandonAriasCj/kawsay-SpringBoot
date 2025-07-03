package com.kawsay.ia.preferencia;

import com.kawsay.ia.entity.Preferencia;
import com.kawsay.ia.repository.PreferenciaRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class Preferenciatest {

    @Autowired
    private PreferenciaRepository preferenciaRepository;

    @Test
    @Commit
    @Order(1)
    void poblarCatalogoPreferencias() {
        if (preferenciaRepository.count() > 0) {
            System.out.println("🟡 Preferencias ya existen, se omite inserción.");
            return;
        }

        List<Preferencia> catalogo = List.of(
                new Preferencia(null, "hobby", "lectura"),
                new Preferencia(null, "hobby", "música"),
                new Preferencia(null, "hobby", "videojuegos"),
                new Preferencia(null, "genero", "comedia"),
                new Preferencia(null, "genero", "ciencia ficción"),
                new Preferencia(null, "narrativa", "directa"),
                new Preferencia(null, "narrativa", "introspectiva"),
                new Preferencia(null, "estado_animo", "motivado"),
                new Preferencia(null, "estado_animo", "reflexivo")
        );

        preferenciaRepository.saveAll(catalogo);
        System.out.println("✅ Catálogo cargado con éxito.");
    }

    @Test
    @Order(2)
    void verificarPreferenciasGuardadas() {
        List<Preferencia> todas = preferenciaRepository.findAll();
        assertFalse(todas.isEmpty(), "La tabla de preferencias no debería estar vacía");
        todas.forEach(p -> System.out.printf("- %s → %s%n", p.getTipo(), p.getValor()));
    }
}
