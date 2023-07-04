package br.com.atos.larissa.banho_tosa_api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
