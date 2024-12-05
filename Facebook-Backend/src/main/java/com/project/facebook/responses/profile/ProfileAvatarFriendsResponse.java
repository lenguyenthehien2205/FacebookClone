package com.project.facebook.responses.profile;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ProfileAvatarFriendsResponse {
    @JsonProperty("profile_id")
    private Long profileId;

    @JsonProperty("fullname")
    private String fullname;

    @JsonProperty("avatar")
    private String avatar;
}
