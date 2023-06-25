package br.com.atos.larissa.banho_tosa_api.controller;

import br.com.atos.larissa.banho_tosa_api.dto.CachorroDto;
import br.com.atos.larissa.banho_tosa_api.service.CachorroService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("cachorroControllerApi")
@RequestMapping("/api/v1/cachorros")
public class CachorroController {

    private final CachorroService service;

    public CachorroController(@Qualifier("cachorroApi") CachorroService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public CachorroDto cadastrar(@RequestBody CachorroDto dados){
        return service.cadastrar(dados);
    }

    @GetMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public List<CachorroDto> cachorros(){
        return service.listar();
    }

    @GetMapping("/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public CachorroDto buscar(@PathVariable Long id){
        return service.buscar(id);
    }

    @PutMapping("/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public CachorroDto atualizar(@RequestBody CachorroDto dados, @PathVariable Long id){
        return service.atualizar(dados, id);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public void deletar(@PathVariable Long id){
        service.deletar(id);
    }
}
