package com.matrixmm.chakrabackend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name="incidencia")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Incidencia {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="id_incidencia")
    private Long idIncidencia;

    @Column(name="fecha_registro", nullable=false)
    private LocalDate fechaRegistro;

    @Column(name="nombre_animal")
    private String nombreAnimal;

    @Column(name="cantidad_animales")
    private Long cantidadAnimales;

    @Column(name="gravedad_incidencia")
    private String gravedadIncidencia;

    @Column(name="observacion", length=200)
    private String observacion;

    @Column(name="resultado", length=200)
    private String resultado;

    @ManyToOne
    @JoinColumn(name="id_familia")
    private Familia familia;
}
