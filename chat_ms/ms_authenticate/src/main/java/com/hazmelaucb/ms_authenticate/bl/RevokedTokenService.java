package com.hazmelaucb.ms_authenticate.bl;

import com.hazmelaucb.ms_authenticate.dao.RevokedTokenRepository;
import com.hazmelaucb.ms_authenticate.entity.RevokedTokenEntity;
import com.hazmelaucb.ms_authenticate.utils.exceptions.InvalidTokenException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class RevokedTokenService {

    private final RevokedTokenRepository revokedTokenRepository;

    public RevokedTokenService(RevokedTokenRepository revokedTokenRepository) {
        this.revokedTokenRepository = revokedTokenRepository;
    }

    public void revokeToken(String jwtToken) {

        if (!StringUtils.hasText(jwtToken)) {
            throw new InvalidTokenException("El token no puede estar vacío o nulo.");
        }

        if (isTokenRevoked(jwtToken)) {
            System.out.println("⚠️ Token ya está revocado: " + jwtToken);
            return; // Evitar duplicados
        }

        RevokedTokenEntity revokedToken = new RevokedTokenEntity();
        revokedToken.setJwtToken(jwtToken);
        revokedTokenRepository.save(revokedToken);

        System.out.println("✅ Token revocado y guardado en la base de datos: " + jwtToken);
    }

    public boolean isTokenRevoked(String jwtToken) {
        if (!StringUtils.hasText(jwtToken)) {
            throw new InvalidTokenException("El token no puede estar vacío o nulo.");
        }
        return revokedTokenRepository.findByJwtToken(jwtToken).isPresent();
    }
}
