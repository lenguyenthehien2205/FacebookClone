package com.project.facebook.responses.interaction;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
public class InteractionMediaResponse {
    @JsonProperty("interactor_name_responses")
    private List<InteractorNameResponse> interactorNameResponses;

    @JsonProperty("total_interactions")
    private int totalInteractions;

    @JsonProperty("highest_interaction")
    private String highestInteraction;

    @JsonProperty("second_highest_interaction")
    private String secondHighestInteraction;
}