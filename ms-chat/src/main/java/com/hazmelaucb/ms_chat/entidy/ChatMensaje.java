package com.hazmelaucb.ms_chat.entidy;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Positive;
import java.time.LocalDateTime;

@Entity
@Table(name = "chat_mensaje")
public class ChatMensaje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mensaje_id")
    private Long mensajeId;

    @NotNull(message = "El senderId no puede ser nulo")
    @Positive(message = "El senderId debe ser un número positivo")
    @Column(name = "sender_id", nullable = false)
    private Long senderId;

    @NotNull(message = "El contenido del mensaje no puede ser nulo")
    @Size(min = 1, max = 500, message = "El contenido debe tener entre 1 y 500 caracteres")
    @Column(name = "contenido", nullable = false)
    private String contenido;

    @Column(name = "creacion")
    private LocalDateTime creacion;

    @Column(name = "leido")
    private LocalDateTime leido;

    @NotNull(message = "El conversacionId no puede ser nulo")
    @Positive(message = "El conversacionId debe ser un número positivo")
    @Column(name = "conversacion_id", nullable = false)
    private Long conversacionId;

    @Column(name = "eliminado")
    private LocalDateTime eliminado;

    // Constructor vacío
    public ChatMensaje() {}

    // Getters y Setters
    public Long getMensajeId() {
        return mensajeId;
    }

    public void setMensajeId(Long mensajeId) {
        this.mensajeId = mensajeId;
    }

    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public LocalDateTime getCreacion() {
        return creacion;
    }

    public void setCreacion(LocalDateTime creacion) {
        this.creacion = creacion;
    }

    public LocalDateTime getLeido() {
        return leido;
    }

    public void setLeido(LocalDateTime leido) {
        this.leido = leido;
    }

    public Long getConversacionId() {
        return conversacionId;
    }

    public void setConversacionId(Long conversacionId) {
        this.conversacionId = conversacionId;
    }

    public LocalDateTime getEliminado() {
        return eliminado;
    }

    public void setEliminado(LocalDateTime eliminado) {
        this.eliminado = eliminado;
    }

    public void setConversacion(Conversacion conversacion) {
    }
}
