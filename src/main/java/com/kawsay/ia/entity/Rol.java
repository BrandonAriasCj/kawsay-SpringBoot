package com.kawsay.ia.entity;
import jakarta.persistence.*;
import java.util.List.*;

import jakarta.persistence.*;

@Entity
@Table(name = "rol")
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rol")
    private Integer id;

    @Column(nullable = false, length = 50)
    private String denominacion;

    @Column(nullable = false)
    private boolean estado = true;

    // Getters y setters
}
