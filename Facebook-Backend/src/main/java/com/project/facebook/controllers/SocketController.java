package com.project.facebook.controllers;

import com.project.facebook.dtos.MessageDTO;
import com.project.facebook.services.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SocketController {
    private final MessageService messageService;
    @MessageMapping("/send-message")
    @SendTo("/topic/messages")
    public MessageDTO sendMessage(MessageDTO message) {
        messageService.sendMessage(message);
        return message;
    }
}
