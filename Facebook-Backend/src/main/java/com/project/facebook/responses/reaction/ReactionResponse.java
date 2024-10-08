package com.project.facebook.responses.reaction;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.facebook.models.Media;
import com.project.facebook.models.Reaction;
import com.project.facebook.responses.media.MediaResponse;
import com.project.facebook.responses.user.UserTagResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ReactionResponse {
    @JsonProperty("reaction_id")
    private Long reactionId;

    private UserTagResponse user;

    @JsonProperty("reactor_type")
    private String reactorType;

    @JsonProperty("reaction_type")
    private String reactionType;

    public static ReactionResponse fromReaction(Reaction reaction) {
        ReactionResponse reactionResponse = ReactionResponse.builder()
                .reactionId(reaction.getReactionId())
                .user(UserTagResponse.fromUser(reaction.getUser()))
                .reactionType(reaction.getReactionType())
                .reactorType(reaction.getReactorType())
                .build();
        return reactionResponse;
    }
}
