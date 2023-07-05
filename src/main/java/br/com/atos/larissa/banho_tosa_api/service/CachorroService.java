package br.com.atos.larissa.banho_tosa_api.service;

import br.com.atos.larissa.banho_tosa_api.dto.CachorroDto;
import br.com.atos.larissa.banho_tosa_api.mapper.CachorroMapper;
import br.com.atos.larissa.banho_tosa_api.model.Cachorro;
import br.com.atos.larissa.banho_tosa_api.model.RoleEnum;
import br.com.atos.larissa.banho_tosa_api.model.Tutor;
import br.com.atos.larissa.banho_tosa_api.repository.CachorroRepository;
import br.com.atos.larissa.banho_tosa_api.repository.TutorRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service("cachorroApi")
public class CachorroService {

    private final CachorroRepository repository;
    private final TutorRepository tutorRepository;
    private final CachorroMapper mapper;

    public CachorroService(CachorroRepository repository, TutorRepository tutorRepository, CachorroMapper mapper) {
        this.repository = repository;
        this.tutorRepository = tutorRepository;
        this.mapper = mapper;
    }

    public CachorroDto cadastrar(CachorroDto dados) {
        Tutor usuarioLogado = TutorService.getUsuarioLogado();
        Cachorro cachorro = mapper.toEntity(dados);
        if (RoleEnum.USER.equals(usuarioLogado.getRole())) {
            repository.save(cachorro);
            usuarioLogado.getCachorros().add(cachorro);
            tutorRepository.save(usuarioLogado);
        }
        CachorroDto dto = mapper.toDto(cachorro);
        return dto;
    }

    public List<CachorroDto> listar() {
        Tutor usuarioLogado = TutorService.getUsuarioLogado();
        List<Cachorro> cachorros;
        if (RoleEnum.USER.equals(usuarioLogado.getRole())) {
            cachorros = repository.findByTutorId(usuarioLogado.getId());
        } else {
            cachorros = repository.findAllByDeletedAtIsNull();
        }
        List<CachorroDto> dtos = mapper.toDto(cachorros);
        return dtos;
    }

    public CachorroDto buscar(Long id) {
        Cachorro cachorro = repository.findByIdAndDeletedAtIsNull(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Busca não encontrada"));
        CachorroDto dto = mapper.toDto(cachorro);
        return dto;
    }

    public CachorroDto atualizar(CachorroDto dados, Long id) {
        Tutor usuarioLogado = TutorService.getUsuarioLogado();
        Cachorro cachorro = repository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Cachorro não encontrado"));

        if (RoleEnum.USER.equals(usuarioLogado.getRole())) {
            repository.findByIdAndTutorId(id, usuarioLogado.getId()).orElseThrow(
                    () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Não autorizado a atualizar o cachorro")
            );
        }

        cachorro = mapper.toEntity(dados);
        cachorro.setId(id);
        repository.save(cachorro);
        CachorroDto dto = mapper.toDto(cachorro);
        return dto;
    }

    public void deletar(Long id) {
        Tutor usuarioLogado = TutorService.getUsuarioLogado();
        repository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Cachorro não encontrado"));

        if (RoleEnum.USER.equals(usuarioLogado.getRole())) {
                repository.findByIdAndTutorId(id, usuarioLogado.getId()).orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Não autorizado a atualizar o cachorro")
                );

        }
        repository.softDeleteById(id);
    }
}
