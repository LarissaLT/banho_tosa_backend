package br.com.atos.larissa.banho_tosa_api.mapper;

import br.com.atos.larissa.banho_tosa_api.dto.PagamentoDto;
import br.com.atos.larissa.banho_tosa_api.model.Pagamento;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PagamentoMapper {

    public PagamentoDto toDto(Pagamento pagamento) {
        PagamentoDto dto = new PagamentoDto(
                LocalDateTime.now(),
                pagamento.getValor(),
                pagamento.getData());
        return dto;
    }

    public Pagamento toEntity(PagamentoDto dto){
        Pagamento entity = new Pagamento();
        entity.setValor(dto.valor());
        entity.setData(dto.data());
        return entity;
    }

    public List<PagamentoDto> toDto(List<Pagamento> entities){
        List<PagamentoDto> resultado = entities.stream().map(e -> toDto(e)).collect(Collectors.toList());
        return resultado;
    }

    public List<Pagamento> toEntity(List<PagamentoDto> dtos){
        List<Pagamento> resultado = dtos.stream().map(e -> toEntity(e)).collect(Collectors.toList());
        return resultado;
    }
}
