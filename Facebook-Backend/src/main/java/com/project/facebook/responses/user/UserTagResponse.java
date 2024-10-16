package com.project.facebook.responses.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.facebook.models.PageBase;
import com.project.facebook.models.Profile;
import com.project.facebook.models.Role;
import com.project.facebook.models.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class UserTagResponse {
    @JsonProperty("user_id")
    private Long userId;

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    @JsonProperty("display_format")
    private String displayFormat;

    private String avatar;

    @JsonProperty("is_online")
    private boolean isOnline;

    public static UserTagResponse fromUser(Profile profile, PageBase pageBase) {
        UserTagResponse userTagResponse = UserTagResponse.builder()
                .userId(profile.getUser().getUserId())
                .firstName(profile.getFirstName())
                .lastName(profile.getLastName())
                .displayFormat(profile.getDisplayFormat())
                .avatar(pageBase.getAvatar())
                .isOnline(profile.getIsOnline())
                .build();
        return userTagResponse;
    }
}
