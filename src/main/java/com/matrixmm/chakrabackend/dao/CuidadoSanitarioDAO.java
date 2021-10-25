package com.matrixmm.chakrabackend.dao;

import com.matrixmm.chakrabackend.model.Alimentacion;
import com.matrixmm.chakrabackend.model.CuidadoSanitario;
import com.matrixmm.chakrabackend.model.Pastoreo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CuidadoSanitarioDAO extends JpaRepository<CuidadoSanitario,Long> {
    @Query("SELECT c FROM CuidadoSanitario c WHERE c.familia.idFamilia=:id_familia AND c.nombre=:nombre AND c.fecha=:fecha")
    public List<CuidadoSanitario> existeFamiliaCuidadoSanitario(@Param("id_familia") Long idFamilia, @Param("nombre") String nombre, @Param("fecha") LocalDate fecha);

    @Query("SELECT c FROM CuidadoSanitario c WHERE c.familia.idFamilia=:id_familia")
    public List<CuidadoSanitario> findByIdFamilia(@Param("id_familia") Long idFamilia);

    @Query("SELECT c FROM CuidadoSanitario c WHERE c.familia.idFamilia=:id_familia AND c.incidencia.idIncidencia IS NOT NULL")
    public List<CuidadoSanitario> findByIdFamiliaAndIncidencia(@Param("id_familia") Long idFamilia);

    @Query("SELECT c FROM CuidadoSanitario c WHERE c.familia.idFamilia=:id_familia AND c.incidencia.idIncidencia=:id_incidencia")
    public List<CuidadoSanitario> existeFamiliaIncidenciaCuidadoSanitario(@Param("id_familia") Long idFamilia, @Param("id_incidencia") Long idIncidencia);
}
