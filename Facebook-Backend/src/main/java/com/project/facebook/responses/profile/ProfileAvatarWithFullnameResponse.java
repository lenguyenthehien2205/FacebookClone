package com.project.facebook.responses.profile;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ProfileAvatarWithFullnameResponse {
    @JsonProperty("profile_id")
    private Long profileId;

    @JsonProperty("mutual_friends")
    private int mutual_friends;

    @JsonProperty("fullname")
    private String fullname;

    @JsonProperty("pathname")
    private String pathname;

    @JsonProperty("url")
    private String url;
}
