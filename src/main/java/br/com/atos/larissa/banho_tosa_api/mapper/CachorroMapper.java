package br.com.atos.larissa.banho_tosa_api.mapper;

import br.com.atos.larissa.banho_tosa_api.dto.CachorroDto;
import br.com.atos.larissa.banho_tosa_api.model.Cachorro;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CachorroMapper {

    public CachorroDto toDto(Cachorro cachorro) {
        CachorroDto dto = new CachorroDto(
                cachorro.getId(),
                cachorro.getNome(),
                cachorro.getRaca(),
                cachorro.getIdade(),
                cachorro.getPorte(),
                cachorro.getGenero());
        return dto;
    }

    public Cachorro toEntity(CachorroDto dto){
        Cachorro entity = new Cachorro();
        entity.setId(dto.id());
        entity.setNome(dto.nome());
        entity.setRaca(dto.raca());
        entity.setIdade(dto.idade());
        entity.setPorte(dto.porte());
        entity.setGenero(dto.genero());
        return entity;
    }

    public List<CachorroDto> toDto(List<Cachorro> entities){
        List<CachorroDto> resultado = entities.stream().map(e -> toDto(e)).collect(Collectors.toList());
        return resultado;
    }

    public List<Cachorro> toEntity(List<CachorroDto> dtos){
        List<Cachorro> resultado = dtos.stream().map(e -> toEntity(e)).collect(Collectors.toList());
        return resultado;
    }
}
