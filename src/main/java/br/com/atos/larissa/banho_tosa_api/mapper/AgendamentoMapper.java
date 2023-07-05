package br.com.atos.larissa.banho_tosa_api.mapper;

import br.com.atos.larissa.banho_tosa_api.dto.AgendamentoDto;
import br.com.atos.larissa.banho_tosa_api.dto.TutorDto;
import br.com.atos.larissa.banho_tosa_api.model.Agendamento;
import br.com.atos.larissa.banho_tosa_api.model.Cachorro;
import br.com.atos.larissa.banho_tosa_api.model.Tutor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AgendamentoMapper {

    private final CachorroMapper cachorroMapper;

    public AgendamentoMapper(CachorroMapper cachorroMapper) {
        this.cachorroMapper = cachorroMapper;
    }

    public AgendamentoDto toDto(Agendamento agendamento) {
        List<Cachorro> cachorros = agendamento.getTutor().getCachorros();
        AgendamentoDto dto = new AgendamentoDto(
                agendamento.getId(),
                agendamento.getData(),
                agendamento.getServico(),
                agendamento.getFuncionario(),
                agendamento.getCachorro(),
                new TutorDto(agendamento.getTutor().getId(), agendamento.getTutor().getNome(), agendamento.getTutor().getTelefone(), agendamento.getTutor().getEmail(), agendamento.getTutor().getSenha(), agendamento.getTutor().getEndereco(), cachorroMapper.toDto(cachorros)),
                agendamento.getPagamento());
        return dto;
    }

    public Agendamento toEntity(AgendamentoDto dto){
        Agendamento entity = new Agendamento();
        entity.setServico(dto.servico());
        entity.setFuncionario(dto.funcionario());
        entity.setCachorro(dto.cachorro());
        entity.setTutor(Tutor.builder().email(dto.tutor().email()).id(dto.tutor().id()).nome(dto.tutor().nome()).telefone(dto.tutor().celular()).endereco(dto.tutor().endereco()).cachorros(cachorroMapper.toEntity(dto.tutor().cachorros())).build());
        entity.setPagamento(dto.pagamento());
        entity.setData(dto.data());
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
