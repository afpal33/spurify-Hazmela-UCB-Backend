package com.hazmelaucb.ms_user.bl;

import com.hazmelaucb.ms_user.dao.UserRepository;
import com.hazmelaucb.ms_user.dto.SuccessResponse;
import com.hazmelaucb.ms_user.entity.Student;
import com.hazmelaucb.ms_user.entity.User;
import com.hazmelaucb.ms_user.utils.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserCommonBL {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuditService auditService;

    @Transactional
    public ResponseEntity<SuccessResponse> disableUser(UUID userId) {
        Optional<User> userOptional = userRepository.findById(userId);

        if (!userOptional.isPresent()) {
            throw new ResourceNotFoundException("Usuario no encontrado");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
        user.setActive(false);
        userRepository.save(user);
        auditService.createAudit(userId, "UPDATE", "Usuario deshabilitado");

        SuccessResponse response = new SuccessResponse("Usuario deshabilitado", userId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
