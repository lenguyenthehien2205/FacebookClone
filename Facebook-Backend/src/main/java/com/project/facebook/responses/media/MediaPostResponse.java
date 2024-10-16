package com.project.facebook.responses.media;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
public class MediaPostResponse {
    @JsonProperty("media_responses")
    private List<MediaResponse> mediaResponses;

    @JsonProperty("total_medias")
    private int totalMedias;
}
