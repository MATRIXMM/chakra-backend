package com.matrixmm.chakrabackend.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.matrixmm.chakrabackend.utils.Format;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name="horario")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Horario {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="id_horario")
    private Long idHorario;

    @Column(name="horario", nullable=false)
    private LocalDateTime horario;

    @Column(name="estado")
    private Boolean estado;

    @ManyToOne
    @JoinColumn(name="id_alimentacion")
    private Alimentacion alimentacion;
}
