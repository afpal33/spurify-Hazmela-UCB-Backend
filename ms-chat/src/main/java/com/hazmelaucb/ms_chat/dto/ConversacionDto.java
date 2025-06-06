package com.hazmelaucb.ms_chat.dto;

import java.time.LocalDateTime;

public class ConversacionDto {
    private final Long conversacionId;
    private final Long user1Id;
    private final Long user2Id;
    private final LocalDateTime creado;
    private final Long seEnvio;

    public ConversacionDto(
            Long conversacionId,
            Long user1Id,
            Long user2Id,
            LocalDateTime creado,
            Long seEnvio        // <-- cambia Integer a Long aquí
    ) {
        this.conversacionId = conversacionId;
        this.user1Id = user1Id;
        this.user2Id = user2Id;
        this.creado = creado;
        this.seEnvio = seEnvio;  // <-- ya es Long, sin conversión
    }

    public Long getConversacionId() { return conversacionId; }
    public Long getUser1Id()        { return user1Id;      }
    public Long getUser2Id()        { return user2Id;      }
    public LocalDateTime getCreado(){ return creado;       }
    public Long getSeEnvio()        { return seEnvio;      }
}
