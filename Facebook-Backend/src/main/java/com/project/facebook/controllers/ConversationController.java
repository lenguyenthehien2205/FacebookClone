package com.project.facebook.controllers;


import com.project.facebook.models.Profile;
import com.project.facebook.models.User;
import com.project.facebook.responses.ResponseObject;
import com.project.facebook.responses.conversation.ConversationResponse;
import com.project.facebook.services.ConversationService;
import com.project.facebook.services.IConversationService;
import com.project.facebook.services.IProfileService;
import com.project.facebook.services.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("${api.base-path}/conversations")
@RequiredArgsConstructor
public class ConversationController {
    private final IConversationService conversationService;
    private final IProfileService profileService;
    @GetMapping("/{profile_id}") // Lấy tất cả các cuộc trò chuyện của một người dùng
    public ResponseEntity<ResponseObject> getConversationsByProfileId(@PathVariable("profile_id") Long profileId, Authentication authentication){
        try{
            User currentUser = (User) authentication.getPrincipal();
            // Kiểm tra xem userId có khớp với người dùng đang đăng nhập không
            Profile currentProfile = profileService.getProfileById(profileId);
            if (!currentUser.getUserId().equals(currentProfile.getUser().getUserId())) {
                return ResponseEntity.ok(ResponseObject.builder()
                        .message("Unauthorized")
                        .status(HttpStatus.FORBIDDEN).build());
            }
            List<ConversationResponse> conversationResponses = conversationService.getConversationsByProfileId(profileId);
            return ResponseEntity.ok(ResponseObject.builder()
                            .message("Conversations retrieved successfully")
                            .data(conversationResponses)
                            .status(HttpStatus.OK)
                    .build());
        }catch (Exception e){
            return ResponseEntity.badRequest().body(ResponseObject.builder()
                            .message(e.getMessage())
                            .status(HttpStatus.BAD_REQUEST)
                            .data(null)
                    .build());
        }
    }

    @GetMapping("/{profile_id1}/{profile_id2}") // Lấy cuộc trò chuyện giữa 2 người dùng
    public ResponseEntity<ResponseObject> getConversationByUsers(@PathVariable("profile_id1") Long profileId1, @PathVariable("profile_id2") Long profileId2, Authentication authentication){
        try{
            User currentUser = (User) authentication.getPrincipal();
            // Kiểm tra xem userId có khớp với người dùng đang đăng nhập không
            Profile currentProfile1 = profileService.getProfileById(profileId1);
            Profile currentProfile2 = profileService.getProfileById(profileId2);
            if (!currentUser.getUserId().equals(currentProfile1.getUser().getUserId()) || !currentUser.getUserId().equals(currentProfile2.getUser().getUserId())) {
                return ResponseEntity.ok(ResponseObject.builder()
                        .message("Unauthorized")
                        .status(HttpStatus.FORBIDDEN).build());
            }
            ConversationResponse conversationResponse = conversationService.getConversationByUsers(profileId1, profileId2);
            return ResponseEntity.ok(ResponseObject.builder()
                            .message("Conversation retrieved successfully")
                            .data(conversationResponse)
                            .status(HttpStatus.OK)
                    .build());
        }catch (Exception e){
            return ResponseEntity.badRequest().body(ResponseObject.builder()
                            .message(e.getMessage())
                            .status(HttpStatus.BAD_REQUEST)
                            .data(null)
                    .build());
        }
    }
}
