package br.com.atos.larissa.banho_tosa_api.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


public record SignUpRequest(
        String nome,
        String email,
        String senha
) {
}
