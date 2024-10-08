package com.project.facebook.controllers;

import com.project.facebook.components.LocalizationUtils;
import com.project.facebook.models.Friend;
import com.project.facebook.models.User;
import com.project.facebook.responses.ResponseObject;
import com.project.facebook.responses.user.UserResponse;
import com.project.facebook.responses.user.UserTagResponse;
import com.project.facebook.services.FriendService;
import com.project.facebook.utils.MessageKeys;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("${api.base-path}/friends")
@RequiredArgsConstructor
public class FriendController {
    private final FriendService friendService;
    private final LocalizationUtils localizationUtils;
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

    @GetMapping("/{user_id}")
    public ResponseEntity<?> getFriendsById(@PathVariable("user_id") Long userId){
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

    @GetMapping("/contacts/{user_id}")
    public ResponseEntity<ResponseObject> getQuickContactsById(@PathVariable("user_id") Long userId, Authentication authentication){
        try{
            // Lấy thông tin người dùng đang đăng nhập
            User currentUser = (User) authentication.getPrincipal();

            // Kiểm tra xem userId có khớp với người dùng đang đăng nhập không
            if (!currentUser.getUserId().equals(userId)) {
                return ResponseEntity.ok(ResponseObject.builder()
                        .message("Unauthorized")
                        .status(HttpStatus.FORBIDDEN).build());
            }

            List<UserTagResponse> quickContacts = friendService.getAllFriendsByUserId(userId)
                    .stream()
                    .map(UserTagResponse::fromUser)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(ResponseObject.builder()
                            .data(quickContacts)
                            .status(HttpStatus.OK)
                            .message(localizationUtils.getLocalizedMessage(MessageKeys.GET_QUICK_CONTACT_SUCCESSFULLY))
                    .build());
        } catch (Exception e){
            return ResponseEntity.ok(ResponseObject.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .message(localizationUtils.getLocalizedMessage(MessageKeys.GET_QUICK_CONTACT_FAILED))
                    .build());
        }
    }
}
