package br.com.atos.larissa.banho_tosa_api.config;


import br.com.atos.larissa.banho_tosa_api.service.JwtService;
import br.com.atos.larissa.banho_tosa_api.service.TutorService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final TutorService tutorService;
    // This method attempts to authenticate the user based on a JWT in the "Authorization" header.
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        // Gets the "Authorization" header from the request.
        final String authHeader = request.getHeader("Authorization");

        // Initializes the variables for the JWT and the email from the JWT.
        final String jwt;
        final String userEmail;

        // If the "Authorization" header is empty or doesn't start with "Bearer ",
        // the filter just passes the request/response pair to the next filter in the chain and returns.
        if (StringUtils.isEmpty(authHeader) || !StringUtils.startsWith(authHeader, "Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Extracts the JWT from the "Authorization" header (it should be right after "Bearer ").
        jwt = authHeader.substring(7);

        // Uses the JWT service to extract the email from the JWT.
        userEmail = jwtService.extractUserName(jwt);

        // If the email is not empty and the context does not already have an authentication (user is not authenticated),
        // it tries to authenticate the user.
        if (StringUtils.isNotEmpty(userEmail)
                && SecurityContextHolder.getContext().getAuthentication() == null) {

            // Loads the user details.
            UserDetails userDetails = tutorService.loadUserByUsername(userEmail);

            // If the JWT is valid for the loaded user, it creates an authentication token and sets it in the security context.
            if (jwtService.isTokenValid(jwt, userDetails)) {
                SecurityContext context = SecurityContextHolder.createEmptyContext();
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());

                // Sets the details of the authentication request.
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Sets the authentication in the context.
                context.setAuthentication(authToken);

                // Sets the context in the SecurityContextHolder.
                SecurityContextHolder.setContext(context);
            }
        }

        // Passes the request/response pair to the next filter in the chain.
        filterChain.doFilter(request, response);
    }
}
