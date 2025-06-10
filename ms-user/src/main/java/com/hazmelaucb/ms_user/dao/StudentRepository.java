package com.hazmelaucb.ms_user.dao;


import com.hazmelaucb.ms_user.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface StudentRepository extends JpaRepository<Student, UUID> {
    // Se pueden agregar métodos custom si se requieren consultas específicas.
    Optional<Student> findByUserEmail(String email);

}
