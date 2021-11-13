package com.matrixmm.chakrabackend.service.impl;

import com.matrixmm.chakrabackend.dao.AlimentacionDAO;
import com.matrixmm.chakrabackend.dao.FamiliaDAO;
import com.matrixmm.chakrabackend.dao.HorarioDAO;
import com.matrixmm.chakrabackend.dto.FamiliaAlimentacionHorarioDTO;
import com.matrixmm.chakrabackend.model.Alimentacion;
import com.matrixmm.chakrabackend.model.Familia;
import com.matrixmm.chakrabackend.model.Horario;
import com.matrixmm.chakrabackend.service.FamiliaAlimentacionHorarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class FamiliaAlimentacionHorarioServiceImpl implements FamiliaAlimentacionHorarioService {
    @Autowired
    FamiliaDAO familiaDAO;

    @Autowired
    AlimentacionDAO alimentacionDAO;

    @Autowired
    HorarioDAO horarioDAO;

    @Override
    public Boolean existeFamilia(Long idFamilia) {
        return familiaDAO.existsById(idFamilia);
    }

    @Override
    public Boolean existeFamiliaAlimentacion(FamiliaAlimentacionHorarioDTO familiaAlimentacionHorarioDTO) {
        List<Alimentacion> lista = alimentacionDAO.existeFamiliaAlimentacion(familiaAlimentacionHorarioDTO.getIdFamilia(), familiaAlimentacionHorarioDTO.getTipo(), familiaAlimentacionHorarioDTO.getCantidad());

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
    public void crear(FamiliaAlimentacionHorarioDTO familiaAlimentacionHorarioDTO) {
        Familia familia = familiaDAO.getById(familiaAlimentacionHorarioDTO.getIdFamilia());

        Alimentacion alimentacion = new Alimentacion();
        alimentacion.setTipo(familiaAlimentacionHorarioDTO.getTipo());
        alimentacion.setCantidad(familiaAlimentacionHorarioDTO.getCantidad());
        alimentacion.setDiasSeguimiento(null);
        alimentacion.setEstado(familiaAlimentacionHorarioDTO.getEstado());
        alimentacion.setFamilia(familia);
        alimentacion.setIncidencia(null);

        alimentacionDAO.save(alimentacion);

        for (Horario horario: familiaAlimentacionHorarioDTO.getHorarios()) {
            Horario horarioToSave = new Horario();
            horarioToSave.setHorario(horario.getHorario());
            horarioToSave.setEstado(horario.getEstado());
            horarioToSave.setAlimentacion(alimentacion);
            horarioDAO.save(horarioToSave);
        }
    }

    @Override
    public List<FamiliaAlimentacionHorarioDTO> listar(String periodo, String tipo, Integer perPage, Integer page) {
        Pageable pagination = PageRequest.of(page-1, perPage);
        List<FamiliaAlimentacionHorarioDTO> familiasPerPage = new ArrayList<FamiliaAlimentacionHorarioDTO>();
        List<Familia> familias = familiaDAO.findByPeriodoAndTipo(periodo, tipo, pagination);
        for (Familia familia : familias) {
            List<Alimentacion> alimentaciones = alimentacionDAO.findByIdFamilia(familia.getIdFamilia());

            for (Alimentacion alimentacion : alimentaciones) {
                FamiliaAlimentacionHorarioDTO familiaAlimentacionHorarioDTO = new FamiliaAlimentacionHorarioDTO();
                familiaAlimentacionHorarioDTO.setIdFamilia(familia.getIdFamilia());
                familiaAlimentacionHorarioDTO.setIdAlimentacion(alimentacion.getIdAlimentacion());
                familiaAlimentacionHorarioDTO.setTipo(alimentacion.getTipo());
                familiaAlimentacionHorarioDTO.setCantidad(alimentacion.getCantidad());
                familiaAlimentacionHorarioDTO.setDiasSeguimiento(null);
                familiaAlimentacionHorarioDTO.setEstado(alimentacion.getEstado());

                List<Horario> horarios = horarioDAO.findByIdAlimentacion(alimentacion.getIdAlimentacion());
                familiaAlimentacionHorarioDTO.setHorarios(horarios);

                familiasPerPage.add(familiaAlimentacionHorarioDTO);
            }
        }
        return familiasPerPage;
    }

    public List<FamiliaAlimentacionHorarioDTO> listarPorFechas(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        List<Horario> horarios = horarioDAO.findByFechas(fechaInicio, fechaFin);

        Long idAlimentacionPrevious = null;
        FamiliaAlimentacionHorarioDTO familiaAlimentacionHorarioDTO = new FamiliaAlimentacionHorarioDTO();
        List<Horario> horariosToInsert = new ArrayList<Horario>();

        List<FamiliaAlimentacionHorarioDTO> familias = new ArrayList<FamiliaAlimentacionHorarioDTO>();

        for (Horario horario : horarios) {
            Alimentacion alimentacion = horario.getAlimentacion();
            Familia familia = alimentacion.getFamilia();

            if ((idAlimentacionPrevious == null) || (!idAlimentacionPrevious.equals(alimentacion.getIdAlimentacion()))) {
                familiaAlimentacionHorarioDTO = new FamiliaAlimentacionHorarioDTO();

                horariosToInsert = new ArrayList<Horario>();

                familiaAlimentacionHorarioDTO.setIdFamilia(familia.getIdFamilia());
                familiaAlimentacionHorarioDTO.setIdAlimentacion(alimentacion.getIdAlimentacion());
                familiaAlimentacionHorarioDTO.setTipo(alimentacion.getTipo());
                familiaAlimentacionHorarioDTO.setCantidad(alimentacion.getCantidad());
                familiaAlimentacionHorarioDTO.setDiasSeguimiento(null);
                familiaAlimentacionHorarioDTO.setEstado(alimentacion.getEstado());

                familias.add(familiaAlimentacionHorarioDTO);

                idAlimentacionPrevious = alimentacion.getIdAlimentacion();
            }

            horariosToInsert.add(horario);

            familiaAlimentacionHorarioDTO.setHorarios(horariosToInsert);
        }

        return familias;
    }

    @Override
    public void actualizar(FamiliaAlimentacionHorarioDTO familiaAlimentacionHorarioDTO) {
        Familia familia = familiaDAO.getById(familiaAlimentacionHorarioDTO.getIdFamilia());
        Alimentacion alimentacion = alimentacionDAO.getById(familiaAlimentacionHorarioDTO.getIdAlimentacion());

        alimentacion.setTipo(familiaAlimentacionHorarioDTO.getTipo());
        alimentacion.setCantidad(familiaAlimentacionHorarioDTO.getCantidad());
        alimentacion.setDiasSeguimiento(null);
        alimentacion.setEstado(familiaAlimentacionHorarioDTO.getEstado());
        alimentacion.setFamilia(familia);
        alimentacion.setIncidencia(null);

        alimentacionDAO.save(alimentacion);

        for (Horario horario: familiaAlimentacionHorarioDTO.getHorarios()) {
            Horario horarioToSave = horarioDAO.getById(horario.getIdHorario());
            horarioToSave.setHorario(horario.getHorario());
            horarioToSave.setEstado(horario.getEstado());
            horarioDAO.save(horarioToSave);
        }
    }

    @Override
    public FamiliaAlimentacionHorarioDTO validar(Long idFamilia) {
        List<Alimentacion> lista = alimentacionDAO.findByIdFamilia(idFamilia);

        if (lista != null && !lista.isEmpty()) {
            Alimentacion alimentacion = lista.get(0);

            FamiliaAlimentacionHorarioDTO familiaAlimentacionHorarioDTO = new FamiliaAlimentacionHorarioDTO();
            familiaAlimentacionHorarioDTO.setIdFamilia(alimentacion.getFamilia().getIdFamilia());
            familiaAlimentacionHorarioDTO.setIdAlimentacion(alimentacion.getIdAlimentacion());
            familiaAlimentacionHorarioDTO.setTipo(alimentacion.getTipo());
            familiaAlimentacionHorarioDTO.setCantidad(alimentacion.getCantidad());
            familiaAlimentacionHorarioDTO.setDiasSeguimiento(null);
            familiaAlimentacionHorarioDTO.setEstado(alimentacion.getEstado());

            List<Horario> horarios = horarioDAO.findByIdAlimentacion(alimentacion.getIdAlimentacion());
            familiaAlimentacionHorarioDTO.setHorarios(horarios);

            return familiaAlimentacionHorarioDTO;
        }

        return null;
    }
}
