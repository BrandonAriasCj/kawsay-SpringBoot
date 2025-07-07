package com.kawsay.ia.mapper;
import com.kawsay.ia.dto.ReporteDTO;
import com.kawsay.ia.entity.Reporte;
import com.kawsay.ia.entity.Usuario;

public class ReporteMapper {

    public static ReporteDTO toDTO(Reporte reporte) {
        if (reporte == null) return null;

        Usuario usuario = reporte.getUsuario();
        String nombre = "Usuario no encontrado";
        if (usuario != null && usuario.getPerfil() != null) {
            nombre = usuario.getPerfil().getNombreCompleto();
        }

        return ReporteDTO.builder()
                .id(reporte.getId())
                .contenido(reporte.getContenido())
                .timestamp(reporte.getTimestamp())
                .usuario_id(String.valueOf(usuario != null ? usuario.getId() : "N/A"))
                .nombreAlumno(nombre)
                .build();
    }
}
