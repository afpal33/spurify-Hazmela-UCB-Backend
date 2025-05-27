package com.hazmelaucb.edge_server;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

@Configuration
public class JwtDecoderConfig {

    // 🔐 Inyecta la clave desde application.yml o config server
    @Value("${spring.security.oauth2.resourceserver.jwt.secret-key}")
    private String secretKey;

    /**
     * Bean manual para ReactiveJwtDecoder
     * Necesario en entornos WebFlux donde la resolución automática puede fallar
     */
    @Bean
    public ReactiveJwtDecoder jwtDecoder() {
        return NimbusReactiveJwtDecoder
                .withSecretKey(new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), "HmacSHA256"))
                .build();
    }
}
