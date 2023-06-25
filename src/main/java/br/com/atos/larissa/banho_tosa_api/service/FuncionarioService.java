package br.com.atos.larissa.banho_tosa_api.service;

import br.com.atos.larissa.banho_tosa_api.dto.FuncionarioDto;
import br.com.atos.larissa.banho_tosa_api.mapper.FuncionarioMapper;
import br.com.atos.larissa.banho_tosa_api.model.Funcionario;
import br.com.atos.larissa.banho_tosa_api.repository.FuncionarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service("funcionarioApi")
public class FuncionarioService {

    private final FuncionarioRepository repository;
    private final FuncionarioMapper mapper;

    public FuncionarioService(FuncionarioRepository repository, FuncionarioMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public FuncionarioDto cadastrar(FuncionarioDto dados) {
        Funcionario funcionario = mapper.toEntity(dados);
        repository.save(funcionario);
        FuncionarioDto dto = mapper.toDto(funcionario);
        return dto;
    }

    public List<FuncionarioDto> listar() {
        List<Funcionario> funcionarios = repository.findAllByDeletedAtIsNull();
        List<FuncionarioDto> dtos = mapper.toDto(funcionarios);
        return dtos;
    }

    public FuncionarioDto buscar(Long id) {
        Optional<Funcionario> op = repository.findByIdAndDeletedAtIsNull(id);
        Funcionario funcionario = op.orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Busca não encontrada"));
        FuncionarioDto dto = mapper.toDto(funcionario);
        return dto;
    }

    public FuncionarioDto atualizar(FuncionarioDto dados, Long id) {
        if (!repository.existsById(id)) {
            new ResponseStatusException(HttpStatus.CONFLICT, "Não foi possível atualizar o funcionário");
        }
        Funcionario funcionario = mapper.toEntity(dados);
        funcionario.setId(id);
        repository.save(funcionario);
        FuncionarioDto dto = mapper.toDto(funcionario);
        return dto;
    }
    public void deletar(Long id){
        if(!repository.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Funcionário não encontrado");
        }
        repository.softDeleteById(id);
    }
}


