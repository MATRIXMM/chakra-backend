package com.matrixmm.chakrabackend.service;

import com.matrixmm.chakrabackend.dto.FamiliaAnimalDTO;
import com.matrixmm.chakrabackend.dto.FamiliaIncidenciaDTO;
import com.matrixmm.chakrabackend.model.Alimentacion;
import com.matrixmm.chakrabackend.model.Familia;

import java.util.List;

public interface FamiliaAnimalService {
    Boolean existeFamiliaAnimal(FamiliaAnimalDTO familiaDTO);
    Boolean existeFamiliaAnimalById(Long idFamilia);
    List<FamiliaAnimalDTO> listar (String periodo, String tipo, Integer perPage, Integer page);
    void crear(FamiliaAnimalDTO familiaAnimalDTO);
    void actualizar(FamiliaAnimalDTO familiaAnimalDTO);
    FamiliaAnimalDTO validar(Long idFamilia);
}
