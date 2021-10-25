package com.matrixmm.chakrabackend.service.impl;

import com.matrixmm.chakrabackend.dao.*;
import com.matrixmm.chakrabackend.dto.FamiliaCuidadoSanitarioDTO;
import com.matrixmm.chakrabackend.dto.FamiliaIncidenciaAlimentacionHorarioDTO;
import com.matrixmm.chakrabackend.dto.FamiliaIncidenciaCuidadoSanitarioDTO;
import com.matrixmm.chakrabackend.model.*;
import com.matrixmm.chakrabackend.service.FamiliaIncidenciaCuidadoSanitarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FamiliaIncidenciaCuidadoSanitarioServiceImpl implements FamiliaIncidenciaCuidadoSanitarioService {
    @Autowired
    FamiliaDAO familiaDAO;

    @Autowired
    IncidenciaDAO incidenciaDAO;

    @Autowired
    CuidadoSanitarioDAO cuidadoSanitarioDAO;

    @Autowired
    HorarioDAO horarioDAO;

    @Override
    public Boolean existeFamilia(Long idFamilia) {
        return familiaDAO.existsById(idFamilia);
    }

    @Override
    public Boolean existeFamiliaIncidenciaCuidadoSanitario(FamiliaIncidenciaCuidadoSanitarioDTO familiaIncidenciaCuidadoSanitarioDTO) {
        List<CuidadoSanitario> lista = cuidadoSanitarioDAO.existeFamiliaIncidenciaCuidadoSanitario(familiaIncidenciaCuidadoSanitarioDTO.getIdFamilia(), familiaIncidenciaCuidadoSanitarioDTO.getIdIncidencia());

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
    public void crear(FamiliaIncidenciaCuidadoSanitarioDTO familiaIncidenciaCuidadoSanitarioDTO) {
        Familia familia = familiaDAO.getById(familiaIncidenciaCuidadoSanitarioDTO.getIdFamilia());
        Incidencia incidencia = incidenciaDAO.getById(familiaIncidenciaCuidadoSanitarioDTO.getIdIncidencia());

        CuidadoSanitario cuidadoSanitario = new CuidadoSanitario();
        cuidadoSanitario.setNombre(familiaIncidenciaCuidadoSanitarioDTO.getNombre());
        cuidadoSanitario.setFecha(familiaIncidenciaCuidadoSanitarioDTO.getFecha());
        cuidadoSanitario.setEstado(familiaIncidenciaCuidadoSanitarioDTO.getEstado());
        cuidadoSanitario.setFamilia(familia);
        cuidadoSanitario.setIncidencia(incidencia);

        cuidadoSanitarioDAO.save(cuidadoSanitario);
    }

    @Override
    public List<FamiliaIncidenciaCuidadoSanitarioDTO> listar(String periodo, String tipo, Integer perPage, Integer page) {
        Pageable pagination = PageRequest.of(page-1, perPage);
        List<FamiliaIncidenciaCuidadoSanitarioDTO> familiasPerPage = new ArrayList<FamiliaIncidenciaCuidadoSanitarioDTO>();
        List<Familia> familias = familiaDAO.findIncidenciaByPeriodoAndTipo(periodo, tipo, pagination);
        for (Familia familia : familias) {
            List<CuidadoSanitario> cuidados_sanitarios = cuidadoSanitarioDAO.findByIdFamiliaAndIncidencia(familia.getIdFamilia());

            for (CuidadoSanitario cuidado_sanitario : cuidados_sanitarios) {
                Incidencia incidencia = cuidado_sanitario.getIncidencia();

                FamiliaIncidenciaCuidadoSanitarioDTO familiaIncidenciaCuidadoSanitarioDTO = new FamiliaIncidenciaCuidadoSanitarioDTO();
                familiaIncidenciaCuidadoSanitarioDTO.setIdFamilia(familia.getIdFamilia());
                familiaIncidenciaCuidadoSanitarioDTO.setIdCuidadoSanitario(cuidado_sanitario.getIdCuidadoSanitario());
                familiaIncidenciaCuidadoSanitarioDTO.setIdIncidencia(incidencia.getIdIncidencia());
                familiaIncidenciaCuidadoSanitarioDTO.setNombre(cuidado_sanitario.getNombre());
                familiaIncidenciaCuidadoSanitarioDTO.setFecha(cuidado_sanitario.getFecha());
                familiaIncidenciaCuidadoSanitarioDTO.setEstado(cuidado_sanitario.getEstado());
                familiaIncidenciaCuidadoSanitarioDTO.setFechaRegistro(incidencia.getFechaRegistro());
                familiaIncidenciaCuidadoSanitarioDTO.setNombreAnimal(incidencia.getNombreAnimal());
                familiaIncidenciaCuidadoSanitarioDTO.setCantidadAnimales(incidencia.getCantidadAnimales());
                familiaIncidenciaCuidadoSanitarioDTO.setGravedadIncidencia(incidencia.getGravedadIncidencia());
                familiaIncidenciaCuidadoSanitarioDTO.setObservacion(incidencia.getObservacion());
                familiaIncidenciaCuidadoSanitarioDTO.setResultado(incidencia.getResultado());

                familiasPerPage.add(familiaIncidenciaCuidadoSanitarioDTO);
            }
        }
        return familiasPerPage;
    }

    @Override
    public void actualizar(FamiliaIncidenciaCuidadoSanitarioDTO familiaIncidenciaCuidadoSanitarioDTO) {
        Familia familia = familiaDAO.getById(familiaIncidenciaCuidadoSanitarioDTO.getIdFamilia());
        Incidencia incidencia = incidenciaDAO.getById(familiaIncidenciaCuidadoSanitarioDTO.getIdIncidencia());
        CuidadoSanitario cuidadoSanitario = cuidadoSanitarioDAO.getById(familiaIncidenciaCuidadoSanitarioDTO.getIdCuidadoSanitario());

        incidencia.setFechaRegistro(familiaIncidenciaCuidadoSanitarioDTO.getFechaRegistro());
        incidencia.setNombreAnimal(familiaIncidenciaCuidadoSanitarioDTO.getNombreAnimal());
        incidencia.setCantidadAnimales(familiaIncidenciaCuidadoSanitarioDTO.getCantidadAnimales());
        incidencia.setGravedadIncidencia(familiaIncidenciaCuidadoSanitarioDTO.getGravedadIncidencia());
        incidencia.setObservacion(familiaIncidenciaCuidadoSanitarioDTO.getObservacion());
        incidencia.setResultado(familiaIncidenciaCuidadoSanitarioDTO.getResultado());
        incidencia.setFamilia(familia);

        incidenciaDAO.save(incidencia);

        cuidadoSanitario.setNombre(familiaIncidenciaCuidadoSanitarioDTO.getNombre());
        cuidadoSanitario.setFecha(familiaIncidenciaCuidadoSanitarioDTO.getFecha());
        cuidadoSanitario.setEstado(familiaIncidenciaCuidadoSanitarioDTO.getEstado());
        cuidadoSanitario.setFamilia(familia);
        cuidadoSanitario.setIncidencia(incidencia);

        cuidadoSanitarioDAO.save(cuidadoSanitario);
    }

    @Override
    public CuidadoSanitario validar(Long idFamilia) {
        List<CuidadoSanitario> lista = cuidadoSanitarioDAO.findByIdFamiliaAndIncidencia(idFamilia);

        if (lista != null && !lista.isEmpty()) {
            return lista.get(0);
        }

        return null;
    }
}
