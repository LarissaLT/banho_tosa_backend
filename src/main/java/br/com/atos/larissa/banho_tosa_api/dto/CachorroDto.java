package br.com.atos.larissa.banho_tosa_api.dto;

import br.com.atos.larissa.banho_tosa_api.model.Genero;
import br.com.atos.larissa.banho_tosa_api.model.Porte;

public record CachorroDto(
        Long id,
        String nome,
        String raca,
        int idade,
        Porte porte,
        Genero genero){

}
