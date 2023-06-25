package br.com.atos.larissa.banho_tosa_api.dto;

public record PagamentoDto(
        java.time.LocalDateTime now, double valor,
        java.time.LocalDateTime data
){

}
