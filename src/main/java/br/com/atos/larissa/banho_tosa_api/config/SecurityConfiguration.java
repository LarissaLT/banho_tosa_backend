package br.com.atos.larissa.banho_tosa_api.config;

import br.com.atos.larissa.banho_tosa_api.service.TutorService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final TutorService tutorService;

    // This is a Bean that configures the main security filter chain.
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Disables CSRF (Cross-Site Request Forgery) protection. This is often done for APIs since they
                // are not vulnerable to CSRF in the same way web applications are.
                .csrf(AbstractHttpConfigurer::disable)

                // Configures authorization rules for incoming requests.
                .authorizeHttpRequests(request ->
                        // Any request to "/api/v1/auth/**" i.e., it does not require authentication.
                        request.requestMatchers("/api/v1/auth/**").permitAll()
                                // Any other request must be authenticated.
                                .anyRequest().authenticated()
                )
                // Disables the X-Frame-Options header, which can prevent clickjacking attacks.
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))

                // Configures the session management to be stateless. This is often done for APIs,
                // which don't require sessions.
                .sessionManagement(manager -> manager.sessionCreationPolicy(STATELESS))

                // Configures an AuthenticationProvider, which is responsible for authenticating users.
                .authenticationProvider(authenticationProvider())

                // Adds the JwtAuthenticationFilter before the default UsernamePasswordAuthenticationFilter.
                // This filter can extract user details from a JWT.
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // This Bean provides a password encoder. BCrypt is a good choice since it automatically includes a random salt.
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // This Bean provides an AuthenticationProvider. In this case, it's a DaoAuthenticationProvider,
    // which can use a UserDetailsService to load user details from a database, and a PasswordEncoder to
    // check passwords.
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(tutorService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    // This Bean provides an AuthenticationManager, which is used by Spring Security to handle authentication.
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
            throws Exception {
        return config.getAuthenticationManager();
    }
}
