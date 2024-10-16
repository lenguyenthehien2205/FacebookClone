package com.project.facebook.services;

import com.project.facebook.models.Friend;
import com.project.facebook.models.Profile;
import com.project.facebook.models.User;
import com.project.facebook.responses.profile.ProfileTagResponse;

import java.util.List;

public interface IFriendService {
    Friend addFriend(Long senderId, Long receiverId) throws Exception;
    List<ProfileTagResponse> getAllFriendsByProfileId(Long userId) throws Exception;
}
