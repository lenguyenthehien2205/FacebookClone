package com.project.facebook.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "friend_requests")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FriendRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("friend_request_id")
    @Column(name = "friend_request_id")
    private Long friendRequestId;

    @ManyToOne
    @JsonProperty("sender_id")
    @JoinColumn(name = "sender_id")
    private User senderId;

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    @JsonProperty("receiver_id")
    private User receiver;

    @Column(name = "status")
    private String status;
}
