package com.matrixmm.chakrabackend.service;

import com.matrixmm.chakrabackend.dto.FamiliaAlimentacionHorarioDTO;
import com.matrixmm.chakrabackend.dto.FamiliaCuidadoSanitarioDTO;
import com.matrixmm.chakrabackend.dto.FamiliaPastoreoDTO;
import com.matrixmm.chakrabackend.model.CuidadoSanitario;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface FamiliaCuidadoSanitarioService {
    Boolean existeFamilia(Long idFamilia);
    Boolean existeFamiliaCuidadoSanitario(FamiliaCuidadoSanitarioDTO familiaCuidadoSanitarioDTO);
    Boolean existeCuidadoSanitario(Long idCuidadoSanitario);
    List<FamiliaCuidadoSanitarioDTO> listar (String periodo, String tipo, Integer perPage, Integer page);
    List<FamiliaCuidadoSanitarioDTO> listarPorFechas(LocalDate fechaInicio, LocalDate fechaFin);
    void crear(FamiliaCuidadoSanitarioDTO familiaCuidadoSanitarioDTO);
    void actualizar(FamiliaCuidadoSanitarioDTO familiaCuidadoSanitarioDTO);
    CuidadoSanitario validar(Long idFamilia);
}
