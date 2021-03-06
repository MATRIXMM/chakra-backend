package com.matrixmm.chakrabackend.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.matrixmm.chakrabackend.model.Horario;
import com.matrixmm.chakrabackend.utils.Format;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FamiliaAlimentacionHorarioDTO implements Serializable {
    private Long idFamilia;
    private Long idAlimentacion;
    private String tipo;
    private Integer cantidad;
    private Integer diasSeguimiento;
    private Boolean estado;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern=Format.DATE_TIME)
    private List<Horario> horarios;
}
