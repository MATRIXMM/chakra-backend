package com.matrixmm.chakrabackend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="seguridad")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Seguridad {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id_seguridad")
    private long idSeguridad;

    @Column(name="username")
    private String username;

    @Column(name="password")
    private String password;

    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name= "id_usuario")
    private Usuario usuario;

}
