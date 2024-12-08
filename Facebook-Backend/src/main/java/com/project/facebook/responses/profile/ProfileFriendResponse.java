package com.project.facebook.responses.profile;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
public class ProfileFriendResponse {
    @JsonProperty("mutual_friends")
    private int mutual_friends;

    @JsonProperty("profile_avatar_friends")
    private List<ProfileAvatarWithFullnameResponse> profileAvatarFriends;
}
