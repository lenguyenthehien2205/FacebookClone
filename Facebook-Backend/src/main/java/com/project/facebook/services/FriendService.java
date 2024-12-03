package com.project.facebook.services;

import com.project.facebook.exceptions.AlreadyExistsException;
import com.project.facebook.exceptions.DataNotFoundException;
import com.project.facebook.models.Friend;
import com.project.facebook.models.PageBase;
import com.project.facebook.models.Profile;
import com.project.facebook.models.User;
import com.project.facebook.repositories.FriendRepository;
import com.project.facebook.repositories.PageBaseRepository;
import com.project.facebook.repositories.ProfileRepository;
import com.project.facebook.repositories.UserRepository;
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
    @Override
    public Friend addFriend(Long firstProfileId, Long secondProfileId) throws Exception{
        if(friendRepository.existsFriendship(firstProfileId, secondProfileId) == 0){
            Profile sender = profileRepository.findById(firstProfileId).orElseThrow(() -> new DataNotFoundException("Sender not found"));
            Profile receiver = profileRepository.findById(secondProfileId).orElseThrow(() -> new DataNotFoundException("Receiver not found"));
            Friend friend = new Friend();
            // sap xep (id nho hon dung truoc)
            Profile profile1 = sender.getId() < receiver.getId() ? sender : receiver;
            Profile profile2 = sender.getId() > receiver.getId() ? sender : receiver;
            friend.setFirstProfile(profile1);
            friend.setSecondProfile(profile2);
            return friendRepository.save(friend);
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
