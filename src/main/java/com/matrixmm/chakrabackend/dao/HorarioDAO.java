package com.matrixmm.chakrabackend.dao;

import com.matrixmm.chakrabackend.model.Alimentacion;
import com.matrixmm.chakrabackend.model.Familia;
import com.matrixmm.chakrabackend.model.Horario;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface HorarioDAO extends JpaRepository<Horario,Long> {
    @Query("SELECT DISTINCT h FROM Horario h WHERE h.alimentacion.idAlimentacion=:id_alimentacion")
    public List<Horario> findByIdAlimentacion(@Param("id_alimentacion") Long idAlimentacion);

    @Query("SELECT DISTINCT h FROM Alimentacion a INNER JOIN Familia f ON a.familia.idFamilia=f.idFamilia AND a.incidencia.idIncidencia IS NULL INNER JOIN Horario h ON h.alimentacion.idAlimentacion=a.idAlimentacion AND (h.horario BETWEEN :fecha_inicio AND :fecha_fin) ORDER BY h.idHorario ASC")
    public List<Horario> findByFechas(@Param("fecha_inicio") LocalDateTime fechaInicio, @Param("fecha_fin") LocalDateTime fechaFin);

    @Query("SELECT DISTINCT h FROM Alimentacion a INNER JOIN Familia f ON a.familia.idFamilia=f.idFamilia AND a.incidencia.idIncidencia IS NOT NULL INNER JOIN Horario h ON h.alimentacion.idAlimentacion=a.idAlimentacion AND (h.horario BETWEEN :fecha_inicio AND :fecha_fin) ORDER BY h.idHorario ASC")
    public List<Horario> findIncidenciaByFechas(@Param("fecha_inicio") LocalDateTime fechaInicio, @Param("fecha_fin") LocalDateTime fechaFin);
}
