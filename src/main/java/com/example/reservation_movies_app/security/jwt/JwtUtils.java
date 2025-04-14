package com.example.reservation_movies_app.security.jwt;

import com.example.reservation_movies_app.security.user.MovieUserDetails;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.List;
/**
 * JwtUtils es una clase encargada de manejar las operaciones relacionadas con la generación y validación de tokens JWT
 * para la autenticación de usuarios en el sistema. Utiliza la biblioteca JJWT para crear, firmar y validar los tokens JWT.
 *
 * Funcionalidad:
 * - **generateTokenForUser**: Genera un token JWT para el usuario autenticado. El token contiene:
 *   - El correo electrónico del usuario como el "subject".
 *   - El ID del usuario y sus roles como "claims" personalizados.
 *   - La fecha de emisión y la fecha de expiración basadas en la configuración de tiempo.
 * - **getUsernameFromToken**: Extrae el nombre de usuario (correo electrónico) del token JWT.
 * - **validateToken**: Valida la integridad y la firma del token JWT, asegurándose de que no esté expirado ni sea malformado.
 *
 * La clase utiliza un secreto (definido en la configuración de la aplicación) para firmar el token y garantizar su autenticidad.
 * El secreto se codifica en base64 y se utiliza para generar una clave HMAC para la firma del token JWT.
 *
 * Esta clase es utilizada durante el proceso de autenticación para generar tokens y validar las solicitudes de los usuarios
 * basadas en esos tokens.
 */

@Component
public class JwtUtils {

    @Value("${auth.token.jwtSecret}")
    private String jwtSecret;
    @Value("${auth.token.expirationInMils}")
    private int expirationTime;

    public String generateTokenForUser(Authentication authentication) {
        MovieUserDetails userPrincipal = (MovieUserDetails) authentication.getPrincipal();
        List<String> roles = userPrincipal.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        return Jwts.builder()
                .setSubject(userPrincipal.getEmail())
                .claim("id",userPrincipal.getId())
                .claim("roles", roles)
                .setIssuedAt(new Date())
                .setExpiration(new Date ((new Date()).getTime() + expirationTime))
                .signWith(key(), SignatureAlgorithm.HS256).compact();
    }

    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    public  String getUsernameFromToken(String token) {
        return   Jwts.parserBuilder()
                .setSigningKey(key())
                .build()
                .parseClaimsJws(token)
                .getBody().getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException |
                 IllegalArgumentException e) {
            throw new JwtException(e.getMessage());
        }
    }
}