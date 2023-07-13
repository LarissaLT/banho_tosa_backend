package br.com.atos.larissa.banho_tosa_api.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Agendamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate data;
    // = LocalDate.parse("24/05/2023", DateTimeFormatter.ofPattern("dd/MM/yyyy"))

    @ManyToOne
    @JoinColumn(name = "servico_id")
    private Servico servico;

    @ManyToOne
    @JoinColumn(name = "funcionario_id")
    private Funcionario funcionario;

    @ManyToOne
    @JoinColumn(name = "cachorro_id")
    private Cachorro cachorro;

    @ManyToOne
    @JoinColumn(name = "tutor_id")
    private Tutor tutor;
}


