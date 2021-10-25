package com.matrixmm.chakrabackend.dao;

import com.matrixmm.chakrabackend.model.Alimentacion;
import com.matrixmm.chakrabackend.model.Horario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HorarioDAO extends JpaRepository<Horario,Long> {
    @Query("SELECT h FROM Horario h WHERE h.alimentacion.idAlimentacion=:id_alimentacion")
    public List<Horario> findByIdAlimentacion(@Param("id_alimentacion") Long idAlimentacion);
}
