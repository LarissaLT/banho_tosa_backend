package br.com.atos.larissa.banho_tosa_api.service;

import br.com.atos.larissa.banho_tosa_api.dto.CachorroDto;
import br.com.atos.larissa.banho_tosa_api.mapper.CachorroMapper;
import br.com.atos.larissa.banho_tosa_api.model.Cachorro;
import br.com.atos.larissa.banho_tosa_api.repository.CachorroRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service("cachorroApi")
public class CachorroService {

    private final CachorroRepository repository;
    private final CachorroMapper mapper;

    public CachorroService(CachorroRepository repository, CachorroMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public CachorroDto cadastrar (CachorroDto dados){
        Cachorro cachorro = mapper.toEntity(dados);
        repository.save(cachorro);
        CachorroDto dto = mapper.toDto(cachorro);
        return dto;
    }

    public List<CachorroDto> listar(){
        List<Cachorro> cachorros = repository.findAllByDeletedAtIsNull();
        List<CachorroDto> dtos = mapper.toDto(cachorros);
        return dtos;
    }

    public CachorroDto buscar(Long id){
        Optional<Cachorro> op = repository.findByIdAndDeletedAtIsNull(id);
        Cachorro cachorro = op.orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Busca não encontrada"));
        CachorroDto dto = mapper.toDto(cachorro);
        return dto;
    }

    public CachorroDto atualizar(CachorroDto dados, Long id){
        if(!repository.existsById(id)){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Não foi possível atualizar o cachorro");
        }
        Cachorro cachorro = mapper.toEntity(dados);
        cachorro.setId(id);
        repository.save(cachorro);
        CachorroDto dto = mapper.toDto(cachorro);
        return dto;
    }

    public void deletar(Long id){
        if(!repository.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cachorro não encontrado");
        }
        repository.softDeleteById(id);
    }
}
