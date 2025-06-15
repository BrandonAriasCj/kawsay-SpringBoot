package com.kawsay.ia.service;
import com.kawsay.ia.entity.Rol;
import com.kawsay.ia.repositoty.RolRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RolService {
    @Autowired
    private RolRepository rolRepository;

    public List<Rol> findAll(){
        return rolRepository.findAll();
    }
    public void crear(Rol rol) {
        rolRepository.save(rol);
    }
}
