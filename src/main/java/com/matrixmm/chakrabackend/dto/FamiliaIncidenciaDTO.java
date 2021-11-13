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
public class FamiliaIncidenciaDTO implements Serializable {
    private Long idFamilia;
    private String nombreFamilia;
    private Long idIncidencia;
    @JsonFormat(pattern=Format.LocalDateYearMonthDay)
    private LocalDate fechaRegistro;
    private String nombreAnimal;
    private Long cantidadAnimales;
    private String gravedadIncidencia;
    private String observacion;
    private String resultado;
}
