package com.project.facebook.controllers;

import com.project.facebook.models.Conversation;
import com.project.facebook.models.Message;
import com.project.facebook.models.Profile;
import com.project.facebook.models.User;
import com.project.facebook.responses.ResponseObject;
import com.project.facebook.responses.conversation.ConversationResponse;
import com.project.facebook.responses.message.MessageOfConversationResponse;
import com.project.facebook.responses.message.MessageResponse;
import com.project.facebook.services.IConversationService;
import com.project.facebook.services.IMessageService;
import com.project.facebook.services.IProfileService;
import com.project.facebook.services.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("${api.base-path}/messages")
@RequiredArgsConstructor
public class MessageController {
    private final IMessageService messageService;
    private final IConversationService conversationService;
    private final IProfileService profileService;
    @GetMapping("/{conversation_id}")
    public ResponseEntity<ResponseObject> getMessages(@PathVariable("conversation_id") Long conversationId, Authentication authentication){
        try{
            User currentUser = (User) authentication.getPrincipal();
            Conversation conversation = conversationService.getConversationById(conversationId);
            Long profileId1 = conversation.getPerson1();
            Long profileId2 = conversation.getPerson2();
            Long userId1 = profileService.getProfileById(profileId1).getUser().getUserId();
            Long userId2 = profileService.getProfileById(profileId2).getUser().getUserId();
            Long currentProfileId;
            if(currentUser.getUserId().equals(userId1)){
                currentProfileId = profileId1;
            }else if(currentUser.getUserId().equals(userId2)){
                currentProfileId = profileId2;
            }else{
                return ResponseEntity.ok(ResponseObject.builder()
                        .message("Unauthorized")
                        .status(HttpStatus.FORBIDDEN)
                        .build());
            }
            MessageOfConversationResponse messageOfConversationResponse = messageService.getMessages(conversationId, currentProfileId);
            return ResponseEntity.ok(ResponseObject.builder()
                    .message("Messages retrieved successfully")
                    .data(messageOfConversationResponse)
                    .status(HttpStatus.OK)
                    .build());
        } catch (Exception e){
            return ResponseEntity.badRequest().body(ResponseObject.builder()
                    .message(e.getMessage())
                    .status(HttpStatus.BAD_REQUEST)
                    .data(null)
                    .build());
        }
    }
}
