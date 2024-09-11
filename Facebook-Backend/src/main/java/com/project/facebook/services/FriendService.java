package com.project.facebook.services;

import com.project.facebook.exceptions.AlreadyExistsException;
import com.project.facebook.exceptions.DataNotFoundException;
import com.project.facebook.models.Friend;
import com.project.facebook.models.User;
import com.project.facebook.repositories.FriendRepository;
import com.project.facebook.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class FriendService implements IFriendService{
    private final FriendRepository friendRepository;
    private final UserRepository userRepository;
    @Override
    public Friend addFriend(Long firstUserId, Long secondUserId) throws Exception{
        if(!friendRepository.existsFriendship(firstUserId, secondUserId)){
            User sender = userRepository.findById(firstUserId).orElseThrow(() -> new DataNotFoundException("User not found"));
            User receiver = userRepository.findById(secondUserId).orElseThrow(() -> new DataNotFoundException("User not found"));
            Friend friend = new Friend();
            // sap xep (id nho hon dung truoc)
            User user1 = sender.getUserId() < receiver.getUserId() ? sender : receiver;
            User user2 = sender.getUserId() > receiver.getUserId() ? sender : receiver;
            friend.setFirstUser(user1);
            friend.setSecondUser(user2);
            return friendRepository.save(friend);
        }else{
            throw new AlreadyExistsException("Friendship already exists");
        }
    }

    @Override
    public List<User> getAllFriendsByUserId(Long userId) throws Exception {
        User existingUser = userRepository.findById(userId).orElseThrow(
                () -> new DataNotFoundException("User not found")
        );
        return friendRepository.findAllFriendsByUserId(userId);
    }


}
