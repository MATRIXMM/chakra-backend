package com.matrixmm.chakrabackend.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.matrixmm.chakrabackend.utils.Format;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FamiliaPastoreoDTO implements Serializable {
    private Long idFamilia;
    private Long idPastoreo;
    private Integer tiempo;
    @JsonFormat(pattern=Format.LocalTimeHourMinutes)
    private LocalTime horario;
    private String dia;
    private Boolean estado;
}
