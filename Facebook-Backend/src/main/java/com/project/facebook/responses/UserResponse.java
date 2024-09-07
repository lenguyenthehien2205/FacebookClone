package com.project.facebook.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.facebook.models.User;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class UserResponse extends BaseResponse{
    private String username;
    private String password;
    private String avatar;

    @JsonProperty("phone_number")
    private String phoneNumber;

    @JsonProperty("role_id")
    private Long roleId;

    public static UserResponse fromUser(User user) {
        UserResponse userResponse = UserResponse.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roleId(user.getRole().getRoleId())
                .phoneNumber(user.getPhoneNumber())
                .avatar(user.getAvatar())
                .build();
        userResponse.setCreatedAt(user.getCreatedAt());
        userResponse.setUpdatedAt(user.getUpdatedAt());
        return userResponse;
    }
}
