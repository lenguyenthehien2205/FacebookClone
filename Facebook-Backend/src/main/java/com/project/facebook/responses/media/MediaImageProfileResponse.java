package com.project.facebook.responses.media;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.facebook.projections.medias.MediaImageProfileProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Builder
@Getter
@Setter
public class MediaImageProfileResponse {
    @JsonProperty("media_id")
    private Long mediaId;

    @JsonProperty("url")
    private String url;
    public static MediaImageProfileResponse convertToResponse(MediaImageProfileProjection projection) {
        return MediaImageProfileResponse
                .builder()
                .mediaId(projection.getMediaId())
                .url(projection.getUrl())
                .build();
    }
}
