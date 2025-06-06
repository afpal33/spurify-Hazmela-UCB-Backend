package com.hazmelaucb.ms_chat.entidy;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "conversacion")  // Debe coincidir con tu tabla en PostgreSQL
public class Conversacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "conversacion_id")
    private Long conversacionId;

    @Column(name = "user1_id", nullable = false)
    @NotNull(message = "El user1Id es obligatorio")
    @Min(value = 1, message = "El user1Id debe ser mayor a 0")
    private Long user1Id;

    @Column(name = "user2_id", nullable = false)
    @NotNull(message = "El user2Id es obligatorio")
    private Long user2Id;

    @Column(name = "creado")
    private LocalDateTime creado;

    @Column(name = "se_envio")
    private Integer seEnvio;

    // Getters y Setters
    public Long getConversacionId() {
        return conversacionId;
    }

    public void setConversacionId(Long conversacionId) {
        this.conversacionId = conversacionId;
    }

    public Long getUser1Id() {
        return user1Id;
    }

    public void setUser1Id(Long user1Id) {
        this.user1Id = user1Id;
    }

    public Long getUser2Id() {
        return user2Id;
    }

    public void setUser2Id(Long user2Id) {
        this.user2Id = user2Id;
    }

    public LocalDateTime getCreado() {
        return creado;
    }

    public void setCreado(LocalDateTime creado) {
        this.creado = creado;
    }

    public Integer getSeEnvio() {
        return seEnvio;
    }

    public void setSeEnvio(Integer seEnvio) {
        this.seEnvio = seEnvio;
    }


}
