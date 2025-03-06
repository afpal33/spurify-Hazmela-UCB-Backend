package com.hazmelaucb.ms_user.bl;

import com.hazmelaucb.ms_user.dao.StudentRepository;
import com.hazmelaucb.ms_user.dao.UserRepository;
import com.hazmelaucb.ms_user.dto.StudentDTO;
import com.hazmelaucb.ms_user.entity.Student;
import com.hazmelaucb.ms_user.entity.User;
import com.hazmelaucb.ms_user.utils.BadRequestException;
import com.hazmelaucb.ms_user.utils.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class StudentBL {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private AuditService auditService;

    @Transactional
    public StudentDTO createStudent(StudentDTO studentDTO) {
        // Verificar que el email no exista
        if (userRepository.findByEmail(studentDTO.getEmail()).isPresent()) {
            throw new BadRequestException("El email ya existe");
        }

        // Crear entidad User
        User user = new User();
        user.setFirstName(studentDTO.getFirstName());
        user.setLastName(studentDTO.getLastName());
        user.setEmail(studentDTO.getEmail());
        user.setPhone(studentDTO.getPhone());
        user.setUserType("STUDENT");
        user = userRepository.save(user);

        // Crear entidad Student
        Student student = new Student();
        student.setUser(user);
        student.setAddress(studentDTO.getAddress());
        student.setBirthDate(studentDTO.getBirthDate() != null ? studentDTO.getBirthDate() : LocalDate.now());
        student.setGender(studentDTO.getGender());
        student.setCountry(studentDTO.getCountry());
        student.setCity(studentDTO.getCity());
        student.setUniversity(studentDTO.getUniversity());
        student.setCareer(studentDTO.getCareer());
        student.setSemester(studentDTO.getSemester());
        student.setAverage(studentDTO.getAverage());
        student.setStudyMode(studentDTO.getStudyMode());
        student.setExperience(studentDTO.getExperience());
        student.setHighlightedProjects(studentDTO.getHighlightedProjects());
        student.setCoursesCertifications(studentDTO.getCoursesCertifications());
        student.setAcademicInterests(studentDTO.getAcademicInterests());
        student.setLinkedin(studentDTO.getLinkedin());
        student.setGithub(studentDTO.getGithub());
        student.setWebsite(studentDTO.getWebsite());
        student.setProfilePicture(studentDTO.getProfilePicture());
        student.setDocumentUniversity(studentDTO.getDocumentUniversity());
        student.setCompleteProfile(studentDTO.getCompleteProfile() != null ? studentDTO.getCompleteProfile() : false);
        student = studentRepository.save(student);

        // Registrar auditoría (usando ServiceUtil internamente)
        auditService.createAudit(user.getUserId(), "CREATE", null);

        studentDTO.setUserId(user.getUserId());
        return studentDTO;
    }

    @Transactional
    public StudentDTO updateStudent(UUID userId, StudentDTO studentDTO) {
        Student student = studentRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Estudiante no encontrado"));

        // Actualizar datos del User
        User user = student.getUser();
        user.setFirstName(studentDTO.getFirstName());
        user.setLastName(studentDTO.getLastName());
        user.setPhone(studentDTO.getPhone());
        userRepository.save(user);

        // Actualizar datos del Student
        student.setAddress(studentDTO.getAddress());
        student.setBirthDate(studentDTO.getBirthDate());
        student.setGender(studentDTO.getGender());
        student.setCountry(studentDTO.getCountry());
        student.setCity(studentDTO.getCity());
        student.setUniversity(studentDTO.getUniversity());
        student.setCareer(studentDTO.getCareer());
        student.setSemester(studentDTO.getSemester());
        student.setAverage(studentDTO.getAverage());
        student.setStudyMode(studentDTO.getStudyMode());
        student.setExperience(studentDTO.getExperience());
        student.setHighlightedProjects(studentDTO.getHighlightedProjects());
        student.setCoursesCertifications(studentDTO.getCoursesCertifications());
        student.setAcademicInterests(studentDTO.getAcademicInterests());
        student.setLinkedin(studentDTO.getLinkedin());
        student.setGithub(studentDTO.getGithub());
        student.setWebsite(studentDTO.getWebsite());
        student.setProfilePicture(studentDTO.getProfilePicture());
        student.setDocumentUniversity(studentDTO.getDocumentUniversity());
        student.setCompleteProfile(studentDTO.getCompleteProfile());
        studentRepository.save(student);

        // Registrar auditoría
        auditService.createAudit(userId, "UPDATE", null);

        studentDTO.setUserId(userId);
        return studentDTO;
    }

    @Transactional
    public void deleteStudent(UUID userId) {
        Student student = studentRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Estudiante no encontrado"));

        // Registrar auditoría antes de eliminar
        auditService.createAudit(userId, "DELETE", null);
        studentRepository.delete(student);
        // La eliminación en cascada se encarga de borrar el User asociado
    }

    public StudentDTO getStudentById(UUID userId) {
        Student student = studentRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Estudiante no encontrado"));
        return convertStudentToDTO(student);
    }

    public List<StudentDTO> getAllStudents() {
        return studentRepository.findAll().stream()
                .map(this::convertStudentToDTO)
                .collect(Collectors.toList());
    }

    // Método auxiliar para convertir entidad a DTO
    private StudentDTO convertStudentToDTO(Student student) {
        StudentDTO dto = new StudentDTO();
        dto.setUserId(student.getUser().getUserId());
        dto.setFirstName(student.getUser().getFirstName());
        dto.setLastName(student.getUser().getLastName());
        dto.setEmail(student.getUser().getEmail());
        dto.setPhone(student.getUser().getPhone());
        dto.setAddress(student.getAddress());
        dto.setBirthDate(student.getBirthDate());
        dto.setGender(student.getGender());
        dto.setCountry(student.getCountry());
        dto.setCity(student.getCity());
        dto.setUniversity(student.getUniversity());
        dto.setCareer(student.getCareer());
        dto.setSemester(student.getSemester());
        dto.setAverage(student.getAverage());
        dto.setStudyMode(student.getStudyMode());
        dto.setExperience(student.getExperience());
        dto.setHighlightedProjects(student.getHighlightedProjects());
        dto.setCoursesCertifications(student.getCoursesCertifications());
        dto.setAcademicInterests(student.getAcademicInterests());
        dto.setLinkedin(student.getLinkedin());
        dto.setGithub(student.getGithub());
        dto.setWebsite(student.getWebsite());
        dto.setProfilePicture(student.getProfilePicture());
        dto.setDocumentUniversity(student.getDocumentUniversity());
        dto.setCompleteProfile(student.getCompleteProfile());
        return dto;
    }
}
