package com.matrixmm.chakrabackend.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.matrixmm.chakrabackend.utils.Format;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name="evento_sanitario")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CuidadoSanitario {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="id_cuidado_sanitario")
    private Long idCuidadoSanitario;

    @Column(name="nombre", length=100)
    private String nombre;

    @Column(name="fecha")
    private LocalDate fecha;

    @Column(name="estado")
    private Boolean estado;

    @ManyToOne
    @JoinColumn(name="id_familia")
    private Familia familia;

    @ManyToOne
    @JoinColumn(name="id_incidencia")
    private Incidencia incidencia;
}
