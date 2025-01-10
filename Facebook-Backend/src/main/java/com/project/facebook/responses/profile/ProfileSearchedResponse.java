package com.project.facebook.responses.profile;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.facebook.models.Profile;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ProfileSearchedResponse {
    @JsonProperty("profile_id")
    private Long profileId;

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    @JsonProperty("display_format")
    private String displayFormat;

    @JsonProperty("pathname")
    private String pathname;

    private String avatar;

    public static ProfileSearchedResponse fromProfile(Profile profile) {
        ProfileSearchedResponse profileSearchedResponse = ProfileSearchedResponse.builder()
                .profileId(profile.getId())
                .firstName(profile.getFirstName())
                .lastName(profile.getLastName())
                .displayFormat(profile.getDisplayFormat())
                .build();
        return profileSearchedResponse;
    }
}
