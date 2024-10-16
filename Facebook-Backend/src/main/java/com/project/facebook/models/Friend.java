package com.project.facebook.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    @JsonProperty("first_profile_id")
    @JoinColumn(name = "first_profile_id")
    private Profile firstProfile;

    @ManyToOne
    @JoinColumn(name = "second_profile_id")
    @JsonProperty("second_profile_id")
    private Profile secondProfile;

    @Column(name = "is_active")
    @JsonProperty("is_active")
    private boolean isActive;
}
