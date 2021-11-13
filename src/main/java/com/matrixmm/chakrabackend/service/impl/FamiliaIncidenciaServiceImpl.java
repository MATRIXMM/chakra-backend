package com.matrixmm.chakrabackend.service.impl;

import com.matrixmm.chakrabackend.dao.*;
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

    @Autowired
    AlimentacionDAO alimentacionDAO;

    @Autowired
    HorarioDAO horarioDAO;

    @Autowired
    CuidadoSanitarioDAO cuidadoSanitarioDAO;

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
        System.out.println("****************************************************");
        System.out.println(familias);
        for (Familia familia : familias) {
            List<Incidencia> incidencias = incidenciaDAO.findByIdFamilia(familia.getIdFamilia());
            System.out.println(incidencias);

            for (Incidencia incidencia : incidencias) {
                FamiliaIncidenciaDTO familiaIncidenciaDTO = new FamiliaIncidenciaDTO();
                familiaIncidenciaDTO.setIdFamilia(familia.getIdFamilia());
                familiaIncidenciaDTO.setNombreFamilia(familia.getNombre());
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
    public void eliminar(FamiliaIncidenciaDTO familiaIncidenciaDTO) {
        Familia familia = familiaDAO.getById(familiaIncidenciaDTO.getIdFamilia());
        Incidencia incidencia = incidenciaDAO.getById(familiaIncidenciaDTO.getIdIncidencia());

        List<Alimentacion> alimentaciones = alimentacionDAO.findByIdFamiliaAndIdIncidencia(familiaIncidenciaDTO.getIdFamilia(), familiaIncidenciaDTO.getIdIncidencia());

        for (Alimentacion alimentacion : alimentaciones) {
            List<Horario> horarios = horarioDAO.findByIdAlimentacion(alimentacion.getIdAlimentacion());

            for (Horario horario: horarios) {
                horarioDAO.delete(horario);
            }

            alimentacionDAO.delete(alimentacion);
        }

        List<CuidadoSanitario> cuidadosSanitarios = cuidadoSanitarioDAO.findByIdFamiliaAndIdIncidencia(familiaIncidenciaDTO.getIdFamilia(), familiaIncidenciaDTO.getIdIncidencia());

        for (CuidadoSanitario cuidadoSanitario : cuidadosSanitarios) {
            cuidadoSanitarioDAO.delete(cuidadoSanitario);
        }

        incidenciaDAO.delete(incidencia);
    }

    @Override
    public Incidencia validar(Long idIncidencia) {
        List<Incidencia> lista = incidenciaDAO.findByIdIncidencia(idIncidencia);

        if (lista != null && !lista.isEmpty()) {
            return lista.get(0);
        }

        return null;
    }
}
