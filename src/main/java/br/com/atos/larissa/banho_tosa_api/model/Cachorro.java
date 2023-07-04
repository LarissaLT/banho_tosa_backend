package br.com.atos.larissa.banho_tosa_api.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Cachorro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String raca;
    private int idade;
    @Enumerated(EnumType.STRING)
    private Porte porte;
    @Enumerated(EnumType.STRING)
    private Genero genero;
    private LocalDateTime deletedAt;

    @ManyToOne
    @JoinColumn(name = "tutor_id")
    private Tutor tutor;

}
