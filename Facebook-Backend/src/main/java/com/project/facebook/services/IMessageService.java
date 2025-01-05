package com.project.facebook.services;

import com.project.facebook.dtos.MessageDTO;
import com.project.facebook.models.Message;
import com.project.facebook.responses.message.MessageOfConversationResponse;
import com.project.facebook.responses.message.MessageResponse;

import java.util.List;

public interface IMessageService {
    void sendMessage(MessageDTO messageDTO);
    MessageOfConversationResponse getMessages(Long conversationId, Long currentProfileId) throws Exception;
}
