package com.project.facebook.controllers;

import com.project.facebook.models.Friend;
import com.project.facebook.responses.user.UserResponse;
import com.project.facebook.services.FriendService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("${api.base-path}/friends")
@RequiredArgsConstructor
public class FriendController {
    private final FriendService friendService;
    @PostMapping("/{first_user_id}/{second_user_id}")
    public ResponseEntity<?> addFriend(
            @PathVariable("first_user_id") Long firstUserId,
            @PathVariable("second_user_id") Long secondUserId){
        try{
            if(firstUserId.equals(secondUserId)){
                return ResponseEntity.badRequest().body("Invalid senderId and receiverId");
            }
            Friend newFriend = friendService.addFriend(firstUserId, secondUserId);
            return ResponseEntity.ok(newFriend);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("{user_id}")
    public ResponseEntity<?> getFriends(@PathVariable("user_id") Long userId){
        try {
            List<UserResponse> friends = friendService.getAllFriendsByUserId(userId)
                    .stream()
                    .map(UserResponse::fromUser)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(friends);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
