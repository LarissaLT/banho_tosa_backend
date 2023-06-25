package br.com.atos.larissa.banho_tosa_api.controller;

import br.com.atos.larissa.banho_tosa_api.dto.ServicoDto;
import br.com.atos.larissa.banho_tosa_api.service.ServicoService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("servicoControllerApi")
@RequestMapping("/api/v1/servicos")
public class ServicoController {

    private final ServicoService service;

    public ServicoController(@Qualifier("servicoApi") ServicoService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public ServicoDto cadastrar(@RequestBody ServicoDto dados){
        return service.cadastrar(dados);
    }

    @GetMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public List<ServicoDto> cachorros(){
        return service.listar();
    }

    @GetMapping("/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ServicoDto buscar(@PathVariable Long id){
        return service.buscar(id);
    }

    @PutMapping("/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ServicoDto atualizar(@RequestBody ServicoDto dados, @PathVariable Long id){
        return service.atualizar(dados, id);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public void deletar(@PathVariable Long id){
        service.deletar(id);
    }
}
