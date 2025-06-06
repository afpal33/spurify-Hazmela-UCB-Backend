package com.hazmelaucb.ms_user.dto;

import java.util.List;
import java.util.UUID;

public class AdministratorDTO {

    private UUID userId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;

    // Campos específicos del administrador
    private String role;
    // Ahora se usa List<String> en lugar de arreglo
    private List<String> permissions;

    // Getters y setters

    public UUID getUserId() {
        return userId;
    }
    public void setUserId(UUID userId) {
        this.userId = userId;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
    public List<String> getPermissions() {
        return permissions;
    }
    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }
}
