package br.com.atos.larissa.banho_tosa_api.controller;

import br.com.atos.larissa.banho_tosa_api.dto.request.SignUpRequest;
import br.com.atos.larissa.banho_tosa_api.dto.request.SigninRequest;
import br.com.atos.larissa.banho_tosa_api.dto.response.JwtAuthenticationResponse;
import br.com.atos.larissa.banho_tosa_api.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AutenticacaoController {
    private final AuthenticationService authenticationService;

    @PostMapping("/signup")
    @ResponseBody
    public JwtAuthenticationResponse signup(@RequestBody SignUpRequest request) {
        return authenticationService.signup(request);
    }

    @PostMapping("/signin")
    @ResponseBody
    public JwtAuthenticationResponse signin(@RequestBody SigninRequest request) {
        return authenticationService.signin(request);
    }
}
