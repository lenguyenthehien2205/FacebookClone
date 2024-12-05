package com.project.facebook.responses.profile;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
public class ProfileHeaderResponse {
    @JsonProperty("avatar")
    private String avatar;

    @JsonProperty("cover_photo")
    private String coverPhoto;


    @JsonProperty("fullname")
    private String fullname;

    @JsonProperty("total_friends")
    private int totalFriends;

    @JsonProperty("mutual_friends")
    private int mutualFriends;

    @JsonProperty("is_friends")
    private boolean isFriends;

    @JsonProperty("avatar_friends")
    private List<ProfileAvatarFriendsResponse> avatarFriends;
}
