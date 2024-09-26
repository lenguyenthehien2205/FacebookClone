package com.project.facebook.responses.media;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.facebook.models.Media;
import com.project.facebook.models.Post;
import com.project.facebook.models.User;
import com.project.facebook.responses.user.UserResponse;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class MediaResponse {
    @JsonProperty("media_id")
    private Long mediaId;

    @Column(name = "url", length = 350)
    private String url;

    @JsonProperty("media_type")
    @Column(name = "media_type")
    private String mediaType;

    public static MediaResponse fromMedia(Media media) {
        MediaResponse mediaResponse = MediaResponse.builder()
                .mediaId(media.getMediaId())
                .url(media.getUrl())
                .mediaType(media.getMediaType())
                .build();
        return mediaResponse;
    }
}

