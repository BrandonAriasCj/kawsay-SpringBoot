package com.kawsay.ia.service;

import com.kawsay.ia.config.AuthUtils;
import com.kawsay.ia.entity.Grupo;
import com.kawsay.ia.entity.Usuario;
import com.kawsay.ia.entity.UsuarioGrupo;
import com.kawsay.ia.repository.UsuarioGrupoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioGrupoService {

    @Autowired
    private UsuarioGrupoRepository usuarioGrupoRepo;
    private final AuthUtils authUtils;

    public void unirseAGrupo(Usuario usuario, Grupo grupo) {
        UsuarioGrupo ug = new UsuarioGrupo();
        ug.setUsuario(usuario);
        ug.setGrupo(grupo);
        usuarioGrupoRepo.save(ug);
    }

    public List<Grupo> obtenerGruposDelUsuarioActual() {
        Usuario usuario = authUtils.getUsuarioAutenticado();
        return usuarioGrupoRepo.findByUsuarioId(usuario.getId())
                .stream()
                .map(UsuarioGrupo::getGrupo)
                .toList();
    }

}
