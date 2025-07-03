package com.kawsay.ia.service;

import com.kawsay.ia.entity.Preferencia;
import com.kawsay.ia.repository.PreferenciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PreferenciaService {

    @Autowired
    private PreferenciaRepository preferenciaRepository;


    public List<Preferencia> findAll() {
        return preferenciaRepository.findAll();
    }

    public Preferencia findOrCreate(String valor) {
        return preferenciaRepository.findByValor(valor)
                .orElseThrow(() -> new RuntimeException("Preferencia no registrada: " + valor));
    }

}
