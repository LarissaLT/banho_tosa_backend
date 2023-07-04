package br.com.atos.larissa.banho_tosa_api.service;

import br.com.atos.larissa.banho_tosa_api.dto.AgendamentoDto;
import br.com.atos.larissa.banho_tosa_api.dto.AgendamentoFormDto;
import br.com.atos.larissa.banho_tosa_api.dto.TutorDto;
import br.com.atos.larissa.banho_tosa_api.mapper.AgendamentoMapper;
import br.com.atos.larissa.banho_tosa_api.mapper.TutorMapper;
import br.com.atos.larissa.banho_tosa_api.model.*;
import br.com.atos.larissa.banho_tosa_api.repository.*;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service("agendamentoApi")
public class AgendamentoService {

    private final AgendamentoRepository repository;
    private final ServicoRepository servicoRepository;
    private final FuncionarioRepository funcionarioRepository;
    private final CachorroRepository cachorroRepository;
    private final TutorRepository tutorRepository;

    private final TutorMapper tutorMapper;
    private final AgendamentoMapper mapper;

    public AgendamentoService(AgendamentoRepository repository, ServicoRepository servicoRepository,
                              FuncionarioRepository funcionarioRepository, CachorroRepository cachorroRepository,
                              TutorRepository tutorRepository, TutorMapper tutorMapper, AgendamentoMapper mapper) {
        this.repository = repository;
        this.servicoRepository = servicoRepository;
        this.funcionarioRepository = funcionarioRepository;
        this.cachorroRepository = cachorroRepository;
        this.tutorRepository = tutorRepository;
        this.tutorMapper = tutorMapper;
        this.mapper = mapper;
    }

    public AgendamentoDto cadastrar(AgendamentoDto dados) {
        Tutor usuarioLogado = TutorService.getUsuarioLogado();
        Agendamento agendamento = mapper.toEntity(dados);
        if (RoleEnum.USER.equals(usuarioLogado.getRole())){
            agendamento.setTutor(usuarioLogado);
        } else {
            repository.save(agendamento);
        }
        AgendamentoDto dto = mapper.toDto(agendamento);
        return dto;
    }

    public List<AgendamentoDto> listar() {
        Tutor usuarioLogado = TutorService.getUsuarioLogado();
        List<Agendamento> agendamentos;
        if (RoleEnum.USER.equals(usuarioLogado.getRole())) {
            agendamentos = repository.findByTutor_Id(usuarioLogado.getId());
        } else {
            agendamentos = repository.findAll();
        }
        List<AgendamentoDto> dtos = mapper.toDto(agendamentos);
        return dtos;
    }

    public AgendamentoFormDto buscarDadosForm() {
        List<Servico> servicos = servicoRepository.findAll();
        List<Funcionario> funcionarios = funcionarioRepository.findAll();
        List<Tutor> tutores = tutorRepository.findAll();
        List<TutorDto> tutoresDto = tutorMapper.toDto(tutores);

        List<Cachorro> cachorros = cachorroRepository.findAll();

        AgendamentoFormDto agendamentoFormDto = new AgendamentoFormDto(servicos, funcionarios, tutoresDto, cachorros);
        return agendamentoFormDto;
    }

    public AgendamentoDto buscar(Long id) {
        Optional<Agendamento> op = repository.findById(id);
        Agendamento agendamento = op.orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Busca não encontrada"));
        AgendamentoDto dto = mapper.toDto(agendamento);
        return dto;
    }

    public AgendamentoDto atualizar(AgendamentoDto dados, Long id) {
        Tutor usuarioLogado = TutorService.getUsuarioLogado();
        Agendamento agendamento = repository.findById(id).orElseThrow(() ->
        new ResponseStatusException(HttpStatus.NOT_FOUND, "Agendamento não encontrado"));

        if (RoleEnum.USER.equals(usuarioLogado.getRole())) {
            if (!agendamento.getTutor().equals(usuarioLogado)) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Não autorizado a atualizar o agendamento");
            }
        }
        repository.save(agendamento);
        AgendamentoDto dto = mapper.toDto(agendamento);
        return dto;
    }


//    public void deletar(Long id){
//        if(!repository.existsById(id)){
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Agendamento não encontrado");
//        }
//        repository.deleteById(id);
//    }
}