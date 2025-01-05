package com.project.facebook.responses.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
public class MessageOfConversationResponse {
    @JsonProperty("conversation_id")
    private Long conversationId;

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    @JsonProperty("display_format")
    private String displayFormat;

    private String avatar;

    List<MessageResponse> messages;
}
