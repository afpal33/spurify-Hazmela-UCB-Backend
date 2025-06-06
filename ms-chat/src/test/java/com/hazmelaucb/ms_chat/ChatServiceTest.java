package com.hazmelaucb.ms_chat;

import com.hazmelaucb.ms_chat.bl.ChatService;
import com.hazmelaucb.ms_chat.dto.IniciarConversacionRequest;
import com.hazmelaucb.ms_chat.dto.ConversacionDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class ChatServiceTest {

    @Autowired
    private ChatService chatService;

    @Test
    void iniciarConversacion_deberíaCrearYDevolverDto() {
        // Given
        var req = new IniciarConversacionRequest();
        req.setUser1Id(1L);
        req.setUser2Id(2L);
        req.setSenderId(1L);
        req.setMensaje("Hola prueba");

        // When
        ConversacionDto dto = chatService.iniciarConversacion(req);

        // Then
        assertThat(dto.getConversacionId()).isNotNull();
        assertThat(dto.getUser1Id()).isEqualTo(1L);
        assertThat(dto.getUser2Id()).isEqualTo(2L);
        assertThat(dto.getSeEnvio()).isEqualTo(0);
    }
}
