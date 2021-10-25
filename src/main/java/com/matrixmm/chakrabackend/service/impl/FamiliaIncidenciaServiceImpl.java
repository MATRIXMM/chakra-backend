package com.matrixmm.chakrabackend.service.impl;

import com.matrixmm.chakrabackend.dao.FamiliaDAO;
import com.matrixmm.chakrabackend.dao.IncidenciaDAO;
import com.matrixmm.chakrabackend.dto.FamiliaCuidadoSanitarioDTO;
import com.matrixmm.chakrabackend.dto.FamiliaIncidenciaAlimentacionHorarioDTO;
import com.matrixmm.chakrabackend.dto.FamiliaIncidenciaDTO;
import com.matrixmm.chakrabackend.dto.FamiliaPastoreoDTO;
import com.matrixmm.chakrabackend.model.*;
import com.matrixmm.chakrabackend.service.FamiliaIncidenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.validation.support.BindingAwareConcurrentModel;

import java.util.ArrayList;
import java.util.List;

@Service
public class FamiliaIncidenciaServiceImpl implements FamiliaIncidenciaService {
    @Autowired
    FamiliaDAO familiaDAO;

    @Autowired
    IncidenciaDAO incidenciaDAO;

    @Override
    public Boolean existeFamilia(Long idFamilia) {
        return familiaDAO.existsById(idFamilia);
    }

    @Override
    public Boolean existeFamiliaIncidencia(FamiliaIncidenciaDTO familiaIncidenciaDTO) {
        List<Incidencia> lista = incidenciaDAO.existeFamiliaIncidencia(familiaIncidenciaDTO.getIdFamilia(), familiaIncidenciaDTO.getFechaRegistro());

        if (lista != null && !lista.isEmpty()) {
            return true;
        }

        return false;
    }

    @Override
    public Boolean existeIncidencia(Long idIncidencia) {
        return incidenciaDAO.existsById(idIncidencia);
    }

    @Override
    public void crear(FamiliaIncidenciaDTO familiaIncidenciaDTO) {
        Familia familia = familiaDAO.getById(familiaIncidenciaDTO.getIdFamilia());

        Incidencia incidencia = new Incidencia();
        incidencia.setFechaRegistro(familiaIncidenciaDTO.getFechaRegistro());
        incidencia.setNombreAnimal(familiaIncidenciaDTO.getNombreAnimal());
        incidencia.setCantidadAnimales(familiaIncidenciaDTO.getCantidadAnimales());
        incidencia.setGravedadIncidencia(familiaIncidenciaDTO.getGravedadIncidencia());
        incidencia.setObservacion(familiaIncidenciaDTO.getObservacion());
        incidencia.setResultado(familiaIncidenciaDTO.getResultado());
        incidencia.setFamilia(familia);

        incidenciaDAO.save(incidencia);
    }

    @Override
    public List<FamiliaIncidenciaDTO> listar(String periodo, String tipo, Integer perPage, Integer page) {
        Pageable pagination = PageRequest.of(page-1, perPage);
        List<FamiliaIncidenciaDTO> familiasPerPage = new ArrayList<FamiliaIncidenciaDTO>();
        List<Familia> familias = familiaDAO.findIncidenciaByPeriodoAndTipo(periodo, tipo, pagination);
        for (Familia familia : familias) {
            List<Incidencia> incidencias = incidenciaDAO.findByIdFamilia(familia.getIdFamilia());

            for (Incidencia incidencia : incidencias) {
                FamiliaIncidenciaDTO familiaIncidenciaDTO = new FamiliaIncidenciaDTO();
                familiaIncidenciaDTO.setIdFamilia(familia.getIdFamilia());
                familiaIncidenciaDTO.setIdIncidencia(incidencia.getIdIncidencia());
                familiaIncidenciaDTO.setFechaRegistro(incidencia.getFechaRegistro());
                familiaIncidenciaDTO.setNombreAnimal(incidencia.getNombreAnimal());
                familiaIncidenciaDTO.setCantidadAnimales(incidencia.getCantidadAnimales());
                familiaIncidenciaDTO.setGravedadIncidencia(incidencia.getGravedadIncidencia());
                familiaIncidenciaDTO.setObservacion(incidencia.getObservacion());
                familiaIncidenciaDTO.setResultado(incidencia.getResultado());

                familiasPerPage.add(familiaIncidenciaDTO);
            }
        }
        return familiasPerPage;
    }

    @Override
    public void actualizar(FamiliaIncidenciaDTO familiaIncidenciaDTO) {
        Familia familia = familiaDAO.getById(familiaIncidenciaDTO.getIdFamilia());
        Incidencia incidencia = incidenciaDAO.getById(familiaIncidenciaDTO.getIdIncidencia());

        incidencia.setFechaRegistro(familiaIncidenciaDTO.getFechaRegistro());
        incidencia.setNombreAnimal(familiaIncidenciaDTO.getNombreAnimal());
        incidencia.setCantidadAnimales(familiaIncidenciaDTO.getCantidadAnimales());
        incidencia.setGravedadIncidencia(familiaIncidenciaDTO.getGravedadIncidencia());
        incidencia.setObservacion(familiaIncidenciaDTO.getObservacion());
        incidencia.setResultado(familiaIncidenciaDTO.getResultado());
        incidencia.setFamilia(familia);

        incidenciaDAO.save(incidencia);
    }

    @Override
    public Incidencia validar(Long idFamilia) {
        List<Incidencia> lista = incidenciaDAO.findByIdFamilia(idFamilia);

        if (lista != null && !lista.isEmpty()) {
            return lista.get(0);
        }

        return null;
    }
}
