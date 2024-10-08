package com.project.facebook.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class PostFetchDTO {
    @JsonProperty("user_id")
    private Long userId;

    @JsonProperty("limit")
    private int limit;

    @JsonProperty("fetched_ids")
    private List<Long> fetchedIds;
}
