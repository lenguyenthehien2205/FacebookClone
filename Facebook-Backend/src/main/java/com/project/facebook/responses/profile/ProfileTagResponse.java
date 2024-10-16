package com.project.facebook.responses.profile;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.facebook.models.PageBase;
import com.project.facebook.models.Profile;
import com.project.facebook.responses.user.UserTagResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ProfileTagResponse {
    @JsonProperty("profile_id")
    private Long profileId;

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    @JsonProperty("display_format")
    private String displayFormat;

    private String avatar;

    @JsonProperty("is_online")
    private boolean isOnline;

    public static ProfileTagResponse fromProfile(Profile profile) {
        ProfileTagResponse profileTagResponse = ProfileTagResponse.builder()
                .profileId(profile.getUser().getUserId())
                .firstName(profile.getFirstName())
                .lastName(profile.getLastName())
                .displayFormat(profile.getDisplayFormat())
                .isOnline(profile.getIsOnline())
                .build();
        return profileTagResponse;
    }
}
