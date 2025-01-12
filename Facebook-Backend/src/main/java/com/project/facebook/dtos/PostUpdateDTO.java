package com.project.facebook.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.facebook.responses.media.MediaResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Builder
@Setter
public class PostUpdateDTO {
    private String content;
    private String privacy;

    @JsonProperty("media_ids")
    private List<Long> mediaIds;
}
