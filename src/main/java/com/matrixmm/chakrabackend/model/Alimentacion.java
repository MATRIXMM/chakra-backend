package com.matrixmm.chakrabackend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name="alimentacion")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Alimentacion {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="id_alimentacion")
    private Long idAlimentacion;

    @Column(name="tipo", length=20, nullable=false)
    private String tipo;

    @Column(name="cantidad", nullable=true)
    private Integer cantidad;

    @Column(name="dias_seguimiento")
    private Integer diasSeguimiento;

    @Column(name="estado")
    private Boolean estado;

    @ManyToOne
    @JoinColumn(name="id_familia")
    private Familia familia;

    @ManyToOne
    @JoinColumn(name="id_incidencia")
    private Incidencia incidencia;
}
