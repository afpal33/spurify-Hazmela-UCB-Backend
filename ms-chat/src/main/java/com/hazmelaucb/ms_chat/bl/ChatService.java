package com.hazmelaucb.ms_chat.bl;

import com.hazmelaucb.ms_chat.dao.ChatMensajeRepository;
import com.hazmelaucb.ms_chat.dao.ConversacionRepository;
import com.hazmelaucb.ms_chat.dto.ConversacionDto;
import com.hazmelaucb.ms_chat.dto.IniciarConversacionRequest;
import com.hazmelaucb.ms_chat.entidy.ChatMensaje;
import com.hazmelaucb.ms_chat.entidy.Conversacion;
import jakarta.transaction.Transactional;
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

    @Transactional
    public ConversacionDto iniciarConversacion(IniciarConversacionRequest req) {
        // 1) Si ya existe, la devolvemos
        var lista = conversacionRepository.findByUsers(req.getUser1Id(), req.getUser2Id());
        if (!lista.isEmpty()) {
            return mapToDto(lista.get(0));
        }

        // 2) Si no existe, la creamos
        Conversacion nueva = new Conversacion();
        nueva.setUser1Id(req.getUser1Id());
        nueva.setUser2Id(req.getUser2Id());
        nueva.setSeEnvio(0);
        // el campo 'creado' se rellenará con la timestamp por defecto de la entidad
        Conversacion guardada = conversacionRepository.save(nueva);

        return mapToDto(guardada);
    }

    private ConversacionDto mapToDto(Conversacion c) {
        Long seEnvioLong = c.getSeEnvio() == null
                ? 0L
                : c.getSeEnvio().longValue();

        return new ConversacionDto(
                c.getConversacionId(),
                c.getUser1Id(),
                c.getUser2Id(),
                c.getCreado(),
                seEnvioLong      // ya es Long
        );
    }


}
