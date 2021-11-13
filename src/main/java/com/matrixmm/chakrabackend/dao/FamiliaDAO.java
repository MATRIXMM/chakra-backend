package com.matrixmm.chakrabackend.dao;

import com.matrixmm.chakrabackend.model.Animal;
import com.matrixmm.chakrabackend.model.Familia;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FamiliaDAO extends JpaRepository<Familia,Long> {
    Page<Familia> findByPeriodo(String periodo, Pageable pageable);

    @Query("SELECT DISTINCT f FROM Familia f WHERE f.idFamilia=:id_familia")
    public List<Familia> findByIdFamilia(@Param("id_familia") Long idFamilia);

    @Query("SELECT DISTINCT f FROM Animal a INNER JOIN Familia f ON a.familia.idFamilia=f.idFamilia AND f.periodo=:periodo AND a.tipo=:tipo ORDER BY f.idFamilia DESC")
    public List<Familia> findByPeriodoAndTipo(@Param("periodo") String periodo, @Param("tipo") String tipo, Pageable pageable);

    @Query("SELECT DISTINCT f FROM Incidencia i INNER JOIN Familia f ON i.familia.idFamilia=f.idFamilia AND i.idIncidencia IS NOT NULL AND f.periodo=:periodo ORDER BY f.idFamilia DESC")
    public List<Familia> findIncidenciaByPeriodo(@Param("periodo") String periodo, Pageable pageable);

    @Query("SELECT DISTINCT f FROM Incidencia i INNER JOIN Familia f ON i.familia.idFamilia=f.idFamilia AND i.idIncidencia IS NOT NULL AND f.periodo=:periodo INNER JOIN Animal a ON a.familia.idFamilia=f.idFamilia AND a.tipo=:tipo ORDER BY f.idFamilia DESC")
    public List<Familia> findIncidenciaByPeriodoAndTipo(@Param("periodo") String periodo, @Param("tipo") String tipo, Pageable pageable);
}
