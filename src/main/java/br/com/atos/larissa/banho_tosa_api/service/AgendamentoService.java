package br.com.atos.larissa.banho_tosa_api.service;

import br.com.atos.larissa.banho_tosa_api.dto.AgendamentoDto;
import br.com.atos.larissa.banho_tosa_api.dto.AgendamentoFormDto;
import br.com.atos.larissa.banho_tosa_api.dto.TutorDto;
import br.com.atos.larissa.banho_tosa_api.mapper.AgendamentoMapper;
import br.com.atos.larissa.banho_tosa_api.mapper.CachorroMapper;
import br.com.atos.larissa.banho_tosa_api.mapper.TutorMapper;
import br.com.atos.larissa.banho_tosa_api.model.*;
import br.com.atos.larissa.banho_tosa_api.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
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
    private final CachorroMapper cachorroMapper;

    public AgendamentoService(AgendamentoRepository repository, ServicoRepository servicoRepository,
                              FuncionarioRepository funcionarioRepository, CachorroRepository cachorroRepository,
                              TutorRepository tutorRepository, TutorMapper tutorMapper, AgendamentoMapper mapper, CachorroMapper cachorroMapper) {
        this.repository = repository;
        this.servicoRepository = servicoRepository;
        this.funcionarioRepository = funcionarioRepository;
        this.cachorroRepository = cachorroRepository;
        this.tutorRepository = tutorRepository;
        this.tutorMapper = tutorMapper;
        this.mapper = mapper;
        this.cachorroMapper = cachorroMapper;
    }

    public AgendamentoDto cadastrar(AgendamentoDto dados) {
        Tutor usuarioLogado = TutorService.getUsuarioLogado();
        Agendamento agendamento = mapper.toEntity(dados);
        if (RoleEnum.USER.equals(usuarioLogado.getRole())){
            agendamento.setTutor(usuarioLogado);
        }
        repository.save(agendamento);
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
        Tutor usuarioLogado = TutorService.getUsuarioLogado();
        List<Servico> servicos = servicoRepository.findAll();
        List<Funcionario> funcionarios = funcionarioRepository.findAll();
        List<Tutor> tutores = tutorRepository.findAll();
        List<TutorDto> tutoresDto = tutorMapper.toDto(tutores);

        List<Cachorro> cachorros;
        if (RoleEnum.USER.equals(usuarioLogado.getRole())) {
            cachorros = usuarioLogado.getCachorros();
        } else {
            cachorros = cachorroRepository.findAll();
        }

        AgendamentoFormDto agendamentoFormDto = new AgendamentoFormDto(servicos, funcionarios, tutoresDto, cachorroMapper.toDto(cachorros));
        return agendamentoFormDto;
    }

    public AgendamentoDto buscar(Long id) {
        Agendamento agendamento = repository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Busca não encontrada"));
        AgendamentoDto dto = mapper.toDto(agendamento);
        return dto;
    }

    public AgendamentoDto atualizar(AgendamentoDto dados, Long id) {
        Tutor usuarioLogado = TutorService.getUsuarioLogado();
        Agendamento agendamento = repository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Agendamento não encontrado"));

        if (RoleEnum.USER.equals(usuarioLogado.getRole())) {
            if (!agendamento.getTutor().getId().equals(usuarioLogado.getId())) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Não autorizado a atualizar o agendamento");
            }
        }
        agendamento = mapper.toEntity(dados);
        agendamento.setId(id);
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