package com.hazmelaucb.ms_chat;

import com.hazmelaucb.ms_chat.dto.ConversacionDto;
import com.hazmelaucb.ms_chat.dto.IniciarConversacionRequest;
import com.hazmelaucb.ms_chat.api.ConversacionController;
import com.hazmelaucb.ms_chat.bl.ChatService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class ConversacionControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ChatService chatService;

    @InjectMocks
    private ConversacionController controller;

    @BeforeEach
    void setUp() {
        // Inicializa MockMvc en modo *stand-alone* con nuestro controller "inyectado"
        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .build();
    }

    @Test
    void ping_deberiaDevolverOK() throws Exception {
        mockMvc.perform(get("/api/conversacion/ping"))
                .andExpect(status().isOk())
                .andExpect(content().string("Microservicio de chat operativo 👌"));
    }

    @Test
    void iniciarConversacion_deberiaDevolverCreated() throws Exception {
        // 1) Preparamos el DTO que queremos que devuelva el mock
        ConversacionDto dto = new ConversacionDto(
                1L,                    // conversacionId
                1L,                    // user1Id
                2L,                    // user2Id
                LocalDateTime.now(),   // creado
                0L                      // seEnvio (ojo: Integer, no long)
        );
        when(chatService.iniciarConversacion(any(IniciarConversacionRequest.class)))
                .thenReturn(dto);

        // 2) Llamada al endpoint
        mockMvc.perform(post("/api/conversacion/iniciar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                              "user1Id": 1,
                              "user2Id": 2,
                              "senderId": 1,
                              "mensaje": "Test"
                            }
                            """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.conversacionId").value(1))
                .andExpect(jsonPath("$.data.user1Id").value(1))
                .andExpect(jsonPath("$.data.user2Id").value(2));
    }
}
