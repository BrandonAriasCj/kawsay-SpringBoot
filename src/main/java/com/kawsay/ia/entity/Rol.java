package com.kawsay.ia.entity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.kawsay.ia.dto.RolTipo;
import jakarta.persistence.*;
import java.util.List.*;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Entity
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "rol")
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rol")
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false,unique = true , length = 50)
    private RolTipo denominacion;


    @Column(nullable = false)
    private boolean estado = true;

    @Builder
    public Rol(RolTipo denominacion, boolean estado) {
        this.denominacion = denominacion;
        this.estado = estado;
    }

}
