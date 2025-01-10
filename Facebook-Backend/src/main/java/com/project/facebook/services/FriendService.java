package com.project.facebook.services;

import com.project.facebook.exceptions.AlreadyExistsException;
import com.project.facebook.exceptions.DataNotFoundException;
import com.project.facebook.models.*;
import com.project.facebook.repositories.*;
import com.project.facebook.responses.profile.ProfileTagResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class FriendService implements IFriendService{
    private final FriendRepository friendRepository;
    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;
    private final PageBaseRepository pageBaseRepository;
    private final ConversationRepository conversationRepository;
    @Override
    public Friend addFriend(Long senderId, Long receiverId) throws Exception{
        int friendship = friendRepository.existsFriendship(senderId, receiverId);
        if(friendship == 0){
            Profile sender = profileRepository.findById(senderId).orElseThrow(() -> new DataNotFoundException("Sender not found"));
            Profile receiver = profileRepository.findById(receiverId).orElseThrow(() -> new DataNotFoundException("Receiver not found"));
            Friend friend = new Friend();
//            // sap xep (id nho hon dung truoc)
//            Profile profile1 = sender.getId() < receiver.getId() ? sender : receiver;
//            Profile profile2 = sender.getId() > receiver.getId() ? sender : receiver;
            friend.setSenderProfile(sender);
            friend.setReceiverProfile(receiver);
            return friendRepository.save(friend);
        }else if(friendship == 1){
            Optional<Friend> friendOpt = friendRepository.findFriendshipByUsers(senderId, receiverId);
            if(friendOpt.isPresent()) {
                Optional<Conversation> conversationOptional = conversationRepository.findConversationByUsers(senderId, receiverId);
                if(conversationOptional.isEmpty()){
                    conversationRepository.save(Conversation.builder()
                            .person1(senderId)
                            .person2(receiverId)
                            .build());
                }
                Friend friend = friendOpt.get();
                friend.setActive(true);
                return friendRepository.save(friend);
            }else{
                throw new DataNotFoundException("Friendship not found");
            }
        }else{
            throw new AlreadyExistsException("Friendship already exists");
        }
    }

    @Override
    public List<ProfileTagResponse> getAllFriendsByProfileId(Long profileId) throws Exception {
        Profile existingProfile = profileRepository.findById(profileId).orElseThrow(
                () -> new DataNotFoundException("User not found")
        );
        List<ProfileTagResponse> profileTagResponses = friendRepository.findAllFriendsByProfileId(profileId)
                .stream()
                .map(profile -> {
                    ProfileTagResponse profileTagResponse = ProfileTagResponse.fromProfile(profile);
                    Optional<PageBase> pageBaseOpt = pageBaseRepository.findById(profile.getPageBase().getId());
                    if (pageBaseOpt.isPresent()){
                        PageBase pageBase = pageBaseOpt.get();
                        profileTagResponse.setAvatar(pageBase.getAvatar());
                    }
                    return profileTagResponse;
                })
                .collect(Collectors.toList());
        return profileTagResponses;
    }
}
