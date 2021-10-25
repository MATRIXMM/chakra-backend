package com.matrixmm.chakrabackend.service.impl;

import com.matrixmm.chakrabackend.dao.AlimentacionDAO;
import com.matrixmm.chakrabackend.dao.FamiliaDAO;
import com.matrixmm.chakrabackend.dao.HorarioDAO;
import com.matrixmm.chakrabackend.dao.IncidenciaDAO;
import com.matrixmm.chakrabackend.dto.FamiliaAlimentacionHorarioDTO;
import com.matrixmm.chakrabackend.dto.FamiliaIncidenciaAlimentacionHorarioDTO;
import com.matrixmm.chakrabackend.model.Alimentacion;
import com.matrixmm.chakrabackend.model.Familia;
import com.matrixmm.chakrabackend.model.Horario;
import com.matrixmm.chakrabackend.model.Incidencia;
import com.matrixmm.chakrabackend.service.FamiliaIncidenciaAlimentacionHorarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FamiliaIncidenciaAlimentacionHorarioServiceImpl implements FamiliaIncidenciaAlimentacionHorarioService {
    @Autowired
    FamiliaDAO familiaDAO;

    @Autowired
    IncidenciaDAO incidenciaDAO;

    @Autowired
    AlimentacionDAO alimentacionDAO;

    @Autowired
    HorarioDAO horarioDAO;

    @Override
    public Boolean existeFamilia(Long idFamilia) {
        return familiaDAO.existsById(idFamilia);
    }

    @Override
    public Boolean existeFamiliaIncidenciaAlimentacion(FamiliaIncidenciaAlimentacionHorarioDTO familiaIncidenciaAlimentacionHorarioDTO) {
        List<Alimentacion> lista = alimentacionDAO.existeFamiliaIncidenciaAlimentacion(familiaIncidenciaAlimentacionHorarioDTO.getIdFamilia(), familiaIncidenciaAlimentacionHorarioDTO.getIdIncidencia());

        if (lista != null && !lista.isEmpty()) {
            return true;
        }

        return false;
    }

    @Override
    public Boolean existeAlimentacion(Long idAlimentacion) {
        return alimentacionDAO.existsById(idAlimentacion);
    }

    @Override
    public void crear(FamiliaIncidenciaAlimentacionHorarioDTO familiaIncidenciaAlimentacionHorarioDTO) {
        Familia familia = familiaDAO.getById(familiaIncidenciaAlimentacionHorarioDTO.getIdFamilia());
        Incidencia incidencia = incidenciaDAO.getById(familiaIncidenciaAlimentacionHorarioDTO.getIdIncidencia());

        Alimentacion alimentacion = new Alimentacion();
        alimentacion.setTipo(familiaIncidenciaAlimentacionHorarioDTO.getTipo());
        alimentacion.setCantidad(familiaIncidenciaAlimentacionHorarioDTO.getCantidad());
        alimentacion.setDiasSeguimiento(familiaIncidenciaAlimentacionHorarioDTO.getDiasSeguimiento());
        alimentacion.setEstado(familiaIncidenciaAlimentacionHorarioDTO.getEstado());
        alimentacion.setFamilia(familia);
        alimentacion.setIncidencia(incidencia);

        alimentacionDAO.save(alimentacion);

        for (Horario horario: familiaIncidenciaAlimentacionHorarioDTO.getHorarios()) {
            Horario horarioToSave = new Horario();
            horarioToSave.setHorario(horario.getHorario());
            horarioToSave.setEstado(horario.getEstado());
            horarioToSave.setAlimentacion(alimentacion);
            horarioDAO.save(horarioToSave);
        }
    }

    @Override
    public List<FamiliaIncidenciaAlimentacionHorarioDTO> listar(String periodo, String tipo, Integer perPage, Integer page) {
        Pageable pagination = PageRequest.of(page-1, perPage);
        List<FamiliaIncidenciaAlimentacionHorarioDTO> familiasPerPage = new ArrayList<FamiliaIncidenciaAlimentacionHorarioDTO>();
        List<Familia> familias = familiaDAO.findIncidenciaByPeriodoAndTipo(periodo, tipo, pagination);
        for (Familia familia : familias) {
            List<Alimentacion> alimentaciones = alimentacionDAO.findByIdFamiliaAndIncidencia(familia.getIdFamilia());

            for (Alimentacion alimentacion : alimentaciones) {
                Incidencia incidencia = alimentacion.getIncidencia();

                FamiliaIncidenciaAlimentacionHorarioDTO familiaIncidenciaAlimentacionHorarioDTO =  new FamiliaIncidenciaAlimentacionHorarioDTO();
                familiaIncidenciaAlimentacionHorarioDTO.setIdFamilia(familia.getIdFamilia());
                familiaIncidenciaAlimentacionHorarioDTO.setIdAlimentacion(alimentacion.getIdAlimentacion());
                familiaIncidenciaAlimentacionHorarioDTO.setIdIncidencia(incidencia.getIdIncidencia());
                familiaIncidenciaAlimentacionHorarioDTO.setTipo(alimentacion.getTipo());
                familiaIncidenciaAlimentacionHorarioDTO.setCantidad(alimentacion.getCantidad());
                familiaIncidenciaAlimentacionHorarioDTO.setDiasSeguimiento(alimentacion.getDiasSeguimiento());
                familiaIncidenciaAlimentacionHorarioDTO.setEstado(alimentacion.getEstado());

                List<Horario> horarios = horarioDAO.findByIdAlimentacion(alimentacion.getIdAlimentacion());

                familiaIncidenciaAlimentacionHorarioDTO.setHorarios(horarios);
                familiaIncidenciaAlimentacionHorarioDTO.setFechaRegistro(incidencia.getFechaRegistro());
                familiaIncidenciaAlimentacionHorarioDTO.setNombreAnimal(incidencia.getNombreAnimal());
                familiaIncidenciaAlimentacionHorarioDTO.setCantidadAnimales(incidencia.getCantidadAnimales());
                familiaIncidenciaAlimentacionHorarioDTO.setGravedadIncidencia(incidencia.getGravedadIncidencia());
                familiaIncidenciaAlimentacionHorarioDTO.setObservacion(incidencia.getObservacion());
                familiaIncidenciaAlimentacionHorarioDTO.setResultado(incidencia.getResultado());

                familiasPerPage.add(familiaIncidenciaAlimentacionHorarioDTO);
            }
        }
        return familiasPerPage;
    }

    @Override
    public void actualizar(FamiliaIncidenciaAlimentacionHorarioDTO familiaIncidenciaAlimentacionHorarioDTO) {
        Familia familia = familiaDAO.getById(familiaIncidenciaAlimentacionHorarioDTO.getIdFamilia());
        Incidencia incidencia = incidenciaDAO.getById(familiaIncidenciaAlimentacionHorarioDTO.getIdIncidencia());
        Alimentacion alimentacion = alimentacionDAO.getById(familiaIncidenciaAlimentacionHorarioDTO.getIdAlimentacion());

        incidencia.setFechaRegistro(familiaIncidenciaAlimentacionHorarioDTO.getFechaRegistro());
        incidencia.setNombreAnimal(familiaIncidenciaAlimentacionHorarioDTO.getNombreAnimal());
        incidencia.setCantidadAnimales(familiaIncidenciaAlimentacionHorarioDTO.getCantidadAnimales());
        incidencia.setGravedadIncidencia(familiaIncidenciaAlimentacionHorarioDTO.getGravedadIncidencia());
        incidencia.setObservacion(familiaIncidenciaAlimentacionHorarioDTO.getObservacion());
        incidencia.setResultado(familiaIncidenciaAlimentacionHorarioDTO.getResultado());
        incidencia.setFamilia(familia);

        incidenciaDAO.save(incidencia);

        alimentacion.setTipo(familiaIncidenciaAlimentacionHorarioDTO.getTipo());
        alimentacion.setCantidad(familiaIncidenciaAlimentacionHorarioDTO.getCantidad());
        alimentacion.setDiasSeguimiento(familiaIncidenciaAlimentacionHorarioDTO.getDiasSeguimiento());
        alimentacion.setEstado(familiaIncidenciaAlimentacionHorarioDTO.getEstado());
        alimentacion.setFamilia(familia);
        alimentacion.setIncidencia(incidencia);

        alimentacionDAO.save(alimentacion);

        for (Horario horario: familiaIncidenciaAlimentacionHorarioDTO.getHorarios()) {
            Horario horarioToSave = horarioDAO.getById(horario.getIdHorario());
            horarioToSave.setHorario(horario.getHorario());
            horarioToSave.setEstado(horario.getEstado());
            horarioDAO.save(horarioToSave);
        }
    }

    @Override
    public FamiliaIncidenciaAlimentacionHorarioDTO validar(Long idFamilia) {
        List<Alimentacion> lista = alimentacionDAO.findByIdFamiliaAndIncidencia(idFamilia);

        if (lista != null && !lista.isEmpty()) {
            Alimentacion alimentacion = lista.get(0);
            Familia familia = alimentacion.getFamilia();
            Incidencia incidencia = alimentacion.getIncidencia();

            FamiliaIncidenciaAlimentacionHorarioDTO familiaIncidenciaAlimentacionHorarioDTO =  new FamiliaIncidenciaAlimentacionHorarioDTO();
            familiaIncidenciaAlimentacionHorarioDTO.setIdFamilia(familia.getIdFamilia());
            familiaIncidenciaAlimentacionHorarioDTO.setIdAlimentacion(alimentacion.getIdAlimentacion());
            familiaIncidenciaAlimentacionHorarioDTO.setIdIncidencia(incidencia.getIdIncidencia());
            familiaIncidenciaAlimentacionHorarioDTO.setTipo(alimentacion.getTipo());
            familiaIncidenciaAlimentacionHorarioDTO.setCantidad(alimentacion.getCantidad());
            familiaIncidenciaAlimentacionHorarioDTO.setDiasSeguimiento(alimentacion.getDiasSeguimiento());
            familiaIncidenciaAlimentacionHorarioDTO.setEstado(alimentacion.getEstado());

            List<Horario> horarios = horarioDAO.findByIdAlimentacion(alimentacion.getIdAlimentacion());

            familiaIncidenciaAlimentacionHorarioDTO.setHorarios(horarios);
            familiaIncidenciaAlimentacionHorarioDTO.setFechaRegistro(incidencia.getFechaRegistro());
            familiaIncidenciaAlimentacionHorarioDTO.setNombreAnimal(incidencia.getNombreAnimal());
            familiaIncidenciaAlimentacionHorarioDTO.setCantidadAnimales(incidencia.getCantidadAnimales());
            familiaIncidenciaAlimentacionHorarioDTO.setGravedadIncidencia(incidencia.getGravedadIncidencia());
            familiaIncidenciaAlimentacionHorarioDTO.setObservacion(incidencia.getObservacion());
            familiaIncidenciaAlimentacionHorarioDTO.setResultado(incidencia.getResultado());

            return familiaIncidenciaAlimentacionHorarioDTO;
        }

        return null;
    }
}
