package com.project.facebook.services;

import com.project.facebook.exceptions.DataNotFoundException;
import com.project.facebook.models.PageBase;
import com.project.facebook.models.Profile;
import com.project.facebook.models.User;
import com.project.facebook.repositories.FriendRepository;
import com.project.facebook.repositories.MediaRepository;
import com.project.facebook.repositories.PageBaseRepository;
import com.project.facebook.repositories.ProfileRepository;
import com.project.facebook.responses.media.MediaImageProfileResponse;
import com.project.facebook.responses.profile.ProfileAvatarFriendsResponse;
import com.project.facebook.responses.profile.ProfileFriendResponse;
import com.project.facebook.responses.profile.ProfileHeaderResponse;
import com.project.facebook.responses.profile.ProfileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProfileService implements IProfileService{
    private final ProfileRepository profileRepository;
    private final PageBaseRepository pageBaseRepository;
    private final PageBaseService pageBaseService;
    private final FriendRepository friendRepository;
    private final MediaRepository mediaRepository;
    @Override
    public Profile getProfileById(Long id) throws DataNotFoundException {
        return profileRepository.findById(id).orElseThrow(
                () -> new DataNotFoundException("Profile with id = "+id+" not found")
        );
    }
    public ProfileHeaderResponse getProfileHeaderByPathNameAndMyProfileId(String pathName, Long myProfileId) {
        // Lấy thông tin profile từ repository
        Profile profile = profileRepository.findProfileByPathName(pathName);
        if (profile == null) {
            throw new RuntimeException("Profile not found");
        }
        // Tính số lượng bạn bè chung
        int mutualFriends = friendRepository.countMutualFriendsByPathNameAndProfileId(profile.getId(), myProfileId);
        // Tính số lượng bạn bè tổng
        int totalFriends = friendRepository.countTotalFriends(profile.getId());
        // Lấy avatar của bạn bè
        List<ProfileAvatarFriendsResponse> profileAvatarFriendsResponseList = new ArrayList<>();
        List<Long> profileIdFriends = profileRepository.getProfileIdFriends(profile.getId(), 8);
        List<String> avatarFriends = profileRepository.getAvatarsByFriendIds(profileIdFriends);

        for (int i = 0; i < profileIdFriends.size(); i++) {
            Long friendId = profileIdFriends.get(i);
            String avatarFriend = avatarFriends.get(i);
            Optional<Profile> profileTemp = profileRepository.findById(friendId);
            profileAvatarFriendsResponseList.add(ProfileAvatarFriendsResponse.builder()
                    .profileId(friendId)
                    .fullname(getFullName(profileTemp.get()))
                    .avatar(avatarFriend)
                    .build());
        }
        Boolean isFriend;
        if (friendRepository.existsFriendship(myProfileId, profile.getId()) == 1){
            isFriend = true;
        } else {
            isFriend = false;
        }
        // Tạo ProfileHeaderResponse và trả về
        return ProfileHeaderResponse.builder()
                .avatar(profile.getPageBase().getAvatar())
                .coverPhoto(profile.getPageBase().getCoverPhoto())
                .fullname(getFullName(profile))
                .totalFriends(totalFriends)
                .mutualFriends(mutualFriends)
                .isFriends(isFriend)
                .avatarFriends(profileAvatarFriendsResponseList)
                .build();
    }
    public ProfileFriendResponse getProfileFriendsByPathNameAndMyProfileId(String pathName, Long myProfileId) {
        // Lấy thông tin profile từ repository
        Profile profile = profileRepository.findProfileByPathName(pathName);
        if (profile == null) {
            throw new RuntimeException("Profile not found");
        }
        // Tính số lượng bạn bè chung
        int mutualFriends = friendRepository.countMutualFriendsByPathNameAndProfileId(profile.getId(), myProfileId);
        // Lấy avatar của bạn bè
        List<ProfileAvatarFriendsResponse> profileAvatarFriendsResponseList = new ArrayList<>();
        List<Long> profileIdFriends = profileRepository.getProfileIdFriends(profile.getId(), 9);
        List<String> avatarFriends = profileRepository.getAvatarsByFriendIds(profileIdFriends);

        for (int i = 0; i < profileIdFriends.size(); i++) {
            Long friendId = profileIdFriends.get(i);
            String avatarFriend = avatarFriends.get(i);
            Optional<Profile> profileTemp = profileRepository.findById(friendId);
            profileAvatarFriendsResponseList.add(ProfileAvatarFriendsResponse.builder()
                    .profileId(friendId)
                    .fullname(getFullName(profileTemp.get()))
                    .avatar(avatarFriend)
                    .build());
        }
        Boolean isFriend;
        if (friendRepository.existsFriendship(myProfileId, profile.getId()) == 1){
            isFriend = true;
        } else {
            isFriend = false;
        }
        return ProfileFriendResponse.builder()
                .mutual_friends(mutualFriends)
                .profileAvatarFriends(profileAvatarFriendsResponseList)
                .build();
    }

    private String getFullName(Profile profile) {
        if (profile.getDisplayFormat().equals("firstname_lastname")) {
            return profile.getFirstName() + " " + profile.getLastName();
        } else {
            return profile.getLastName() + " " + profile.getFirstName();
        }
    }

    @Override
    public PageBase updateProfileAvatar(Long profileId, String avatarFileName) throws DataNotFoundException{
        Profile existingProfile = getProfileById(profileId);
        PageBase pageBase = pageBaseService.getPageBaseById(existingProfile.getPageBase().getId());
        pageBase.setAvatar(avatarFileName);
        return pageBaseRepository.save(pageBase);
    }
    @Override
    public PageBase updateProfileCoverPhoto(Long profileId, String avatarFileName) throws DataNotFoundException{
        Profile existingProfile = getProfileById(profileId);
        PageBase pageBase = pageBaseService.getPageBaseById(existingProfile.getPageBase().getId());
        pageBase.setCoverPhoto(avatarFileName);
        return pageBaseRepository.save(pageBase);
    }
}
