package com.matrixmm.chakrabackend.service.impl;

import com.matrixmm.chakrabackend.dao.AlimentacionDAO;
import com.matrixmm.chakrabackend.dao.FamiliaDAO;
import com.matrixmm.chakrabackend.dao.HorarioDAO;
import com.matrixmm.chakrabackend.dao.PastoreoDAO;
import com.matrixmm.chakrabackend.dto.FamiliaAlimentacionHorarioDTO;
import com.matrixmm.chakrabackend.dto.FamiliaCuidadoSanitarioDTO;
import com.matrixmm.chakrabackend.dto.FamiliaPastoreoDTO;
import com.matrixmm.chakrabackend.model.*;
import com.matrixmm.chakrabackend.service.FamiliaAlimentacionHorarioService;
import com.matrixmm.chakrabackend.service.FamiliaPastoreoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class FamiliaPastoreoServiceImpl implements FamiliaPastoreoService {
    @Autowired
    FamiliaDAO familiaDAO;

    @Autowired
    PastoreoDAO pastoreoDAO;

    @Override
    public Boolean existeFamilia(Long idFamilia) {
        return familiaDAO.existsById(idFamilia);
    }

    @Override
    public Boolean existeFamiliaPastoreo(FamiliaPastoreoDTO familiaPastoreoDTO) {
        List<Pastoreo> lista = pastoreoDAO.existeFamiliaPastoreo(familiaPastoreoDTO.getIdFamilia(), familiaPastoreoDTO.getTiempo(), familiaPastoreoDTO.getHorario(), familiaPastoreoDTO.getDia());

        if (lista != null && !lista.isEmpty()) {
            return true;
        }

        return false;
    }

    @Override
    public Boolean existePastoreo(Long idPastoreo) {
        return pastoreoDAO.existsById(idPastoreo);
    }

    @Override
    public void crear(FamiliaPastoreoDTO familiaPastoreoDTO) {
        Familia familia = familiaDAO.getById(familiaPastoreoDTO.getIdFamilia());

        Pastoreo pastoreo = new Pastoreo();
        pastoreo.setTiempo(familiaPastoreoDTO.getTiempo());
        pastoreo.setHorario(familiaPastoreoDTO.getHorario());
        pastoreo.setDia(familiaPastoreoDTO.getDia());
        pastoreo.setEstado(familiaPastoreoDTO.getEstado());
        pastoreo.setFamilia(familia);

        pastoreoDAO.save(pastoreo);
    }

    @Override
    public List<FamiliaPastoreoDTO> listar(String periodo, String tipo, Integer perPage, Integer page) {
        Pageable pagination = PageRequest.of(page-1, perPage);
        List<FamiliaPastoreoDTO> familiasPerPage = new ArrayList<FamiliaPastoreoDTO>();
        List<Familia> familias = familiaDAO.findByPeriodoAndTipo(periodo, tipo, pagination);
        for (Familia familia : familias) {
            List<Pastoreo> pastoreos = pastoreoDAO.findByIdFamilia(familia.getIdFamilia());

            for (Pastoreo pastoreo : pastoreos) {
                FamiliaPastoreoDTO familiaPastoreoDTO = new FamiliaPastoreoDTO();
                familiaPastoreoDTO.setIdFamilia(familia.getIdFamilia());
                familiaPastoreoDTO.setIdPastoreo(pastoreo.getIdPastoreo());
                familiaPastoreoDTO.setTiempo(pastoreo.getTiempo());
                familiaPastoreoDTO.setHorario(pastoreo.getHorario());
                familiaPastoreoDTO.setEstado(pastoreo.getEstado());
                familiaPastoreoDTO.setDia(pastoreo.getDia());

                familiasPerPage.add(familiaPastoreoDTO);
            }
        }
        return familiasPerPage;
    }

    @Override
    public void actualizar(FamiliaPastoreoDTO familiaPastoreoDTO) {
        Familia familia = familiaDAO.getById(familiaPastoreoDTO.getIdFamilia());
        Pastoreo pastoreo = pastoreoDAO.getById(familiaPastoreoDTO.getIdPastoreo());

        pastoreo.setTiempo(familiaPastoreoDTO.getTiempo());
        pastoreo.setHorario(familiaPastoreoDTO.getHorario());
        pastoreo.setDia(familiaPastoreoDTO.getDia());
        pastoreo.setEstado(familiaPastoreoDTO.getEstado());
        pastoreo.setFamilia(familia);

        pastoreoDAO.save(pastoreo);
    }

    @Override
    public Pastoreo validar(Long idFamilia) {
        List<Pastoreo> lista = pastoreoDAO.findByIdFamilia(idFamilia);

        if (lista != null && !lista.isEmpty()) {
            return lista.get(0);
        }

        return null;
    }
}
