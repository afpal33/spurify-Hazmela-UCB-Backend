package com.hazmelaucb.ms_authenticate.entity;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "roles")
public class RoleEntity {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(unique = true, nullable = false)
    private String name; // "ADMIN", "ESTUDIANTE"

    public RoleEntity() {}

    public RoleEntity(String name) {
        this.name = name;
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}
