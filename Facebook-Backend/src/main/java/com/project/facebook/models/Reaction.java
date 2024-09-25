package com.project.facebook.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "reactions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Reaction extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("reaction_id")
    @Column(name = "reaction_id")
    private Long reactionId;

    @ManyToOne
    @JsonProperty("post_id")
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    @JsonProperty("reactor_id")
    @JoinColumn(name = "reactor_id")
    private User user;

    @JsonProperty("reactor_type")
    @Column(name = "reactor_type")
    private String reactorType;

    @JsonProperty("reaction_type")
    @Column(name = "reaction_type")
    private String reactionType;
}
