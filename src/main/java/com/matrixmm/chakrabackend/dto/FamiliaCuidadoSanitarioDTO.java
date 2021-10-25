package com.matrixmm.chakrabackend.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.matrixmm.chakrabackend.model.Familia;
import com.matrixmm.chakrabackend.utils.Format;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FamiliaCuidadoSanitarioDTO implements Serializable {
    private Long idFamilia;
    private Long idCuidadoSanitario;
    private String nombre;
    private Long idIncidencia;
    @JsonFormat(pattern= Format.LocalDateYearMonthDay)
    private LocalDate fecha;
    private Boolean estado;
}
