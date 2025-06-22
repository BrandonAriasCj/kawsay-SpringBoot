package com.kawsay.ia.service;
import com.kawsay.ia.entity.Rol;
import com.kawsay.ia.repository.RolRepository;

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

    public Rol buscarPorId(Integer id) {
        return rolRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado con id: " + id));
    }
    public Rol actualizarRol(Integer id, Rol nuevo) {
        Rol existente = buscarPorId(id);
        existente.setDenominacion(nuevo.getDenominacion());
        existente.setEstado(nuevo.isEstado());
        return rolRepository.save(existente);
    }
    public void deshabilitarRol(Integer id) {
        Rol rol = buscarPorId(id);
        rol.setEstado(false);
        rolRepository.save(rol);
    }
    public List<Rol> listarActivos() {
        return rolRepository.findAll()
                .stream()
                .filter(Rol::isEstado)
                .toList();
    }





}
