package com.project.facebook.responses.media;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.facebook.models.Media;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class MediaResponse {
    @JsonProperty("media_id")
    private Long mediaId;

    @JsonProperty("note")
    private String note;

    private String url;

    @JsonProperty("media_type")
    private String mediaType;

    public static MediaResponse fromMedia(Media media) {
        MediaResponse mediaResponse = MediaResponse.builder()
                .mediaId(media.getMediaId())
                .note(media.getNote())
                .url(media.getUrl())
                .mediaType(media.getMediaType())
                .build();
        return mediaResponse;
    }
}

