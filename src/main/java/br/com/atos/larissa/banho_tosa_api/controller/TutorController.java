package br.com.atos.larissa.banho_tosa_api.controller;

import br.com.atos.larissa.banho_tosa_api.dto.TutorDto;
import br.com.atos.larissa.banho_tosa_api.service.TutorService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("tutorControllerApi")
@RequestMapping("/api/v1/tutores")
public class TutorController {

    private final TutorService service;

    public TutorController(@Qualifier("tutorApi")TutorService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public TutorDto cadastrar(@RequestBody TutorDto dados){
        return service.cadastrar(dados);
    }

    @GetMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public List<TutorDto> cachorros(){
        return service.listar();
    }

    @GetMapping("/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public TutorDto buscar(@PathVariable Long id){
        return service.buscar(id);
    }

    @PutMapping("/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public TutorDto atualizar(@RequestBody TutorDto dados, @PathVariable Long id){
        return service.atualizar(dados, id);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public void deletar(@PathVariable Long id){
        service.deletar(id);
    }
}
