package com.project.facebook.responses.conversation;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.facebook.models.Profile;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ConversationResponse {
    private Long id;

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    @JsonProperty("display_format")
    private String displayFormat;

    private String avatar;

    @JsonProperty("profile_id")
    private Long profileId;

    public static ConversationResponse fromProfile(Profile profile, Long conversationId){
        ConversationResponse conversationResponse = ConversationResponse.builder()
                .id(conversationId)
                .firstName(profile.getFirstName())
                .lastName(profile.getLastName())
                .displayFormat(profile.getDisplayFormat())
                .build();
        return conversationResponse;
    }
}
