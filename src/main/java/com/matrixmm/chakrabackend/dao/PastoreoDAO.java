package com.matrixmm.chakrabackend.dao;

import com.matrixmm.chakrabackend.model.Alimentacion;
import com.matrixmm.chakrabackend.model.Familia;
import com.matrixmm.chakrabackend.model.Horario;
import com.matrixmm.chakrabackend.model.Pastoreo;
import org.springframework.cglib.util.ParallelSorter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.Past;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface PastoreoDAO extends JpaRepository<Pastoreo,Long> {
    @Query("SELECT DISTINCT p FROM Pastoreo p WHERE p.familia.idFamilia=:id_familia AND p.tiempo=:tiempo AND p.horario=:horario AND p.dia=:dia")
    public List<Pastoreo> existeFamiliaPastoreo(@Param("id_familia") Long idFamilia, @Param("tiempo") Integer tiempo, @Param("horario") LocalDateTime horario, @Param("dia") String dia);

    @Query("SELECT DISTINCT p FROM Pastoreo p WHERE p.familia.idFamilia=:id_familia")
    public List<Pastoreo> findByIdFamilia(@Param("id_familia") Long idFamilia);

    @Query("SELECT DISTINCT p FROM Pastoreo p WHERE (p.horario BETWEEN :fecha_inicio AND :fecha_fin) ORDER BY p.idPastoreo ASC")
    public List<Pastoreo> findByFechas(@Param("fecha_inicio") LocalDateTime fechaInicio, @Param("fecha_fin") LocalDateTime fechaFin);
}
