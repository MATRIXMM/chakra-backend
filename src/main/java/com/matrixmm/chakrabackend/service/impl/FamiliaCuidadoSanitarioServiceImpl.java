package com.matrixmm.chakrabackend.service.impl;

import com.matrixmm.chakrabackend.dao.CuidadoSanitarioDAO;
import com.matrixmm.chakrabackend.dao.FamiliaDAO;
import com.matrixmm.chakrabackend.dto.FamiliaAlimentacionHorarioDTO;
import com.matrixmm.chakrabackend.dto.FamiliaCuidadoSanitarioDTO;
import com.matrixmm.chakrabackend.dto.FamiliaPastoreoDTO;
import com.matrixmm.chakrabackend.model.*;
import com.matrixmm.chakrabackend.service.FamiliaCuidadoSanitarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FamiliaCuidadoSanitarioServiceImpl implements FamiliaCuidadoSanitarioService {
    @Autowired
    FamiliaDAO familiaDAO;

    @Autowired
    CuidadoSanitarioDAO cuidadoSanitarioDAO;

    @Override
    public Boolean existeFamilia(Long idFamilia) {
        return familiaDAO.existsById(idFamilia);
    }

    @Override
    public Boolean existeFamiliaCuidadoSanitario(FamiliaCuidadoSanitarioDTO familiaCuidadoSanitarioDTO) {
        List<CuidadoSanitario> lista = cuidadoSanitarioDAO.existeFamiliaCuidadoSanitario(familiaCuidadoSanitarioDTO.getIdFamilia(), familiaCuidadoSanitarioDTO.getNombre(), familiaCuidadoSanitarioDTO.getFecha());

        if (lista != null && !lista.isEmpty()) {
            return true;
        }

        return false;
    }

    @Override
    public Boolean existeCuidadoSanitario(Long idCuidadoSanitario) {
        return cuidadoSanitarioDAO.existsById(idCuidadoSanitario);
    }

    @Override
    public void crear(FamiliaCuidadoSanitarioDTO familiaCuidadoSanitarioDTO) {
        Familia familia = familiaDAO.getById(familiaCuidadoSanitarioDTO.getIdFamilia());

        CuidadoSanitario cuidadoSanitario = new CuidadoSanitario();
        cuidadoSanitario.setNombre(familiaCuidadoSanitarioDTO.getNombre());
        cuidadoSanitario.setFecha(familiaCuidadoSanitarioDTO.getFecha());
        cuidadoSanitario.setEstado(familiaCuidadoSanitarioDTO.getEstado());
        cuidadoSanitario.setFamilia(familia);
        cuidadoSanitario.setIncidencia(null);

        cuidadoSanitarioDAO.save(cuidadoSanitario);
    }

    @Override
    public List<FamiliaCuidadoSanitarioDTO> listar(String periodo, String tipo, Integer perPage, Integer page) {
        Pageable pagination = PageRequest.of(page-1, perPage);
        List<FamiliaCuidadoSanitarioDTO> familiasPerPage = new ArrayList<FamiliaCuidadoSanitarioDTO>();
        List<Familia> familias = familiaDAO.findByPeriodoAndTipo(periodo, tipo, pagination);
        for (Familia familia : familias) {
            List<CuidadoSanitario> cuidados_sanitarios = cuidadoSanitarioDAO.findByIdFamilia(familia.getIdFamilia());

            for (CuidadoSanitario cuidado_sanitario : cuidados_sanitarios) {
                FamiliaCuidadoSanitarioDTO familiaCuidadoSanitarioDTO = new FamiliaCuidadoSanitarioDTO();
                familiaCuidadoSanitarioDTO.setIdFamilia(familia.getIdFamilia());
                familiaCuidadoSanitarioDTO.setIdCuidadoSanitario(cuidado_sanitario.getIdCuidadoSanitario());
                familiaCuidadoSanitarioDTO.setNombre(cuidado_sanitario.getNombre());
                familiaCuidadoSanitarioDTO.setFecha(cuidado_sanitario.getFecha());
                familiaCuidadoSanitarioDTO.setEstado(cuidado_sanitario.getEstado());
                familiaCuidadoSanitarioDTO.setIdIncidencia(null);

                familiasPerPage.add(familiaCuidadoSanitarioDTO);
            }
        }
        return familiasPerPage;
    }

    @Override
    public void actualizar(FamiliaCuidadoSanitarioDTO familiaCuidadoSanitarioDTO) {
        Familia familia = familiaDAO.getById(familiaCuidadoSanitarioDTO.getIdFamilia());
        CuidadoSanitario cuidadoSanitario = cuidadoSanitarioDAO.getById(familiaCuidadoSanitarioDTO.getIdCuidadoSanitario());

        cuidadoSanitario.setNombre(familiaCuidadoSanitarioDTO.getNombre());
        cuidadoSanitario.setFecha(familiaCuidadoSanitarioDTO.getFecha());
        cuidadoSanitario.setEstado(familiaCuidadoSanitarioDTO.getEstado());
        cuidadoSanitario.setFamilia(familia);
        cuidadoSanitario.setIncidencia(null);

        cuidadoSanitarioDAO.save(cuidadoSanitario);
    }

    @Override
    public CuidadoSanitario validar(Long idFamilia) {
        List<CuidadoSanitario> lista = cuidadoSanitarioDAO.findByIdFamilia(idFamilia);

        if (lista != null && !lista.isEmpty()) {
            return lista.get(0);
        }

        return null;
    }
}
