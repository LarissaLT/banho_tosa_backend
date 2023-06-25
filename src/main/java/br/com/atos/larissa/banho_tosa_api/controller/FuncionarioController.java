package br.com.atos.larissa.banho_tosa_api.controller;

import br.com.atos.larissa.banho_tosa_api.dto.FuncionarioDto;
import br.com.atos.larissa.banho_tosa_api.service.FuncionarioService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("funcionarioControllerApi")
@RequestMapping("/api/v1/funcionarios")
public class FuncionarioController {

    private final FuncionarioService service;

    public FuncionarioController(@Qualifier("funcionarioApi") FuncionarioService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public FuncionarioDto cadastrar(@RequestBody FuncionarioDto dados){
        return service.cadastrar(dados);
    }

    @GetMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public List<FuncionarioDto> listar(){
        return service.listar();
    }

    @GetMapping("/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public FuncionarioDto buscar(@PathVariable Long id){
        return service.buscar(id);
    }

    @PutMapping("/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public FuncionarioDto atualizar(@RequestBody FuncionarioDto dados, @PathVariable Long id){

        return service.atualizar(dados, id);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public void deletar(@PathVariable Long id){
        service.deletar(id);
    }
}
