package com.hazmelaucb.ms_user.bl;

import com.hazmelaucb.ms_user.dao.AdministratorRepository;
import com.hazmelaucb.ms_user.dao.UserRepository;
import com.hazmelaucb.ms_user.dto.AdministratorDTO;
import com.hazmelaucb.ms_user.dto.SuccessResponse;
import com.hazmelaucb.ms_user.entity.Administrator;
import com.hazmelaucb.ms_user.entity.User;
import com.hazmelaucb.ms_user.utils.exceptions.BadRequestException;
import com.hazmelaucb.ms_user.utils.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AdministratorBL {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AdministratorRepository administratorRepository;

    @Autowired
    private AuditService auditService;

    @Transactional
    public AdministratorDTO createAdministrator(AdministratorDTO adminDTO) {
        // Verificar que el email no exista
        if (userRepository.findByEmail(adminDTO.getEmail()).isPresent()) {
            throw new BadRequestException("El email ya existe");
        }

        // Crear entidad User
        User user = new User();
        user.setFirstName(adminDTO.getFirstName());
        user.setLastName(adminDTO.getLastName());
        user.setEmail(adminDTO.getEmail());
        user.setPhone(adminDTO.getPhone());
        user.setUserType("ADMIN");
        user = userRepository.save(user);

        // Crear entidad Administrator
        Administrator admin = new Administrator();
        admin.setUser(user);
        admin.setRole(adminDTO.getRole());
        admin.setPermissions(adminDTO.getPermissions());
        admin = administratorRepository.save(admin);

        // Registrar auditoría
        auditService.createAudit(user.getUserId(), "CREATE", null);

        adminDTO.setUserId(user.getUserId());
        return adminDTO;
    }

    @Transactional
    public AdministratorDTO updateAdministrator(UUID userId, AdministratorDTO adminDTO) {
        Administrator admin = administratorRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Administrador no encontrado"));

        // Actualizar datos del User
        User user = admin.getUser();
        user.setFirstName(adminDTO.getFirstName());
        user.setLastName(adminDTO.getLastName());
        user.setPhone(adminDTO.getPhone());
        userRepository.save(user);

        // Actualizar datos del Administrator
        admin.setRole(adminDTO.getRole());
        admin.setPermissions(adminDTO.getPermissions());
        administratorRepository.save(admin);

        // Registrar auditoría
        auditService.createAudit(userId, "UPDATE", null);

        adminDTO.setUserId(userId);
        return adminDTO;
    }

    @Transactional
    public SuccessResponse deleteAdministrator(UUID userId) {
        Optional<Administrator> adminOptional = administratorRepository.findById(userId);

        if (adminOptional.isEmpty()) {
            throw new ResourceNotFoundException("Administrador no encontrado");
        }

        Administrator admin = adminOptional.get();

        // Registrar auditoría antes de eliminar
        auditService.createAudit(userId, "DELETE", null);

        administratorRepository.delete(admin);
        userRepository.deleteById(userId);

        // Crear y devolver la respuesta de éxito
        // Crear y devolver la respuesta de éxito con estado 200 OK
        SuccessResponse response = new SuccessResponse("Administrador eliminado correctamente", userId);
        return ResponseEntity.status(HttpStatus.OK).body(response).getBody();
    }


    public AdministratorDTO getAdministratorById(UUID userId) {
        Administrator admin = administratorRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Administrador no encontrado"));
        return convertAdministratorToDTO(admin);
    }

    public List<AdministratorDTO> getAllAdministrators() {
        return administratorRepository.findAll().stream()
                .map(this::convertAdministratorToDTO)
                .collect(Collectors.toList());
    }

    // Método auxiliar para convertir entidad a DTO
    private AdministratorDTO convertAdministratorToDTO(Administrator admin) {
        AdministratorDTO dto = new AdministratorDTO();
        dto.setUserId(admin.getUser().getUserId());
        dto.setFirstName(admin.getUser().getFirstName());
        dto.setLastName(admin.getUser().getLastName());
        dto.setEmail(admin.getUser().getEmail());
        dto.setPhone(admin.getUser().getPhone());
        dto.setRole(admin.getRole());
        dto.setPermissions(admin.getPermissions());
        return dto;
    }
}
