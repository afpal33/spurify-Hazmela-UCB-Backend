package com.hazmelaucb.ms_user.api;


import com.hazmelaucb.ms_user.bl.StudentBL;
import com.hazmelaucb.ms_user.dto.StudentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    @Autowired
    private StudentBL studentBL;

    @PostMapping
    public ResponseEntity<StudentDTO> createStudent(@RequestBody StudentDTO studentDTO) {
        StudentDTO created = studentBL.createStudent(studentDTO);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentDTO> updateStudent(@PathVariable("id") UUID userId,
                                                    @RequestBody StudentDTO studentDTO) {
        StudentDTO updated = studentBL.updateStudent(userId, studentDTO);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable("id") UUID userId) {
        studentBL.deleteStudent(userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentDTO> getStudentById(@PathVariable("id") UUID userId) {
        StudentDTO dto = studentBL.getStudentById(userId);
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<List<StudentDTO>> getAllStudents() {
        List<StudentDTO> dtos = studentBL.getAllStudents();
        return ResponseEntity.ok(dtos);
    }
}
