package com.hazmelaucb.ms_authenticate.dao;

import com.hazmelaucb.ms_authenticate.entity.ActiveSessionEntity;
import com.hazmelaucb.ms_authenticate.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ActiveSessionRepository extends JpaRepository<ActiveSessionEntity, UUID> {
    List<ActiveSessionEntity> findByUser(UserEntity user);
    Optional<ActiveSessionEntity> findByRefreshToken(String refreshToken);
    void deleteByRefreshToken(String refreshToken);
    void deleteByUser(UserEntity user);
}
