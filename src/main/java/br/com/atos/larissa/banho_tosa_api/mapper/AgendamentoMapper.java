package br.com.atos.larissa.banho_tosa_api.mapper;

import br.com.atos.larissa.banho_tosa_api.dto.AgendamentoDto;
import br.com.atos.larissa.banho_tosa_api.model.Agendamento;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AgendamentoMapper {

    public AgendamentoDto toDto(Agendamento agendamento) {
        AgendamentoDto dto = new AgendamentoDto(
                agendamento.getId(),
                agendamento.getData(),
                agendamento.getServico(),
                agendamento.getFuncionario(),
                agendamento.getCachorro(),
                agendamento.getTutor(),
                agendamento.getPagamento());
        return dto;
    }

    public Agendamento toEntity(AgendamentoDto dto){
        Agendamento entity = new Agendamento();
        entity.setServico(dto.servico());
        entity.setFuncionario(dto.funcionario());
        entity.setCachorro(dto.cachorro());
        entity.setTutor(dto.tutor());
        entity.setPagamento(dto.pagamento());
        return entity;
    }

    public List<AgendamentoDto> toDto(List<Agendamento> entities){
        List<AgendamentoDto> resultado = entities.stream().map(e -> toDto(e)).collect(Collectors.toList());
        return resultado;
    }

    public List<Agendamento> toEntity(List<AgendamentoDto> dtos){
        List<Agendamento> resultado = dtos.stream().map(e -> toEntity(e)).collect(Collectors.toList());
        return resultado;
    }
}
