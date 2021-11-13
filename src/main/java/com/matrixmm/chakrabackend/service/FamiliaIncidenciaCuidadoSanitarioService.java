package com.matrixmm.chakrabackend.service;

import com.matrixmm.chakrabackend.dto.FamiliaCuidadoSanitarioDTO;
import com.matrixmm.chakrabackend.dto.FamiliaIncidenciaAlimentacionHorarioDTO;
import com.matrixmm.chakrabackend.dto.FamiliaIncidenciaCuidadoSanitarioDTO;
import com.matrixmm.chakrabackend.model.CuidadoSanitario;

import java.time.LocalDate;
import java.util.List;

public interface FamiliaIncidenciaCuidadoSanitarioService {
       Boolean existeFamilia(Long idFamilia);
       Boolean existeFamiliaIncidenciaCuidadoSanitario(FamiliaIncidenciaCuidadoSanitarioDTO familiaIncidenciaCuidadoSanitarioDTO);
       Boolean existeCuidadoSanitario(Long idCuidadoSanitario);
       List<FamiliaIncidenciaCuidadoSanitarioDTO> listar (String periodo, String tipo, Integer perPage, Integer page);
       List<FamiliaIncidenciaCuidadoSanitarioDTO> listarPorFechas(LocalDate fechaInicio, LocalDate fechaFin);
       void crear(FamiliaIncidenciaCuidadoSanitarioDTO familiaIncidenciaCuidadoSanitarioDTO);
       void actualizar(FamiliaIncidenciaCuidadoSanitarioDTO familiaIncidenciaCuidadoSanitarioDTO);
       CuidadoSanitario validar(Long idIncidencia);
}
