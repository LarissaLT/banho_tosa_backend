package br.com.atos.larissa.banho_tosa_api.dto;

import br.com.atos.larissa.banho_tosa_api.model.*;

import java.util.List;

public record AgendamentoFormDto(
        List<Servico> servicos,
        List<Funcionario> funcionarios,
        List<TutorDto> tutores,
        List<Cachorro> cachorros){
}
