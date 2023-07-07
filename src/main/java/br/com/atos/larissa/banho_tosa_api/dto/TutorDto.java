package br.com.atos.larissa.banho_tosa_api.dto;

import br.com.atos.larissa.banho_tosa_api.model.RoleEnum;

import java.util.List;

public record TutorDto(
        Long id,
        String nome,
        String celular,
        String email,
        String senha,
        String endereco,
        RoleEnum role,
        List<CachorroDto> cachorros){

}
