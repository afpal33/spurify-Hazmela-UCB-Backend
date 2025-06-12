package com.hazmelaucb.ms_user.api;

import com.hazmelaucb.ms_user.bl.StudentBL;
import com.hazmelaucb.ms_user.dto.StudentDTO;
import com.hazmelaucb.ms_user.dto.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/students")
@Tag(name = "Student", description = "API para la gestión de estudiantes en la red social")
public class StudentController {

    @Autowired
    private StudentBL studentBL;

    @Operation(summary = "${api.student.create.description}", description = "${api.student.create.notes}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "${api.responseCodes.created.description}"),
            @ApiResponse(responseCode = "400", description = "${api.responseCodes.badRequest.description}"),
            @ApiResponse(responseCode = "409", description = "${api.responseCodes.conflict.description}")
    })
    @PostMapping
    public ResponseEntity<StudentDTO> createStudent(@RequestBody StudentDTO studentDTO) {
        StudentDTO created = studentBL.createStudent(studentDTO);
        return ResponseEntity.status(201).body(created);
    }

    @Operation(summary = "${api.student.update.description}", description = "${api.student.update.notes}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "${api.responseCodes.ok.description}"),
            @ApiResponse(responseCode = "400", description = "${api.responseCodes.badRequest.description}"),
            @ApiResponse(responseCode = "404", description = "${api.responseCodes.notFound.description}")
    })
    @PutMapping("/{id}")
    public ResponseEntity<StudentDTO> updateStudent(
            @Parameter(description = "ID del estudiante a actualizar", required = true)
            @PathVariable("id") UUID userId,
            @RequestBody StudentDTO studentDTO) {
        StudentDTO updated = studentBL.updateStudent(userId, studentDTO);
        return ResponseEntity.ok(updated);
    }

    @Operation(summary = "${api.student.delete.description}", description = "${api.student.delete.notes}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "${api.responseCodes.noContent.description}"),
            @ApiResponse(responseCode = "404", description = "${api.responseCodes.notFound.description}"),
            @ApiResponse(responseCode = "422", description = "${api.responseCodes.unprocessableEntity.description}")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<SuccessResponse> deleteStudent(
            @Parameter(description = "ID del estudiante a eliminar", required = true)
            @PathVariable("id") UUID userId) {
        SuccessResponse response = studentBL.deleteStudent(userId).getBody();
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "${api.student.getById.description}", description = "${api.student.getById.notes}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "${api.responseCodes.ok.description}"),
            @ApiResponse(responseCode = "400", description = "${api.responseCodes.badRequest.description}"),
            @ApiResponse(responseCode = "404", description = "${api.responseCodes.notFound.description}")
    })
    @GetMapping("/{id}")
    public ResponseEntity<StudentDTO> getStudentById(
            @Parameter(description = "ID del estudiante a obtener", required = true)
            @PathVariable("id") UUID userId) {
        StudentDTO dto = studentBL.getStudentById(userId);
        return ResponseEntity.ok(dto);
    }

    @Operation(summary = "${api.student.getAll.description}", description = "${api.student.getAll.notes}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "${api.responseCodes.ok.description}"),
            @ApiResponse(responseCode = "500", description = "Error en el servidor")
    })
    @GetMapping
    public ResponseEntity<List<StudentDTO>> getAllStudents() {
        List<StudentDTO> dtos = studentBL.getAllStudents();
        return ResponseEntity.ok(dtos);
    }

    @Operation(summary = "Obtener estudiante por email", description = "Retorna los datos del estudiante correspondiente al email dado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estudiante encontrado"),
            @ApiResponse(responseCode = "404", description = "Estudiante no encontrado")
    })
    @GetMapping("/by-email")
    public ResponseEntity<StudentDTO> getStudentByEmail(
            @Parameter(description = "Email del estudiante", required = true)
            @RequestParam("email") String email) {
        StudentDTO dto = studentBL.getStudentByEmail(email);
        return ResponseEntity.ok(dto);
    }

}
