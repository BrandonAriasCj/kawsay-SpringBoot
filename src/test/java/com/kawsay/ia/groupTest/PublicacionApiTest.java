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
public class PublicacionApiTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void crearPublicacion() {
        Map<String, Object> body = new HashMap<>();
        body.put("contenido", "Esta es una publicación desde el test");
        body.put("autorId", 1);

        ResponseEntity<String> response = restTemplate.postForEntity(
                "/api/grupos/1/publicaciones", body, String.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        System.out.println("✅ Publicación creada correctamente");
    }

    @Test
    void listarPublicacionesGrupo() {
        ResponseEntity<String> response = restTemplate.getForEntity(
                "/api/grupos/1/publicaciones", String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        System.out.println("📚 Publicaciones del grupo 1:\n" + response.getBody());
    }
}
