package com.hazmelaucb.ms_user.dto;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

public class StudentDTO {

    private UUID userId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    // Campos específicos del estudiante
    private String address;
    private LocalDate birthDate;
    private String gender; // "MALE", "FEMALE" u "OTHER"
    private String country;
    private String city;
    private String university;
    private String career;
    private Integer semester;
    private BigDecimal average; // Ahora de tipo BigDecimal
    private String studyMode; // "ON-CAMPUS", "ONLINE", "HYBRID"
    private String experience;
    private String highlightedProjects;
    private String coursesCertifications;
    private String academicInterests;
    private String linkedin;
    private String github;
    private String website;
    private String profilePicture;
    private String documentUniversity;
    private Boolean completeProfile;
    private Set<SkillDTO> skills;

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
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public LocalDate getBirthDate() {
        return birthDate;
    }
    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }
    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public String getUniversity() {
        return university;
    }
    public void setUniversity(String university) {
        this.university = university;
    }
    public String getCareer() {
        return career;
    }
    public void setCareer(String career) {
        this.career = career;
    }
    public Integer getSemester() {
        return semester;
    }
    public void setSemester(Integer semester) {
        this.semester = semester;
    }
    public BigDecimal getAverage() {
        return average;
    }
    public void setAverage(BigDecimal average) {
        this.average = average;
    }
    public String getStudyMode() {
        return studyMode;
    }
    public void setStudyMode(String studyMode) {
        this.studyMode = studyMode;
    }
    public String getExperience() {
        return experience;
    }
    public void setExperience(String experience) {
        this.experience = experience;
    }
    public String getHighlightedProjects() {
        return highlightedProjects;
    }
    public void setHighlightedProjects(String highlightedProjects) {
        this.highlightedProjects = highlightedProjects;
    }
    public String getCoursesCertifications() {
        return coursesCertifications;
    }
    public void setCoursesCertifications(String coursesCertifications) {
        this.coursesCertifications = coursesCertifications;
    }
    public String getAcademicInterests() {
        return academicInterests;
    }
    public void setAcademicInterests(String academicInterests) {
        this.academicInterests = academicInterests;
    }
    public String getLinkedin() {
        return linkedin;
    }
    public void setLinkedin(String linkedin) {
        this.linkedin = linkedin;
    }
    public String getGithub() {
        return github;
    }
    public void setGithub(String github) {
        this.github = github;
    }
    public String getWebsite() {
        return website;
    }
    public void setWebsite(String website) {
        this.website = website;
    }
    public String getProfilePicture() {
        return profilePicture;
    }
    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }
    public String getDocumentUniversity() {
        return documentUniversity;
    }
    public void setDocumentUniversity(String documentUniversity) {
        this.documentUniversity = documentUniversity;
    }
    public Boolean getCompleteProfile() {
        return completeProfile;
    }
    public void setCompleteProfile(Boolean completeProfile) {
        this.completeProfile = completeProfile;
    }
    public Set<SkillDTO> getSkills() {
        return skills;
    }
    public void setSkills(Set<SkillDTO> skills) {
        this.skills = skills;
    }
}
