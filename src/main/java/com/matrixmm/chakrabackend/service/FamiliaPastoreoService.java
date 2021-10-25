package com.matrixmm.chakrabackend.service;

import com.matrixmm.chakrabackend.dto.FamiliaAlimentacionHorarioDTO;
import com.matrixmm.chakrabackend.dto.FamiliaCuidadoSanitarioDTO;
import com.matrixmm.chakrabackend.dto.FamiliaPastoreoDTO;
import com.matrixmm.chakrabackend.model.Familia;
import com.matrixmm.chakrabackend.model.Pastoreo;

import java.util.List;

public interface FamiliaPastoreoService {
    Boolean existeFamilia(Long idFamilia);
    Boolean existeFamiliaPastoreo(FamiliaPastoreoDTO familiaPastoreoDTO);
    Boolean existePastoreo(Long idPastoreo);
    List<FamiliaPastoreoDTO> listar (String periodo, String tipo, Integer perPage, Integer page);
    void crear(FamiliaPastoreoDTO familiaPastoreoDTO);
    void actualizar(FamiliaPastoreoDTO familiaPastoreoDTO);
    Pastoreo validar(Long idFamilia);
}
