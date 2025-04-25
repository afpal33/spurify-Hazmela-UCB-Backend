package com.hazmelaucb.ms_chat.dao;


import com.hazmelaucb.ms_chat.entidy.ChatMensaje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMensajeRepository extends JpaRepository<ChatMensaje, Long> {
    // Ejemplo de método para buscar mensajes por conversacionId
    List<ChatMensaje> findByConversacionId(Long conversacionId);
}
