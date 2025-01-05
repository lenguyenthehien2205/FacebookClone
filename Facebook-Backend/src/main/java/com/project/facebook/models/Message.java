package com.project.facebook.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "messages")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Message extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JsonProperty("conversation_id")
    @JoinColumn(name = "conversation_id")
    private Conversation conversation;

    @JsonProperty("sender_id")
    @Column(name = "sender_id")
    private Long senderId;

    @Column(name = "content")
    private String content;
}