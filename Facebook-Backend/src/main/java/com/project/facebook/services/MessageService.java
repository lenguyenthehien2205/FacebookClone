package com.project.facebook.services;

import com.project.facebook.dtos.MessageDTO;
import com.project.facebook.models.Conversation;
import com.project.facebook.models.Message;
import com.project.facebook.models.PageBase;
import com.project.facebook.models.Profile;
import com.project.facebook.repositories.ConversationRepository;
import com.project.facebook.repositories.MessageRepository;
import com.project.facebook.responses.message.MessageOfConversationResponse;
import com.project.facebook.responses.message.MessageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService implements IMessageService {
    private final MessageRepository messageRepository;
    private final ConversationRepository conversationRepository;
    private final IPageBaseService pageBaseService;
    private final IProfileService profileService;
    @Override
    public void sendMessage(MessageDTO messageDTO) {
        Conversation conversation = conversationRepository.findById(messageDTO.getConversationId())
                .orElseThrow(() -> new RuntimeException("Conversation not found"));
        Message message = Message.builder()
                .senderId(messageDTO.getSenderId())
                .conversation(conversation)
                .content(messageDTO.getContent())
                .build();
        messageRepository.save(message);
    }

    @Override
    public MessageOfConversationResponse getMessages(Long conversationId, Long currentProfileId) throws Exception {
            Conversation conversation = conversationRepository.findById(conversationId)
                    .orElseThrow(() -> new RuntimeException("Conversation not found"));

            List<Message> messages = messageRepository.getMessagesByConversationId(conversationId);
            List<MessageResponse> messageResponses = messages.stream()
                    .map(MessageResponse::fromMessage)
                    .toList();

            MessageOfConversationResponse messageOfConversationResponse = MessageOfConversationResponse.builder()
                    .conversationId(conversationId)
                    .messages(messageResponses)
                    .build();
            Profile profile;
            PageBase pageBase;
            if(conversation.getPerson1().equals(currentProfileId)){
                profile = profileService.getProfileById(conversation.getPerson2());
                pageBase = pageBaseService.getPageBaseById(profile.getPageBase().getId());
            } else {
                profile = profileService.getProfileById(conversation.getPerson1());
                pageBase = pageBaseService.getPageBaseById(profile.getPageBase().getId());
            }
            messageOfConversationResponse.setAvatar(pageBase.getAvatar());
            messageOfConversationResponse.setFirstName(profile.getFirstName());
            messageOfConversationResponse.setLastName(profile.getLastName());
            messageOfConversationResponse.setDisplayFormat(profile.getDisplayFormat());
            return messageOfConversationResponse;
    }
}
