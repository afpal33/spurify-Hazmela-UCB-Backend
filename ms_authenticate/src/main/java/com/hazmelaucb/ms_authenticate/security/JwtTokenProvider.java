package com.hazmelaucb.ms_authenticate.security;

import com.hazmelaucb.ms_authenticate.bl.RevokedTokenService;
import com.hazmelaucb.ms_authenticate.entity.RoleEntity;
import com.hazmelaucb.ms_authenticate.entity.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Component
public class JwtTokenProvider {

    private final Key secretKey;
    private final RevokedTokenService revokedTokenService;

    public JwtTokenProvider(@Value("${jwt.secret}") String secret, RevokedTokenService revokedTokenService) {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.revokedTokenService = revokedTokenService;
    }

    public String generateToken(UserEntity user) {
        String role = user.getRoles().stream()
                .map(RoleEntity::getName)
                .findFirst()
                .orElse("ESTUDIANTE"); // Por defecto estudiante

        return Jwts.builder()
                .setSubject(user.getEmail())
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 1 día
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateRefreshToken(UserEntity user) {
        String role = user.getRoles().stream()
                .map(RoleEntity::getName)
                .findFirst()
                .orElse("ESTUDIANTE"); // Si no tiene rol, asigna uno por defecto

        return Jwts.builder()
                .setSubject(user.getEmail())
                .claim("role", role) // 🔥 Agregamos el rol al refresh token
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 604800000)) // 7 días
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public String getUsernameFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateToken(String token) {
        if (revokedTokenService.isTokenRevoked(token)) {
            System.out.println("❌ Token revocado: " + token);
            return false;
        }

        try {
            Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            System.out.println("❌ Token inválido: " + e.getMessage());
            return false;
        }
    }

    // 🔹 Extraer token del header Authorization
    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7); // 🔥 Remueve "Bearer " para obtener solo el token
        }
        return null;
    }

    // 🔹 Obtener autenticación basada en el token
    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();

        String role = claims.get("role", String.class);

        // Si el rol es null, evitar que cause error
        if (role == null || role.isBlank()) {
            throw new IllegalArgumentException("El token no contiene roles válidos.");
        }
        List<SimpleGrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(role));

        return new UsernamePasswordAuthenticationToken(claims.getSubject(), null, authorities);
    }


}
