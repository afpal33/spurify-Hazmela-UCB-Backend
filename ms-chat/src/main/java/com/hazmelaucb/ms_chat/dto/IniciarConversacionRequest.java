package com.hazmelaucb.ms_chat.dto;

import jakarta.validation.constraints.NotNull;

public class IniciarConversacionRequest {
    @NotNull(message = "El user1Id es obligatorio")
    private Long user1Id;

    @NotNull(message = "El user2Id es obligatorio")
    private Long user2Id;

    @NotNull(message = "El senderId es obligatorio")
    private Long senderId;

    @NotNull(message = "El mensaje no puede estar vacío")
    private String mensaje;

    // Getters y Setters
    public Long getUser1Id() { return user1Id; }
    public void setUser1Id(Long user1Id) { this.user1Id = user1Id; }

    public Long getUser2Id() { return user2Id; }
    public void setUser2Id(Long user2Id) { this.user2Id = user2Id; }

    public Long getSenderId() { return senderId; }
    public void setSenderId(Long senderId) { this.senderId = senderId; }

    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }
}
