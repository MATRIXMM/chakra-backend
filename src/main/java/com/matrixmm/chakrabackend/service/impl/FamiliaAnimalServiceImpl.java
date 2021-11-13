package com.matrixmm.chakrabackend.service.impl;

import com.matrixmm.chakrabackend.dao.AnimalDAO;
import com.matrixmm.chakrabackend.dao.FamiliaDAO;
import com.matrixmm.chakrabackend.dto.FamiliaAnimalDTO;
import com.matrixmm.chakrabackend.dto.FamiliaIncidenciaDTO;
import com.matrixmm.chakrabackend.model.Alimentacion;
import com.matrixmm.chakrabackend.model.Animal;
import com.matrixmm.chakrabackend.model.Familia;
import com.matrixmm.chakrabackend.model.Incidencia;
import com.matrixmm.chakrabackend.service.FamiliaAnimalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FamiliaAnimalServiceImpl implements FamiliaAnimalService {
    @Autowired
    FamiliaDAO familiaDAO;

    @Autowired
    AnimalDAO animalDAO;

    @Override
    public void crear(FamiliaAnimalDTO familiaAnimalDTO) {
        Familia familia = new Familia();
        familia.setNombre(familiaAnimalDTO.getNombre());
        familia.setPeriodo(familiaAnimalDTO.getPeriodo());
        familia.setFechaRegistro(familiaAnimalDTO.getFechaRegistro());

        Animal animal = new Animal();
        animal.setTipo(familiaAnimalDTO.getTipo());
        animal.setCantidad(familiaAnimalDTO.getCantidad());
        animal.setCaracteristica(familiaAnimalDTO.getCaracteristica());
        animal.setFamilia(familia);

        familiaDAO.save(familia);

        animalDAO.save(animal);
    }

    @Override
    public Boolean existeFamiliaAnimal(FamiliaAnimalDTO familiaAnimalDTO) {
        Integer idFamilia = animalDAO.existeFamiliaAnimal(familiaAnimalDTO.getNombre(), familiaAnimalDTO.getPeriodo(), familiaAnimalDTO.getFechaRegistro(), familiaAnimalDTO.getTipo(), familiaAnimalDTO.getCantidad(), familiaAnimalDTO.getCaracteristica());

        if (idFamilia == null) {
            return false;
        }

        return true;
    }

    @Override
    public Boolean existeFamiliaAnimalById(Long idFamilia) {
        return familiaDAO.existsById(idFamilia);
    }

    @Override
    public List<FamiliaAnimalDTO> listar(String periodo, String tipo, Integer perPage, Integer page) {
        Pageable pagination = PageRequest.of(page-1, perPage);
        List<FamiliaAnimalDTO> familiasPerPage = new ArrayList<FamiliaAnimalDTO>();
        List<Familia> familias = familiaDAO.findByPeriodoAndTipo(periodo, tipo, pagination);
        for (Familia familia : familias) {
            List<Animal> animales = animalDAO.findByIdFamilia(familia.getIdFamilia());

            for (Animal animal : animales) {
                FamiliaAnimalDTO familiaAnimalDTO = new FamiliaAnimalDTO();
                familiaAnimalDTO.setIdFamilia(familia.getIdFamilia());
                familiaAnimalDTO.setNombre(familia.getNombre());
                familiaAnimalDTO.setPeriodo(familia.getPeriodo());
                familiaAnimalDTO.setFechaRegistro(familia.getFechaRegistro());
                familiaAnimalDTO.setIdAnimal(animal.getIdAnimal());
                familiaAnimalDTO.setTipo(animal.getTipo());
                familiaAnimalDTO.setCantidad(animal.getCantidad());
                familiaAnimalDTO.setCaracteristica(animal.getCaracteristica());
                familiasPerPage.add(familiaAnimalDTO);
            }
        }
        return familiasPerPage;
    }

    @Override
    public void actualizar(FamiliaAnimalDTO familiaAnimalDTO) {
        Familia familia = familiaDAO.getById(familiaAnimalDTO.getIdFamilia());
        Animal animal = animalDAO.getById(familiaAnimalDTO.getIdAnimal());

        familia.setNombre(familiaAnimalDTO.getNombre());
        familia.setPeriodo(familiaAnimalDTO.getPeriodo());
        familia.setFechaRegistro(familiaAnimalDTO.getFechaRegistro());

        familiaDAO.save(familia);

        animal.setTipo(familiaAnimalDTO.getTipo());
        animal.setCantidad(familiaAnimalDTO.getCantidad());
        animal.setCaracteristica(familiaAnimalDTO.getCaracteristica());

        animalDAO.save(animal);
    }

    @Override
    public FamiliaAnimalDTO validar(Long idFamilia) {
        List<Familia> lista = familiaDAO.findByIdFamilia(idFamilia);

        if (lista != null && !lista.isEmpty()) {
            FamiliaAnimalDTO familiaAnimalDTO = new FamiliaAnimalDTO();

            Familia familia = lista.get(0);

            familiaAnimalDTO.setIdFamilia(familia.getIdFamilia());
            familiaAnimalDTO.setNombre(familia.getNombre());
            familiaAnimalDTO.setPeriodo(familia.getPeriodo());
            familiaAnimalDTO.setFechaRegistro(familia.getFechaRegistro());

            List<Animal> animales = animalDAO.findByIdFamilia(familia.getIdFamilia());

            for (Animal animal : animales) {
                familiaAnimalDTO.setIdAnimal(animal.getIdAnimal());
                familiaAnimalDTO.setTipo(animal.getTipo());
                familiaAnimalDTO.setCantidad(animal.getCantidad());
                familiaAnimalDTO.setCaracteristica(animal.getCaracteristica());
            }

            return familiaAnimalDTO;
        }

        return null;
    }
}
