package br.com.atos.larissa.banho_tosa_api.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
    // Injects the JWT signing key from the application configuration.
    @Value("${token.signing.key}")
    private String jwtSigningKey;

    // This method takes a token and extracts the username from it.
    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // This method generates a token for a specific user.
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    // This method checks if a given token is valid for a given user.
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String userName = extractUserName(token);
        return (userName.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    // This method takes a token and a function to apply to the claims of the token.
    // It applies this function and returns the result.
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolvers) {
        final Claims claims = extractAllClaims(token);
        return claimsResolvers.apply(claims);
    }

    // This method generates a JWT token for a specific user with additional claims.
    // The token has a specific expiration date and is signed with a secret key.
    private String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts.builder().setClaims(extraClaims).setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 *60 * 24))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256).compact();
    }

    // This method checks if a given token has expired.
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // This method extracts the expiration date from a token.
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // This method takes a token, verifies it with the signing key, and returns all the claims in it.
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token)
                .getBody();
    }

    // This method decodes the signing key from base64 and returns a Key object that can be used for signing and verification.
    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSigningKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
