package com.hazmelaucb.ms_user.dao;


import com.hazmelaucb.ms_user.entity.Skill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SkillRepository extends JpaRepository<Skill, Integer> {
    // Se pueden definir consultas personalizadas si fuera necesario.
}
