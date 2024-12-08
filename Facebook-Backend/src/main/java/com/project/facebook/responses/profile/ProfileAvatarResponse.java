package com.project.facebook.responses.profile;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ProfileAvatarResponse {
    @JsonProperty("profile_id")
    private Long profileId;

    @JsonProperty("url")
    private String url;
}
