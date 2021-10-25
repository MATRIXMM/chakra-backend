package com.matrixmm.chakrabackend.service;

import com.matrixmm.chakrabackend.dto.FamiliaAlimentacionHorarioDTO;
import com.matrixmm.chakrabackend.dto.FamiliaIncidenciaAlimentacionHorarioDTO;
import com.matrixmm.chakrabackend.model.Alimentacion;

import java.util.List;

public interface FamiliaIncidenciaAlimentacionHorarioService {
       Boolean existeFamilia(Long idFamilia);
       Boolean existeFamiliaIncidenciaAlimentacion(FamiliaIncidenciaAlimentacionHorarioDTO familiaIncidenciaAlimentacionHorarioDTO);
       Boolean existeAlimentacion(Long idAlimentacion);
       List<FamiliaIncidenciaAlimentacionHorarioDTO> listar (String periodo, String tipo, Integer perPage, Integer page);
       void crear(FamiliaIncidenciaAlimentacionHorarioDTO familiaIncidenciaAlimentacionHorarioDTO);
       void actualizar(FamiliaIncidenciaAlimentacionHorarioDTO familiaIncidenciaAlimentacionHorarioDTO);
       FamiliaIncidenciaAlimentacionHorarioDTO validar(Long idFamilia);
}
