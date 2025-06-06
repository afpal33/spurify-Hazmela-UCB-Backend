package com.hazmelaucb.ms_chat.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // Prefijos para el broker interno
        config.enableSimpleBroker("/topic");
        // Prefijo para los mensajes que envían los clientes
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // Endpoint para la conexión WebSocket
        registry.addEndpoint("/ws")
                .setAllowedOriginPatterns("*") // Ajusta CORS según necesites
                .withSockJS(); // Habilita SockJS como fallback
    }
}