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
    @JsonProperty("first_user_id")
    @JoinColumn(name = "first_user_id")
    private User firstUser;

    @ManyToOne
    @JoinColumn(name = "second_user_id")
    @JsonProperty("second_user_id")
    private User secondUser;

    @Column(name = "is_active")
    @JsonProperty("is_active")
    private boolean isActive;
}
