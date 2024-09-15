package com.project.facebook.responses.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.facebook.models.Role;
import com.project.facebook.models.User;
import com.project.facebook.responses.BaseResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class UserResponse extends BaseResponse {
    @JsonProperty("user_id")
    private Long userId;
    private String username;
    private String avatar;

    @JsonProperty("phone_number")
    private String phoneNumber;

    @JsonProperty("role_id")
    private Role role;

    public static UserResponse fromUser(User user) {
        UserResponse userResponse = UserResponse.builder()
                .userId(user.getUserId())
                .username(user.getUsername())
                .role(user.getRole())
                .phoneNumber(user.getPhoneNumber())
                .avatar(user.getAvatar())
                .build();
        userResponse.setCreatedAt(user.getCreatedAt());
        userResponse.setUpdatedAt(user.getUpdatedAt());
        return userResponse;
    }
}
