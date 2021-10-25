package com.matrixmm.chakrabackend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name="animal")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Animal {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="id_animal")
    private Long idAnimal;

    @Column(name="tipo")
    private String tipo;

    @Column(name="cantidad")
    private Integer cantidad;

    @Column(name="caracteristica", length=20)
    private String caracteristica;

    @ManyToOne
    @JoinColumn(name="id_familia")
    private Familia familia;
}
