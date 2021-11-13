package com.matrixmm.chakrabackend.service;

import com.matrixmm.chakrabackend.dto.FamiliaAlimentacionHorarioDTO;
import com.matrixmm.chakrabackend.dto.FamiliaAnimalDTO;
import com.matrixmm.chakrabackend.model.Alimentacion;

import java.time.LocalDateTime;
import java.util.List;

public interface FamiliaAlimentacionHorarioService {
       Boolean existeFamilia(Long idFamilia);
       Boolean existeFamiliaAlimentacion(FamiliaAlimentacionHorarioDTO familiaAlimentacionHorarioDTO);
       Boolean existeAlimentacion(Long idAlimentacion);
       List<FamiliaAlimentacionHorarioDTO> listar(String periodo, String tipo, Integer perPage, Integer page);
       List<FamiliaAlimentacionHorarioDTO> listarPorFechas(LocalDateTime fechaInicio, LocalDateTime fechaFin);
       void crear(FamiliaAlimentacionHorarioDTO familiaAlimentacionHorarioDTO);
       void actualizar(FamiliaAlimentacionHorarioDTO familiaAlimentacionHorarioDTO);
       FamiliaAlimentacionHorarioDTO validar(Long idFamilia);
}
