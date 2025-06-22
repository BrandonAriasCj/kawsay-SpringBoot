package com.kawsay.ia.groupTest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GrupoApiTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void crearGrupo() {
        Map<String, Object> body = new HashMap<>();
        body.put("nombre", "Grupo Backend Test");
        body.put("descripcion", "Pruebas de API");
        body.put("categoria", "TECNOLOGÍA");
        body.put("creadorId", 1);
        body.put("moderadorId", 1);

        ResponseEntity<String> response = restTemplate
                .postForEntity("/api/grupos", body, String.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        System.out.println("✅ Grupo creado correctamente");
    }

    @Test
    void obtenerGrupos() {
        ResponseEntity<String> response = restTemplate.getForEntity("/api/grupos", String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        System.out.println("📄 Grupos: " + response.getBody());
    }
}
