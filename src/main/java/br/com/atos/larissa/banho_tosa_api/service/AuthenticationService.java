package br.com.atos.larissa.banho_tosa_api.service;


import br.com.atos.larissa.banho_tosa_api.dto.request.SignUpRequest;
import br.com.atos.larissa.banho_tosa_api.dto.request.SigninRequest;
import br.com.atos.larissa.banho_tosa_api.dto.response.JwtAuthenticationResponse;
import br.com.atos.larissa.banho_tosa_api.model.RoleEnum;
import br.com.atos.larissa.banho_tosa_api.model.Tutor;
import br.com.atos.larissa.banho_tosa_api.repository.TutorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final TutorRepository tutorRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    // The 'signup' method handles user registration.
    public JwtAuthenticationResponse signup(SignUpRequest request) {
        // It creates a new user entity with the provided sign-up request.
        // The password is encoded before being stored.
        Tutor user = Tutor.builder()
                .nome(request.nome())
                .email(request.email())
                .senha(passwordEncoder.encode(request.senha()))
                .role(RoleEnum.USER)
                .build();

        // The new user entity is then saved into the UserRepository (likely a database).
        tutorRepository.save(user);

        // A JWT is generated for the new user.
        var jwt = jwtService.generateToken(user);

        // The JWT is returned inside a JwtAuthenticationResponse object.
        return new JwtAuthenticationResponse(jwt);

    }

    // The 'signin' method handles user authentication.
    public JwtAuthenticationResponse signin(SigninRequest request) {
        // The AuthenticationManager attempts to authenticate the request.
        // It throws an AuthenticationException if the authentication fails.
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.senha()));

        // If authentication succeeds, we find the user in our UserRepository.
        var user = tutorRepository.findByEmail(request.email())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password."));


        // A JWT is generated for the authenticated user.
        var jwt = jwtService.generateToken(user);

        // The JWT is returned inside a JwtAuthenticationResponse object.
        return new JwtAuthenticationResponse(jwt);
    }
}
