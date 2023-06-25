package br.com.atos.larissa.banho_tosa_api.dto;

import br.com.atos.larissa.banho_tosa_api.model.Cachorro;

import java.util.List;

public record TutorDto(
        Long id,
        String nome,
        String celular,
        String email,
        String senha,
        String endereco,
        List<Cachorro> cachorros){

}
