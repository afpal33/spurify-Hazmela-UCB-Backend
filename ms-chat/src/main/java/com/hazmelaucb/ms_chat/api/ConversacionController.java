package com.hazmelaucb.ms_chat.api;

import com.hazmelaucb.ms_chat.entidy.Conversacion;
import com.hazmelaucb.ms_chat.entidy.ChatMensaje;
import com.hazmelaucb.ms_chat.dao.ConversacionRepository;
import com.hazmelaucb.ms_chat.dao.ChatMensajeRepository;
import com.hazmelaucb.ms_chat.dto.IniciarConversacionRequest;
import com.hazmelaucb.ms_chat.utils.ResponseHandler;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.Parameter;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Tag(name = "Conversaciones", description = "Operaciones relacionadas con conversaciones de chat")
@RestController
@RequestMapping("/api/conversacion")
public class ConversacionController {

    @Autowired
    private ConversacionRepository conversacionRepository;

    @Autowired
    private ChatMensajeRepository mensajeRepository;

    @Operation(summary = "Iniciar una conversación con un mensaje", description = "Si la conversación ya existe, envía un mensaje; si no, la crea.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Conversación iniciada con mensaje"),
            @ApiResponse(responseCode = "400", description = "Error de validación")
    })
    @PostMapping("/iniciar")
    public ResponseEntity<Object> iniciarConversacion(@Valid @RequestBody IniciarConversacionRequest request) {
        List<Conversacion> conversacionesExistentes = conversacionRepository.findByUser1IdAndUser2Id(
                request.getUser1Id(), request.getUser2Id());

        Conversacion conversacion;

        if (!conversacionesExistentes.isEmpty()) {
            conversacion = conversacionesExistentes.get(0);
        } else {
            conversacion = new Conversacion();
            conversacion.setUser1Id(request.getUser1Id());
            conversacion.setUser2Id(request.getUser2Id());
            conversacion.setCreado(LocalDateTime.now());
            conversacion.setSeEnvio(0);
            conversacion = conversacionRepository.save(conversacion);
        }

        if (!request.getUser1Id().equals(request.getSenderId()) && !request.getUser2Id().equals(request.getSenderId())) {
            return ResponseHandler.generateResponse("El senderId no pertenece a esta conversación", HttpStatus.BAD_REQUEST, null);
        }

        ChatMensaje mensaje = new ChatMensaje();
        mensaje.setSenderId(request.getSenderId());
        mensaje.setContenido(request.getMensaje());
        mensaje.setCreacion(LocalDateTime.now());
        mensaje.setConversacion(conversacion);

        mensajeRepository.save(mensaje);

        return ResponseHandler.generateResponse("Conversación iniciada con mensaje", HttpStatus.CREATED, conversacion);
    }

    @Operation(summary = "Crear una conversación sin mensaje")
    @ApiResponse(responseCode = "201", description = "Conversación creada")
    @PostMapping
    public ResponseEntity<Object> create(@Valid @RequestBody Conversacion conversacion) {
        if (conversacion.getCreado() == null) {
            conversacion.setCreado(LocalDateTime.now());
        }
        if (conversacion.getSeEnvio() == null) {
            conversacion.setSeEnvio(0);
        }
        Conversacion nuevaConversacion = conversacionRepository.save(conversacion);
        return ResponseHandler.generateResponse("Conversación creada", HttpStatus.CREATED, nuevaConversacion);
    }

    @Operation(summary = "Obtener todas las conversaciones")
    @ApiResponse(responseCode = "200", description = "Lista de conversaciones")
    @GetMapping
    public ResponseEntity<Object> getAll() {
        List<Conversacion> conversaciones = conversacionRepository.findAll();
        return ResponseHandler.generateResponse("Lista de conversaciones", HttpStatus.OK, conversaciones);
    }

    @Operation(summary = "Obtener una conversación por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Conversación encontrada"),
            @ApiResponse(responseCode = "404", description = "Conversación no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@Parameter(description = "ID de la conversación") @PathVariable Long id) {
        return conversacionRepository.findById(id)
                .map(conversacion -> ResponseHandler.generateResponse("Conversación encontrada", HttpStatus.OK, conversacion))
                .orElseGet(() -> ResponseHandler.generateResponse("Conversación no encontrada", HttpStatus.NOT_FOUND, null));
    }

    @Operation(summary = "Eliminar una conversación")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Conversación eliminada"),
            @ApiResponse(responseCode = "404", description = "Conversación no encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteById(@Parameter(description = "ID de la conversación") @PathVariable Long id) {
        return conversacionRepository.findById(id)
                .map(conversacion -> {
                    conversacionRepository.delete(conversacion);
                    return ResponseHandler.generateResponse("Conversación eliminada", HttpStatus.OK, null);
                })
                .orElseGet(() -> ResponseHandler.generateResponse("Conversación no encontrada", HttpStatus.NOT_FOUND, null));
    }

    @Operation(summary = "Obtener todas las conversaciones de un usuario")
    @ApiResponse(responseCode = "200", description = "Conversaciones del usuario")
    @GetMapping("/usuario/{userId}")
    public ResponseEntity<Object> getConversacionesPorUsuario(@Parameter(description = "ID del usuario") @PathVariable Long userId) {
        List<Conversacion> conversaciones = conversacionRepository.findByUser1IdOrUser2Id(userId, userId);
        return ResponseHandler.generateResponse("Conversaciones del usuario", HttpStatus.OK, conversaciones);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        return ResponseHandler.generateResponse("Error de validación", HttpStatus.BAD_REQUEST, errors);
    }
}
