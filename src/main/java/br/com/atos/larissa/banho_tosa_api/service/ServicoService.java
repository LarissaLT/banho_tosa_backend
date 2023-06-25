package br.com.atos.larissa.banho_tosa_api.service;

import br.com.atos.larissa.banho_tosa_api.dto.ServicoDto;
import br.com.atos.larissa.banho_tosa_api.mapper.ServicoMapper;
import br.com.atos.larissa.banho_tosa_api.model.Servico;
import br.com.atos.larissa.banho_tosa_api.repository.ServicoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service("servicoApi")
public class ServicoService {

    private final ServicoRepository repository;
    private final ServicoMapper mapper;

    public ServicoService(ServicoRepository repository, ServicoMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public ServicoDto cadastrar(ServicoDto dados){
        Servico servico = mapper.toEntity(dados);
        repository.save(servico);
        ServicoDto dto = mapper.toDto(servico);
        return dto;
    }

    public List<ServicoDto> listar(){
        List<Servico> servicos = repository.findAll();
        List<ServicoDto> dtos = mapper.toDto(servicos);
        return dtos;
    }

    public ServicoDto buscar(Long id){
        Optional<Servico> op = repository.findById(id);
        Servico servico = op.orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Busca não encontrada"));
        ServicoDto dto = mapper.toDto(servico);
        return dto;
    }

    public ServicoDto atualizar(ServicoDto dados, Long id){
        if(!repository.existsById(id)){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Não foi possível atualizar o Servico");
        }
        Servico servico = mapper.toEntity(dados);
        servico.setId(id);
        repository.save(servico);
        ServicoDto dto = mapper.toDto(servico);
        return dto;
    }

    public void deletar(Long id){
        if(!repository.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Servico não encontrado");
        }
        repository.deleteById(id);
    }
}