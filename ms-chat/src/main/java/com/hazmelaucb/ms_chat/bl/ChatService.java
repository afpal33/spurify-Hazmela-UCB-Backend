package com.hazmelaucb.ms_chat.bl;

import com.hazmelaucb.ms_chat.dao.ChatMensajeRepository;
import com.hazmelaucb.ms_chat.dao.ConversacionRepository;
import com.hazmelaucb.ms_chat.entidy.ChatMensaje;
import com.hazmelaucb.ms_chat.entidy.Conversacion;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ChatService {

    private final ChatMensajeRepository chatMensajeRepository;
    private final ConversacionRepository conversacionRepository;

    public ChatService(ChatMensajeRepository chatMensajeRepository, ConversacionRepository conversacionRepository) {
        this.chatMensajeRepository = chatMensajeRepository;
        this.conversacionRepository = conversacionRepository;
    }

    // Obtener todos los mensajes
    public List<ChatMensaje> obtenerTodosLosMensajes() {
        return chatMensajeRepository.findAll();
    }

    // Obtener un mensaje por ID
    public Optional<ChatMensaje> obtenerMensajePorId(Long id) {
        return chatMensajeRepository.findById(id);
    }

    // Crear un mensaje
    public ChatMensaje crearMensaje(ChatMensaje mensaje) {
        if (mensaje.getCreacion() == null) {
            mensaje.setCreacion(LocalDateTime.now());
        }

        // Guardar el mensaje
        ChatMensaje saved = chatMensajeRepository.save(mensaje);

        // Actualizar contador de mensajes en la conversación
        Long convId = mensaje.getConversacionId();
        Optional<Conversacion> optConv = conversacionRepository.findById(convId);

        if (optConv.isPresent()) {
            Conversacion conv = optConv.get();
            conv.setSeEnvio((conv.getSeEnvio() == null ? 0 : conv.getSeEnvio()) + 1);
            conversacionRepository.save(conv);
        }

        return saved;
    }

    // Eliminar un mensaje por ID
    public boolean eliminarMensaje(Long id) {
        if (chatMensajeRepository.existsById(id)) {
            chatMensajeRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
