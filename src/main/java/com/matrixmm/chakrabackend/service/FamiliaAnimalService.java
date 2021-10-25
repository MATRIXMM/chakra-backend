package com.matrixmm.chakrabackend.service;

import com.matrixmm.chakrabackend.dto.FamiliaAnimalDTO;
import com.matrixmm.chakrabackend.model.Alimentacion;
import com.matrixmm.chakrabackend.model.Familia;

import java.util.List;

public interface FamiliaAnimalService {
    Boolean existeFamiliaAnimal(FamiliaAnimalDTO familiaDTO);
    List<FamiliaAnimalDTO> listar (String periodo, String tipo, Integer perPage, Integer page);
    void crear(FamiliaAnimalDTO familiaAnimalDTO);
    Familia validar(Long idFamilia);
}
