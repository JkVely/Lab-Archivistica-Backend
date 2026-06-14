package co.edu.udistrital.labarchivistica.security;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import co.edu.udistrital.labarchivistica.config.AppProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

/**
 * Utilidad para generación y validación de tokens JWT.
 * Usa JJWT 0.12.x con firma HMAC-SHA256.
 */
@Component
@RequiredArgsConstructor
public class JwtUtil {

    private final AppProperties appProperties;

    // -------------------------------------------------------
    // Generación
    // -------------------------------------------------------

    /**
     * Genera un JWT con el email del usuario como subject.
     *
     * @param userDetails detalles del usuario autenticado
     * @return token JWT firmado
     */
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    /**
     * Genera un JWT con claims adicionales.
     *
     * @param extraClaims mapa de claims extra a incluir en el payload
     * @param userDetails detalles del usuario autenticado
     * @return token JWT firmado
     */
    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts.builder()
                .claims(extraClaims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + appProperties.getJwtExpiration()))
                .signWith(getSigningKey(), Jwts.SIG.HS256)
                .compact();
    }

    // -------------------------------------------------------
    // Extracción de claims
    // -------------------------------------------------------

    /**
     * Extrae el username (email) del token.
     *
     * @param token JWT
     * @return email del usuario
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extrae la fecha de expiración del token.
     *
     * @param token JWT
     * @return fecha de expiración
     */
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Extrae un claim genérico del token.
     *
     * @param token          JWT
     * @param claimsResolver función que extrae el claim deseado
     * @return valor del claim
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // -------------------------------------------------------
    // Validación
    // -------------------------------------------------------

    /**
     * Valida que el token pertenezca al usuario y no esté expirado.
     *
     * @param token       JWT
     * @param userDetails detalles del usuario a comparar
     * @return {@code true} si el token es válido
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    // -------------------------------------------------------
    // Helpers privados
    // -------------------------------------------------------

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private SecretKey getSigningKey() {
        byte[] keyBytes = appProperties.getJwtSecret().getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
