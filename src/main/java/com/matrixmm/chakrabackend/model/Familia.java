package com.matrixmm.chakrabackend.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

@Entity
@Table(name="familia")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Familia {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long idFamilia;

    @Column(name="nombre")
    private String nombre;

    @Column(name="periodo")
    private String periodo;

    @Column(name="fecha_registro")
    private LocalDate fechaRegistro;
}
