package com.hazmelaucb.ms_user.api;


import com.hazmelaucb.ms_user.bl.AdministratorBL;
import com.hazmelaucb.ms_user.dto.AdministratorDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admins")
public class AdministratorController {

    @Autowired
    private AdministratorBL administratorBL;

    @PostMapping
    public ResponseEntity<AdministratorDTO> createAdministrator(@RequestBody AdministratorDTO adminDTO) {
        AdministratorDTO created = administratorBL.createAdministrator(adminDTO);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AdministratorDTO> updateAdministrator(@PathVariable("id") UUID userId,
                                                                @RequestBody AdministratorDTO adminDTO) {
        AdministratorDTO updated = administratorBL.updateAdministrator(userId, adminDTO);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdministrator(@PathVariable("id") UUID userId) {
        administratorBL.deleteAdministrator(userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdministratorDTO> getAdministratorById(@PathVariable("id") UUID userId) {
        AdministratorDTO dto = administratorBL.getAdministratorById(userId);
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<List<AdministratorDTO>> getAllAdministrators() {
        List<AdministratorDTO> dtos = administratorBL.getAllAdministrators();
        return ResponseEntity.ok(dtos);
    }
}
