package com.matrixmm.chakrabackend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name="pastoreo")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pastoreo {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="id_pastoreo")
    private Long idPastoreo;

    @Column(name="tiempo", nullable = false)
    private Integer tiempo;

    @Column(name="horario")
    private LocalTime horario;

    @Column(name="dia")
    private String dia;

    @Column(name="estado")
    private Boolean estado;

    @ManyToOne
    @JoinColumn(name="id_familia")
    private Familia familia;
}
