package com.hazmelaucb.ms_authenticate.dao;


import com.hazmelaucb.ms_authenticate.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    Optional<UserEntity> findByEmail(String email);

    @Modifying
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Query(value = "UPDATE auth_users SET failed_attempts = :failedAttempts, is_locked = :isLocked WHERE id = :userId", nativeQuery = true)
    int updateFailedAttempts(@Param("userId") UUID userId, @Param("failedAttempts") int failedAttempts, @Param("isLocked") boolean isLocked);

}
