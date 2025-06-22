package com.kawsay.ia.service;

import com.kawsay.ia.entity.Grupo;
import com.kawsay.ia.entity.Usuario;
import com.kawsay.ia.entity.UsuarioGrupo;
import com.kawsay.ia.repository.UsuarioGrupoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioGrupoService {

    @Autowired
    private UsuarioGrupoRepository usuarioGrupoRepo;

    public void unirseAGrupo(Usuario usuario, Grupo grupo) {
        UsuarioGrupo ug = new UsuarioGrupo();
        ug.setUsuario(usuario);
        ug.setGrupo(grupo);
        usuarioGrupoRepo.save(ug);
    }

    public List<Grupo> obtenerGruposDelUsuario(Integer idUsuario) {
        return usuarioGrupoRepo.findByUsuarioId(idUsuario)
                .stream()
                .map(UsuarioGrupo::getGrupo)
                .toList();
    }
}
