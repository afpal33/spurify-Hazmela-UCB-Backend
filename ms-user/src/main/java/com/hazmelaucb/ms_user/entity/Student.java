package com.hazmelaucb.ms_user.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "student")
public class Student {

    @Id
    @Column(name = "user_id")
    private UUID id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "address", nullable = false, columnDefinition = "TEXT")
    private String address;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @Column(name = "gender", length = 20)
    private String gender; // 'MALE', 'FEMALE', 'OTHER'

    @Column(name = "country", length = 100, nullable = false)
    private String country;

    @Column(name = "city", length = 100, nullable = false)
    private String city;

    @Column(name = "university", length = 255, nullable = false)
    private String university;

    @Column(name = "career", length = 255, nullable = false)
    private String career;

    @Column(name = "semester")
    private Integer semester;

    // Cambiado a BigDecimal para usar precision y scale
    @Column(name = "average", precision = 3, scale = 2)
    private BigDecimal average; // GPA entre 0 y 10

    @Column(name = "study_mode", length = 50)
    private String studyMode; // 'ON-CAMPUS', 'ONLINE', 'HYBRID'

    @Column(name = "experience", columnDefinition = "TEXT")
    private String experience;

    @Column(name = "highlighted_projects", columnDefinition = "TEXT")
    private String highlightedProjects;

    @Column(name = "courses_certifications", columnDefinition = "TEXT")
    private String coursesCertifications;

    @Column(name = "academic_interests", columnDefinition = "TEXT")
    private String academicInterests;

    @Column(name = "linkedin", columnDefinition = "TEXT")
    private String linkedin;

    @Column(name = "github", columnDefinition = "TEXT")
    private String github;

    @Column(name = "website", columnDefinition = "TEXT")
    private String website;

    @Column(name = "profile_picture", columnDefinition = "TEXT")
    private String profilePicture;

    @Column(name = "document_university", columnDefinition = "TEXT")
    private String documentUniversity;

    @Column(name = "complete_profile")
    private Boolean completeProfile = false;

    @ManyToMany
    @JoinTable(
            name = "student_skill",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id")
    )
    private Set<Skill> skills = new HashSet<>();

    // Getters y setters

    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
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
    public Set<Skill> getSkills() {
        return skills;
    }
    public void setSkills(Set<Skill> skills) {
        this.skills = skills;
    }
}
