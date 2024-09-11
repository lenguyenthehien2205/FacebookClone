package com.project.facebook.services;

import com.project.facebook.models.Friend;
import com.project.facebook.models.User;

import java.util.List;

public interface IFriendService {
    Friend addFriend(Long senderId, Long receiverId) throws Exception;
    List<User> getAllFriendsByUserId(Long userId) throws Exception;
}
