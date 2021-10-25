package com.matrixmm.chakrabackend.dao;

import com.matrixmm.chakrabackend.model.CuidadoSanitario;
import com.matrixmm.chakrabackend.model.Incidencia;
import com.matrixmm.chakrabackend.model.Pastoreo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface IncidenciaDAO extends JpaRepository<Incidencia,Long> {
    @Query("SELECT i FROM Incidencia i WHERE i.familia.idFamilia=:id_familia AND i.fechaRegistro=:fecha_registro")
    public List<Incidencia> existeFamiliaIncidencia(@Param("id_familia") Long idFamilia, @Param("fecha_registro") LocalDate fechaRegistro);

    @Query("SELECT i FROM Incidencia i WHERE i.familia.idFamilia=:id_familia")
    public List<Incidencia> findByIdFamilia(@Param("id_familia") Long idFamilia);
}
