package com.matrixmm.chakrabackend.dao;

import com.matrixmm.chakrabackend.model.Animal;
import com.matrixmm.chakrabackend.model.Familia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@Repository
public interface AnimalDAO extends JpaRepository<Animal,Long> {
    @Query("SELECT f.idFamilia FROM Animal a INNER JOIN Familia f ON a.familia.idFamilia=f.idFamilia AND f.nombre=:nombre AND f.periodo=:periodo AND f.fechaRegistro=:fecha_registro AND a.tipo=:tipo AND a.cantidad=:cantidad AND a.caracteristica=:caracteristica")
    public Integer existeFamiliaAnimal(@Param("nombre") String nombre, @Param("periodo") String periodo, @Param("fecha_registro") LocalDate fechaRegistro, @Param("tipo") String tipo, @Param("cantidad") Integer cantidad, @Param("caracteristica") String caracteristica);

    @Query("SELECT DISTINCT a FROM Animal a WHERE a.familia.idFamilia=:id_familia")
    public List<Animal> findByIdFamilia(@Param("id_familia") Long idFamilia);
}
