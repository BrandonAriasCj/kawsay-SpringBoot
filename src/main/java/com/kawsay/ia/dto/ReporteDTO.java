// com.kawsay.ia.dto.ReporteDTO
package com.kawsay.ia.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReporteDTO {
    private Long id;
    private String contenido;
    private LocalDateTime timestamp;
    private String usuario_id;
}

