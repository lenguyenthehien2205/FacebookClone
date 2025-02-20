package com.project.facebook.services;

import com.project.facebook.models.Conversation;
import com.project.facebook.models.Message;
import com.project.facebook.responses.conversation.ConversationResponse;

import java.util.List;
import java.util.Optional;

public interface IConversationService {
    List<ConversationResponse> getConversationsByProfileId(Long profileId) throws Exception;
    ConversationResponse getConversationByUsers(Long profileId1, Long profileId2) throws Exception;
    Conversation getConversationById(Long conversationId) throws Exception;
    Long getConversationIdByUsers(Long profileId1, Long profileId2) throws Exception;
}
