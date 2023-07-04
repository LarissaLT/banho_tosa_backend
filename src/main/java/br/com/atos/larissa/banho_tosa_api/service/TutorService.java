package br.com.atos.larissa.banho_tosa_api.service;

import br.com.atos.larissa.banho_tosa_api.dto.AgendamentoDto;
import br.com.atos.larissa.banho_tosa_api.dto.TutorDto;
import br.com.atos.larissa.banho_tosa_api.mapper.TutorMapper;
import br.com.atos.larissa.banho_tosa_api.model.Agendamento;
import br.com.atos.larissa.banho_tosa_api.model.RoleEnum;
import br.com.atos.larissa.banho_tosa_api.model.Tutor;
import br.com.atos.larissa.banho_tosa_api.repository.TutorRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service("tutorApi")
public class TutorService implements UserDetailsService {

    private final TutorRepository repository;
    private final TutorMapper mapper;

    public TutorService(TutorRepository repository, TutorMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public UserDetails loadUserByUsername(String tutor) {
        return repository.findByEmail(tutor)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    static Tutor getUsuarioLogado() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Tutor usuarioLogado = (Tutor) authentication.getPrincipal();
        return usuarioLogado;
    }

    public TutorDto cadastrar(TutorDto dados){
        Tutor tutor = mapper.toEntity(dados);
        tutor.setSenha("123456");
            repository.save(tutor);
        TutorDto dto = mapper.toDto(tutor);
        return dto;
    }

    public List<TutorDto> listar(){
        List<Tutor> tutores = repository.findAllByDeletedAtIsNull();
        List<TutorDto> dtos = mapper.toDto(tutores);
        return dtos;
    }

    public TutorDto buscar(Long id){
        Optional<Tutor> op = repository.findByIdAndDeletedAtIsNull(id);
        Tutor tutor = op.orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Busca não encontrada"));
        TutorDto dto = mapper.toDto(tutor);
        return dto;
    }

    public TutorDto atualizar(TutorDto dados, Long id){
        Tutor usuarioLogado = getUsuarioLogado();
        Tutor tutor = repository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Tutor não encontrado"));

        if (RoleEnum.USER.equals(usuarioLogado.getRole())) {
            if (!tutor.equals(usuarioLogado)) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Não autorizado a atualizar o tutor");
            }
        }
        repository.save(tutor);
        TutorDto dto = mapper.toDto(tutor);
        return dto;
    }

    public void deletar(Long id){
        if(!repository.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Tutor não encontrado");
        }
        repository.softDeleteById(id);
    }
}