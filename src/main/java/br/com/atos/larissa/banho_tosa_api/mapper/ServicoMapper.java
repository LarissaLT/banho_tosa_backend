package br.com.atos.larissa.banho_tosa_api.mapper;

import br.com.atos.larissa.banho_tosa_api.dto.ServicoDto;
import br.com.atos.larissa.banho_tosa_api.model.Servico;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServicoMapper {

    public ServicoDto toDto(Servico servico) {
        ServicoDto dto = new ServicoDto(
                servico.getId(),
                servico.getNome());
        return dto;
    }

    public Servico toEntity(ServicoDto dto){
        Servico entity = new Servico();
        entity.setNome(dto.nome());
        return entity;
    }

    public List<ServicoDto> toDto(List<Servico> entities){
        List<ServicoDto> resultado = entities.stream().map(e -> toDto(e)).collect(Collectors.toList());
        return resultado;
    }

    public List<Servico> toEntity(List<ServicoDto> dtos){
        List<Servico> resultado = dtos.stream().map(e -> toEntity(e)).collect(Collectors.toList());
        return resultado;
    }
}
