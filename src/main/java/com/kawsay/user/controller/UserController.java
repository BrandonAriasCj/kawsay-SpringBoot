package com.kawsay.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Map;


@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api")
public class UserController {


    @PostMapping("/usuario/token")
    public ResponseEntity<?> test() {
        System.out.println("✅ Endpoint activo y ejecutándose.");
        return ResponseEntity.ok("Test funcionando");
    }



    @PostMapping("/usuario/json")
    public ResponseEntity<?> recibirJsonRaw(@RequestBody String rawJson) {
        System.out.println("JSON recibido:");
        System.out.println(rawJson);

        return ResponseEntity.ok("JSON capturado");
    }
}
