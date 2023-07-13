package br.com.atos.larissa.banho_tosa_api.mapper;

import br.com.atos.larissa.banho_tosa_api.dto.AgendamentoDto;
import br.com.atos.larissa.banho_tosa_api.dto.TutorDto;
import br.com.atos.larissa.banho_tosa_api.model.Agendamento;
import br.com.atos.larissa.banho_tosa_api.model.Cachorro;
import br.com.atos.larissa.banho_tosa_api.model.Tutor;
import br.com.atos.larissa.banho_tosa_api.repository.TutorRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AgendamentoMapper {

    private final CachorroMapper cachorroMapper;
    private final TutorRepository tutorRepository;

    public AgendamentoMapper(CachorroMapper cachorroMapper,
                             TutorRepository tutorRepository) {
        this.cachorroMapper = cachorroMapper;
        this.tutorRepository = tutorRepository;
    }

    public AgendamentoDto toDto(Agendamento agendamento) {
        TutorDto tutor = null;
        if (agendamento.getTutor() != null) {
            List<Cachorro> cachorros  = agendamento.getTutor().getCachorros();
          tutor =  new TutorDto(agendamento.getTutor().getId(), agendamento.getTutor().getNome(), agendamento.getTutor().getTelefone(), agendamento.getTutor().getEmail(), agendamento.getTutor().getSenha(), agendamento.getTutor().getEndereco(), agendamento.getTutor().getRole(), cachorroMapper.toDto(cachorros));
        }
        AgendamentoDto dto = new AgendamentoDto(
                agendamento.getId(),
                agendamento.getData(),
                agendamento.getServico(),
                agendamento.getFuncionario(),
                agendamento.getCachorro(),
                tutor);
        return dto;
    }

    public Agendamento toEntity(AgendamentoDto dto) {
        Agendamento entity = new Agendamento();
        entity.setServico(dto.servico());
        entity.setFuncionario(dto.funcionario());
        entity.setCachorro(dto.cachorro());
        if (dto.tutor() != null) {
            entity.setTutor(Tutor.builder().email(dto.tutor().email()).id(dto.tutor().id()).nome(dto.tutor().nome()).telefone(dto.tutor().celular()).endereco(dto.tutor().endereco()).cachorros(cachorroMapper.toEntity(dto.tutor().cachorros())).build());
        }
        entity.setData(dto.data());
        return entity;
    }

    public List<AgendamentoDto> toDto(List<Agendamento> entities) {
        List<AgendamentoDto> resultado = entities.stream().map(e -> toDto(e)).collect(Collectors.toList());
        return resultado;
    }

    public List<Agendamento> toEntity(List<AgendamentoDto> dtos) {
        List<Agendamento> resultado = dtos.stream().map(e -> toEntity(e)).collect(Collectors.toList());
        return resultado;
    }
}