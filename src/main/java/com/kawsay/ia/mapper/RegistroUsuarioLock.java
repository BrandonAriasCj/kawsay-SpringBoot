package com.kawsay.ia.mapper;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RegistroUsuarioLock {

    private final Map<String, Object> locks = new ConcurrentHashMap<>();

    public synchronized Object obtenerLock(String correo) {
        return locks.computeIfAbsent(correo, k -> new Object());
    }

    public void liberarLock(String correo) {
        locks.remove(correo);
    }
}
