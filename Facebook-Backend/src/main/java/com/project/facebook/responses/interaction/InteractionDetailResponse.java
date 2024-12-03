package com.project.facebook.responses.interaction;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
public class InteractionDetailResponse {
    @JsonProperty("interactor_name_responses")
    private List<InteractorNameResponse> interactorNameResponses;

    @JsonProperty("total_interactions")
    private int totalInteractions;
}
