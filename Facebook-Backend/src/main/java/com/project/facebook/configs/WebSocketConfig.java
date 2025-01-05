package com.project.facebook.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) { // dang ky endpoint
        registry.addEndpoint("/api/v1/ws").setAllowedOrigins("*");
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) { // cau hinh message broker
        registry.setApplicationDestinationPrefixes("/app"); // prefix cho cac message tu client gui len
        registry.enableSimpleBroker("/topic"); // prefix cho cac message tu server gui xuong
    }
}
