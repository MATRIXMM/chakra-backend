package com.matrixmm.chakrabackend.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.matrixmm.chakrabackend.utils.Format;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FamiliaAnimalDTO implements Serializable {
    private Long idFamilia;
    private String nombre;
    private String periodo;
    @JsonFormat(pattern=Format.LocalDateYearMonthDay)
    private LocalDate fechaRegistro;
    private String tipo;
    private Integer cantidad;
    private String caracteristica;
}
