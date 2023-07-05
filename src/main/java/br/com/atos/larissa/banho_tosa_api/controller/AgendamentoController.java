package br.com.atos.larissa.banho_tosa_api.controller;

import br.com.atos.larissa.banho_tosa_api.dto.AgendamentoDto;
import br.com.atos.larissa.banho_tosa_api.dto.AgendamentoFormDto;
import br.com.atos.larissa.banho_tosa_api.model.Tutor;
import br.com.atos.larissa.banho_tosa_api.service.AgendamentoService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("agendamentoControlerApi")
@RequestMapping("/api/v1/agendamentos")
public class AgendamentoController {

    public final AgendamentoService service;

    public AgendamentoController(@Qualifier("agendamentoApi") AgendamentoService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public AgendamentoDto cadastrar(@RequestBody AgendamentoDto dados){

        AgendamentoDto cadastrar = service.cadastrar(dados);
        return cadastrar;
    }

    @GetMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public List<AgendamentoDto> listar(){
        return service.listar();
    }
    @GetMapping("/form")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public AgendamentoFormDto buscarDadosForm(){
        return service.buscarDadosForm();
    }

    @GetMapping("/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public AgendamentoDto buscar(@PathVariable Long id){
        return service.buscar(id);
    }

    @PutMapping("/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public AgendamentoDto atualizar(@RequestBody AgendamentoDto dados, @PathVariable Long id){
        return service.atualizar(dados, id);
    }

//    @DeleteMapping("/{id}")
//    @ResponseBody
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    public void deletar(@PathVariable Long id){
//        service.deletar(id);
//    }
}
