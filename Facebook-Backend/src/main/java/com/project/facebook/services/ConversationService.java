package com.project.facebook.services;

import com.project.facebook.exceptions.DataNotFoundException;
import com.project.facebook.models.Conversation;
import com.project.facebook.models.Message;
import com.project.facebook.models.PageBase;
import com.project.facebook.models.Profile;
import com.project.facebook.repositories.ConversationRepository;
import com.project.facebook.repositories.FriendRepository;
import com.project.facebook.repositories.PageBaseRepository;
import com.project.facebook.repositories.ProfileRepository;
import com.project.facebook.responses.conversation.ConversationResponse;
import com.project.facebook.responses.profile.ProfileTagResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ConversationService implements IConversationService{
    private final FriendRepository friendRepository;
    private final ProfileRepository profileRepository;
    private final ConversationRepository conversationRepository;
    private final PageBaseRepository pageBaseRepository;
    @Override
    public List<ConversationResponse> getConversationsByProfileId(Long profileId) throws Exception{
        Profile existingProfile = profileRepository.findById(profileId).orElseThrow(
                () -> new DataNotFoundException("User not found")
        );
        List<ConversationResponse> conversationResponses = friendRepository.findAllFriendsByProfileId(profileId)
                .stream()
                .map(profile -> {
                    Optional<Conversation> conversation = conversationRepository.findConversationByUsers(profileId, profile.getId());
                    if (conversation.isPresent()){
                        ConversationResponse conversationResponse = ConversationResponse.fromProfile(profile, conversation.get().getId());
                        Optional<PageBase> pageBaseOpt = pageBaseRepository.findById(profile.getPageBase().getId());
                        if(pageBaseOpt.isPresent()){
                            PageBase pageBase = pageBaseOpt.get();
                            conversationResponse.setAvatar(pageBase.getAvatar());
                        }
                        conversationResponse.setCreatedAt(conversation.get().getCreatedAt());
                        conversationResponse.setUpdatedAt(conversation.get().getUpdatedAt());
                        conversationResponse.setProfileId(profile.getId());
                        return conversationResponse;
                    }
                    return null;
                })
                .filter(Objects::nonNull) // Loại bỏ các giá trị null
                .sorted(Comparator.comparing(ConversationResponse::getUpdatedAt, Comparator.nullsLast(Comparator.reverseOrder()))) // Sắp xếp giảm dần
                .collect(Collectors.toList());
        return conversationResponses;
    }

    @Override
    public ConversationResponse getConversationByUsers(Long profileId1, Long profileId2) throws Exception{
        Profile existingProfile1 = profileRepository.findById(profileId1).orElseThrow(
                () -> new DataNotFoundException("User not found")
        );
        Profile existingProfile2 = profileRepository.findById(profileId2).orElseThrow(
                () -> new DataNotFoundException("User not found")
        );
        Optional<Conversation> conversation = conversationRepository.findConversationByUsers(profileId1, profileId2);

        if(conversation.isPresent()){
            ConversationResponse conversationResponse = ConversationResponse.fromProfile(existingProfile2, conversation.get().getId());
            conversationResponse.setProfileId(profileId2);
            return conversationResponse;
        }else {
            throw new DataNotFoundException("Conversation not found");
        }
    }

    @Override
    public Conversation getConversationById(Long conversationId) throws Exception {
        Optional<Conversation> conversation = conversationRepository.findById(conversationId);
        if(conversation.isPresent()){
            return conversation.get();
        }else {
            throw new DataNotFoundException("Conversation not found");
        }
    }

    @Override
    public Long getConversationIdByUsers(Long profileId1, Long profileId2) throws Exception {
        return conversationRepository.findConversationByUsers(profileId1, profileId2)
                .orElseThrow(() -> new DataNotFoundException("Conversation not found"))
                .getId();
    }
}
