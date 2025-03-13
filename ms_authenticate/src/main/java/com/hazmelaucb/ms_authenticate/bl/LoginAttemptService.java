package com.hazmelaucb.ms_authenticate.bl;

import com.hazmelaucb.ms_authenticate.dao.LoginAttemptRepository;
import com.hazmelaucb.ms_authenticate.dao.UserRepository;
import com.hazmelaucb.ms_authenticate.dto.LoginAttemptResponse;
import com.hazmelaucb.ms_authenticate.entity.LoginAttemptEntity;
import com.hazmelaucb.ms_authenticate.entity.UserEntity;
import com.hazmelaucb.ms_authenticate.utils.exceptions.UserNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
        if (user == null) {
            throw new IllegalArgumentException("El usuario no puede ser nulo");
        }

        if (!StringUtils.hasText(ip)) {
            throw new IllegalArgumentException("La dirección IP no puede estar vacía");
        }

        if (!StringUtils.hasText(userAgent)) {
            throw new IllegalArgumentException("El User-Agent no puede estar vacío");
        }
        LoginAttemptEntity attempt = new LoginAttemptEntity();
        attempt.setUser(user);
        attempt.setSuccess(success);
        attempt.setIp(ip);
        attempt.setUserAgent(userAgent);

        loginAttemptRepository.save(attempt);
    }

    public List<LoginAttemptResponse> getLoginAttempts(UUID userId) {

        if (userId == null) {
            throw new IllegalArgumentException("El ID del usuario no puede ser nulo");
        }

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Usuario no encontrado"));

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
