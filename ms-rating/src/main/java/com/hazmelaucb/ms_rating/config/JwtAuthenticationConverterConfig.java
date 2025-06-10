package com.hazmelaucb.ms_rating.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Collection;
import java.util.Collections;

@Configuration
public class JwtAuthenticationConverterConfig {

  @Bean
  public JwtAuthenticationConverter jwtAuthenticationConverter() {
    JwtAuthenticationConverter converter = new JwtAuthenticationConverter();

    // Configura cómo se extraen los roles si los tienes
    JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
    grantedAuthoritiesConverter.setAuthorityPrefix("ROLE_"); // Opcional
    grantedAuthoritiesConverter.setAuthoritiesClaimName("roles"); // Ajusta si usas otro claim para roles

    converter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);

    // Personalizamos el principal (el nombre del usuario)
    converter.setPrincipalClaimName("sub"); // Por defecto ya usa "sub", pero si usas otro claim, cámbialo

    return converter;
  }
}
