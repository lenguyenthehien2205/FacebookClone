package com.project.facebook.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data//toString
@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class MessageDTO {
    @JsonProperty("sender_id")
    private Long senderId;

    @JsonProperty("conversation_id")
    private Long conversationId;

    @JsonProperty("content")
    private String content;
}
