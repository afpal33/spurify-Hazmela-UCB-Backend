package com.hazmelaucb.ms_authenticate.dao;

import com.hazmelaucb.ms_authenticate.entity.RevokedTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface RevokedTokenRepository extends JpaRepository<RevokedTokenEntity, UUID> {
    Optional<RevokedTokenEntity> findByJwtToken(String jwtToken);
}
