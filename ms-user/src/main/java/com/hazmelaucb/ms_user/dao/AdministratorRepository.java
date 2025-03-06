package com.hazmelaucb.ms_user.dao;



import com.hazmelaucb.ms_user.entity.Administrator;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface AdministratorRepository extends JpaRepository<Administrator, UUID> {
    // Métodos adicionales pueden definirse según las necesidades.
}
