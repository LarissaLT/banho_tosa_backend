package br.com.atos.larissa.banho_tosa_api.dto;


import br.com.atos.larissa.banho_tosa_api.model.*;

import java.time.LocalDate;

public record AgendamentoDto(
        Long id,
        LocalDate data,
        Servico servico,
        Funcionario funcionario,
        Cachorro cachorro,
        TutorDto tutor,
        Pagamento pagamento){
}
