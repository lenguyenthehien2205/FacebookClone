package com.project.facebook.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "interactions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Interaction extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("interaction_id")
    @Column(name = "interaction_id")
    private Long interactionId;

    @JsonProperty("post_media_id")
    @Column(name = "post_media_id")
    private Long postMediaId; // Thay thế cho post_id

    @JsonProperty("post_media_type")
    @Column(name = "post_media_type")
    private String postMediaType; // 'post' hoặc 'media'

    @JsonProperty("interactor_id")
    @Column(name = "interactor_id")
    private Long interactorId;

    @JsonProperty("interactor_type")
    @Column(name = "interactor_type")
    private String interactorType;

    @JsonProperty("interaction_type")
    @Column(name = "interaction_type")
    private String interactionType;
}
