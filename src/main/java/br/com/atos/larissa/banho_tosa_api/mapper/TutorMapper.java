package br.com.atos.larissa.banho_tosa_api.mapper;

import br.com.atos.larissa.banho_tosa_api.dto.TutorDto;
import br.com.atos.larissa.banho_tosa_api.model.Tutor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TutorMapper {

    public TutorDto toDto(Tutor tutor) {
        TutorDto dto = new TutorDto(
                tutor.getId(),
                tutor.getNome(),
                tutor.getTelefone(),
                tutor.getEmail(),
                tutor.getSenha(),
                tutor.getEndereco(),
                tutor.getCachorros());
        return dto;
    }

    public Tutor toEntity(TutorDto dto){
        Tutor entity = new Tutor();
        entity.setNome(dto.nome());
        entity.setTelefone(dto.celular());
        entity.setEmail(dto.email());
        entity.setEmail(dto.senha());
        entity.setEndereco(dto.endereco());
        entity.setCachorros(dto.cachorros());
        return entity;
    }

    public List<TutorDto> toDto(List<Tutor> entities){
        List<TutorDto> resultado = entities.stream().map(e -> toDto(e)).collect(Collectors.toList());
        return resultado;
    }

    public List<Tutor> toEntity(List<TutorDto> dtos){
        List<Tutor> resultado = dtos.stream().map(e -> toEntity(e)).collect(Collectors.toList());
        return resultado;
    }
}
