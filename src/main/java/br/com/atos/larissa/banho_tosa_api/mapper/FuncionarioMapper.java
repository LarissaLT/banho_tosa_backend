package br.com.atos.larissa.banho_tosa_api.mapper;

import br.com.atos.larissa.banho_tosa_api.dto.FuncionarioDto;
import br.com.atos.larissa.banho_tosa_api.model.Funcionario;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FuncionarioMapper {

    public FuncionarioDto toDto(Funcionario funcionario) {
        FuncionarioDto dto = new FuncionarioDto(
                funcionario.getId(),
                funcionario.getNome());
        return dto;
    }

    public Funcionario toEntity(FuncionarioDto dto){
        Funcionario entity = new Funcionario();
        entity.setNome(dto.nome());
        return entity;
    }

    public List<FuncionarioDto> toDto(List<Funcionario> entities){
        List<FuncionarioDto> resultado = entities.stream().map(e -> toDto(e)).collect(Collectors.toList());
        return resultado;
    }

    public List<Funcionario> toEntity(List<FuncionarioDto> dtos){
        List<Funcionario> resultado = dtos.stream().map(e -> toEntity(e)).collect(Collectors.toList());
        return resultado;
    }
}
