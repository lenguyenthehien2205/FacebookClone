package com.project.facebook.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "friends")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Friend {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("friend_id")
    @Column(name = "friend_id")
    private Long friendId;

    @ManyToOne
    @JsonProperty("sender_id")
    @JoinColumn(name = "sender_id")
    private Profile senderProfile;

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    @JsonProperty("receiver_id")
    private Profile receiverProfile;

    @JsonProperty("created_at")
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "is_active")
    @JsonProperty("is_active")
    private boolean isActive;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
