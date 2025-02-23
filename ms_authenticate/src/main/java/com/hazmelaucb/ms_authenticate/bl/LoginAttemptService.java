package com.hazmelaucb.ms_authenticate.bl;

import com.hazmelaucb.ms_authenticate.dao.LoginAttemptRepository;
import com.hazmelaucb.ms_authenticate.dao.UserRepository;
import com.hazmelaucb.ms_authenticate.dto.LoginAttemptResponse;
import com.hazmelaucb.ms_authenticate.entity.LoginAttemptEntity;
import com.hazmelaucb.ms_authenticate.entity.UserEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class LoginAttemptService {

    private final LoginAttemptRepository loginAttemptRepository;
    private final UserRepository userRepository;

    public LoginAttemptService(LoginAttemptRepository loginAttemptRepository, UserRepository userRepository) {
        this.loginAttemptRepository = loginAttemptRepository;
        this.userRepository = userRepository;
    }

    public void registerLoginAttempt(UserEntity user, boolean success, String ip, String userAgent) {
        LoginAttemptEntity attempt = new LoginAttemptEntity();
        attempt.setUser(user);
        attempt.setSuccess(success);
        attempt.setIp(ip);
        attempt.setUserAgent(userAgent);

        loginAttemptRepository.save(attempt);
    }

    public List<LoginAttemptResponse> getLoginAttempts(UUID userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        return loginAttemptRepository.findByUserOrderByTimestampDesc(user).stream()
                .map(attempt -> new LoginAttemptResponse(
                        attempt.getId(),
                        attempt.isSuccess(),
                        attempt.getIp(),
                        attempt.getUserAgent(),
                        attempt.getTimestamp()
                ))
                .collect(Collectors.toList());
    }
}
