package com.project.facebook.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data//toString
@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class MediaDTO {
    @JsonProperty("post_id")
    private Long postId;

    @JsonProperty("note")
    private String note;

    @JsonProperty("url")
    private String url;

    @JsonProperty("media_type")
    private String mediaType;
}