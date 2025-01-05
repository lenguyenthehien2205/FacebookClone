package com.project.facebook.responses.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.facebook.models.Conversation;
import com.project.facebook.models.Message;
import com.project.facebook.models.Post;
import com.project.facebook.responses.post.PostResponse;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class MessageResponse {
    private Long id;

    @JsonProperty("sender_id")
    private Long senderId;

    private String content;

    public static MessageResponse fromMessage(Message message){
        MessageResponse messageResponse = MessageResponse.builder()
                .id(message.getId())
                .senderId(message.getSenderId())
                .content(message.getContent())
                .build();
        return messageResponse;
    }
}
