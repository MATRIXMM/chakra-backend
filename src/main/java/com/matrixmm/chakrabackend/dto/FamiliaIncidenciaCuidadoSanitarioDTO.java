package com.matrixmm.chakrabackend.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.matrixmm.chakrabackend.model.Horario;
import com.matrixmm.chakrabackend.utils.Format;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FamiliaIncidenciaCuidadoSanitarioDTO implements Serializable {
    private Long idFamilia;
    private Long idCuidadoSanitario;
    private Long idIncidencia;
    private String nombre;
    @JsonFormat(pattern=Format.LocalDateYearMonthDay)
    private LocalDate fecha;
    private Boolean estado;
    @JsonFormat(pattern=Format.LocalDateYearMonthDay)
    private LocalDate fechaRegistro;
    private String nombreAnimal;
    private Long cantidadAnimales;
    private String gravedadIncidencia;
    private String observacion;
    private String resultado;
}
