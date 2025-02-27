package com.hazmelaucb.ms_authenticate.dao;

import com.hazmelaucb.ms_authenticate.entity.LoginAttemptEntity;
import com.hazmelaucb.ms_authenticate.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface LoginAttemptRepository extends JpaRepository<LoginAttemptEntity, UUID> {
    List<LoginAttemptEntity> findByUserOrderByTimestampDesc(UserEntity user);
}
