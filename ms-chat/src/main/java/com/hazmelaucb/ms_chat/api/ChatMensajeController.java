package com.hazmelaucb.ms_chat.api;

import com.hazmelaucb.ms_chat.bl.ChatService;
import com.hazmelaucb.ms_chat.entidy.ChatMensaje;
import com.hazmelaucb.ms_chat.utils.ResponseHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@Tag(name = "Mensajes", description = "Endpoints para gestionar los mensajes de chat")
@RestController
@RequestMapping("/api/mensajes")
@Validated
public class ChatMensajeController {

    private final ChatService chatService;

    public ChatMensajeController(ChatService chatService) {
        this.chatService = chatService;
    }

    @Operation(summary = "Obtener todos los mensajes")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Mensajes obtenidos correctamente")
    })
    @GetMapping
    public ResponseEntity<Object> obtenerTodosLosMensajes() {
        List<ChatMensaje> mensajes = chatService.obtenerTodosLosMensajes();
        return ResponseHandler.generateResponse("Mensajes obtenidos", HttpStatus.OK, mensajes);
    }

    @Operation(summary = "Obtener un mensaje por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Mensaje encontrado"),
            @ApiResponse(responseCode = "404", description = "Mensaje no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Object> obtenerMensajePorId(@Parameter(description = "ID del mensaje") @PathVariable Long id) {
        Optional<ChatMensaje> mensaje = chatService.obtenerMensajePorId(id);
        return mensaje.map(value -> ResponseHandler.generateResponse("Mensaje encontrado", HttpStatus.OK, value))
                .orElseGet(() -> ResponseHandler.generateResponse("Mensaje no encontrado", HttpStatus.NOT_FOUND, null));
    }

    @Operation(summary = "Crear un nuevo mensaje")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Mensaje creado con éxito")
    })
    @PostMapping
    public ResponseEntity<Object> crearMensaje(@Valid @RequestBody ChatMensaje mensaje) {
        ChatMensaje nuevoMensaje = chatService.crearMensaje(mensaje);
        return ResponseHandler.generateResponse("Mensaje creado", HttpStatus.CREATED, nuevoMensaje);
    }

    @Operation(summary = "Eliminar un mensaje por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Mensaje eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Mensaje no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> eliminarMensaje(@Parameter(description = "ID del mensaje a eliminar") @PathVariable Long id) {
        boolean eliminado = chatService.eliminarMensaje(id);
        return eliminado
                ? ResponseHandler.generateResponse("Mensaje eliminado", HttpStatus.OK, null)
                : ResponseHandler.generateResponse("Mensaje no encontrado", HttpStatus.NOT_FOUND, null);
    }
}
