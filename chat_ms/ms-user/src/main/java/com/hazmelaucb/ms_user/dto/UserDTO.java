package com.hazmelaucb.ms_user.dto;


import java.time.LocalDateTime;
import java.util.UUID;

public class UserDTO {

    private UUID userId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private LocalDateTime registrationDate;
    private String userType; // "STUDENT" o "ADMIN"
    private Boolean active;

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
    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }
    public void setRegistrationDate(LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }
    public String getUserType() {
        return userType;
    }
    public void setUserType(String userType) {
        this.userType = userType;
    }
    public Boolean getActive() {
        return active;
    }
    public void setActive(Boolean active) {
        this.active = active;
    }
}
