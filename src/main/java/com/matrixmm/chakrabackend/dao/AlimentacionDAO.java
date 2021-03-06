package com.matrixmm.chakrabackend.dao;

import com.matrixmm.chakrabackend.model.Alimentacion;
import com.matrixmm.chakrabackend.model.Animal;
import com.matrixmm.chakrabackend.model.Familia;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AlimentacionDAO extends JpaRepository<Alimentacion,Long> {
    @Query("SELECT DISTINCT a FROM Alimentacion a WHERE a.familia.idFamilia=:id_familia AND a.tipo=:tipo AND a.cantidad=:cantidad")
    public List<Alimentacion> existeFamiliaAlimentacion(@Param("id_familia") Long idFamilia, @Param("tipo") String tipo, @Param("cantidad") Integer cantidad);

    @Query("SELECT DISTINCT a FROM Alimentacion a WHERE a.familia.idFamilia=:id_familia AND a.incidencia.idIncidencia IS NULL")
    public List<Alimentacion> findByIdFamilia(@Param("id_familia") Long idFamilia);

    @Query("SELECT DISTINCT a FROM Alimentacion a WHERE a.familia.idFamilia=:id_familia AND a.incidencia.idIncidencia IS NOT NULL")
    public List<Alimentacion> findByIdFamiliaAndIncidencia(@Param("id_familia") Long idFamilia);

    @Query("SELECT DISTINCT a FROM Alimentacion a WHERE a.incidencia.idIncidencia IS NOT NULL AND a.incidencia.idIncidencia=:id_incidencia")
    public List<Alimentacion> findByIdIncidencia(@Param("id_incidencia") Long idIncidencia);

    @Query("SELECT DISTINCT a FROM Alimentacion a WHERE a.familia.idFamilia=:id_familia AND a.incidencia.idIncidencia=:id_incidencia")
    public List<Alimentacion> findByIdFamiliaAndIdIncidencia(@Param("id_familia") Long idFamilia, @Param("id_incidencia") Long idIncidencia);

    @Query("SELECT DISTINCT a FROM Alimentacion a WHERE a.familia.idFamilia=:id_familia AND a.incidencia.idIncidencia=:id_incidencia")
    public List<Alimentacion> existeFamiliaIncidenciaAlimentacion(@Param("id_familia") Long idFamilia, @Param("id_incidencia") Long idIncidencia);
}
