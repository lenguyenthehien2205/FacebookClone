package com.project.facebook.responses.interaction;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InteractorNameResponse {
    @JsonProperty("interactor_name")
    private String interactorName;
}
