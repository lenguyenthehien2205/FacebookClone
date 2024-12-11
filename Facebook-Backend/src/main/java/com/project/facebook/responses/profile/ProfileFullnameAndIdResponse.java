package com.project.facebook.responses.profile;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.facebook.projections.medias.MediaImageProfileProjection;
import com.project.facebook.projections.profiles.ProfileFullnameAndIdProjection;
import com.project.facebook.responses.media.MediaImageProfileResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ProfileFullnameAndIdResponse {
    @JsonProperty("profile_id")
    private Long profileId;

    @JsonProperty("fullname")
    private String fullname;

    public static ProfileFullnameAndIdResponse convertToResponse(ProfileFullnameAndIdProjection projection) {
        return ProfileFullnameAndIdResponse
                .builder()
                .profileId(projection.getId())
                .fullname(projection.getFullname())
                .build();
    }
}
