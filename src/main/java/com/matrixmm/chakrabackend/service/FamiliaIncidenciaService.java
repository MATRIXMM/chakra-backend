package com.matrixmm.chakrabackend.service;

import com.matrixmm.chakrabackend.dto.FamiliaCuidadoSanitarioDTO;
import com.matrixmm.chakrabackend.dto.FamiliaIncidenciaDTO;
import com.matrixmm.chakrabackend.dto.FamiliaPastoreoDTO;
import com.matrixmm.chakrabackend.model.Familia;
import com.matrixmm.chakrabackend.model.Incidencia;

import java.util.List;

public interface FamiliaIncidenciaService {
    Boolean existeFamilia(Long idFamilia);
    Boolean existeFamiliaIncidencia(FamiliaIncidenciaDTO familiaIncidenciaDTO);
    Boolean existeIncidencia(Long idIncidencia);
    List<FamiliaIncidenciaDTO> listar (String periodo, String tipo, Integer perPage, Integer page);
    void crear(FamiliaIncidenciaDTO familiaIncidenciaDTO);
    void actualizar(FamiliaIncidenciaDTO familiaIncidenciaDTO);
    void eliminar(FamiliaIncidenciaDTO familiaIncidenciaDTO);
    Incidencia validar(Long idIncidencia);
}
