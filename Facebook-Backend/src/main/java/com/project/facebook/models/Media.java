package com.project.facebook.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "medias")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Media {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("media_id")
    @Column(name = "media_id")
    private Long mediaId;

    @ManyToOne
    @JsonProperty("post_id")
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @Column(name = "note")
    private String note;

    @Column(name = "url", length = 350)
    private String url;

    @JsonProperty("media_type")
    @Column(name = "media_type")
    private String mediaType;

    public static final int MAXIMUM_MEDIA_PER_POST = 10;
}