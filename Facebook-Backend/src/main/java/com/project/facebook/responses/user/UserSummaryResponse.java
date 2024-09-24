package com.project.facebook.responses.user;

import com.project.facebook.models.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class UserSummaryResponse {
    private String username;
    private String avatar;
    private Boolean isOnline;

    public static UserSummaryResponse fromUser(User user) {
        UserSummaryResponse userSummaryResponse = UserSummaryResponse.builder()
                .username(user.getUsername())
                .avatar(user.getAvatar())
                .isOnline(user.isOnline())
                .build();
        return userSummaryResponse;
    }
}
