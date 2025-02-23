package com.hazmelaucb.ms_authenticate.api;

import com.hazmelaucb.ms_authenticate.dao.UserRepository;
import com.hazmelaucb.ms_authenticate.entity.UserEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class UnlockUserController {

    private final UserRepository userRepository;

    public UnlockUserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PutMapping("/unlock-user/{email}")
    public ResponseEntity<String> unlockUser(@PathVariable String email) {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        user.resetFailedAttempts();
        userRepository.save(user);

        return ResponseEntity.ok("✅ Usuario desbloqueado exitosamente.");
    }
}
