package com.hazmelaucb.ms_chat.api;


import com.hazmelaucb.ms_chat.entidy.ChatMensaje;
import com.hazmelaucb.ms_chat.dao.ChatMensajeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;

@Controller
public class ChatWebSocketController {

    @Autowired
    private ChatMensajeRepository chatMensajeRepository;

    // El cliente envía un mensaje a "/app/chat.sendMessage"
    @MessageMapping("/chat.sendMessage")
    // El servidor reenvía a "/topic/public" para todos los suscriptores
    @SendTo("/topic/public")
    public ChatMensaje sendMessage(ChatMensaje chatMensaje) {
        // Asigna fecha de creación
        chatMensaje.setCreacion(LocalDateTime.now());

        // Guarda en la base de datos
        ChatMensaje saved = chatMensajeRepository.save(chatMensaje);

        // Retorna el mensaje para que se distribuya a todos los suscriptores
        return saved;
    }
}