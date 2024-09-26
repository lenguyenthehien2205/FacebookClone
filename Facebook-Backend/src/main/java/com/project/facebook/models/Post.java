package com.project.facebook.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.facebook.components.LocalizationUtils;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Entity
@Table(name = "posts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Post extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("post_id")
    @Column(name = "post_id")
    private Long postId;

    @ManyToOne
    @JsonProperty("author_id")
    @JoinColumn(name = "author_id")
    private User author;

    @JsonProperty("author_type")
    @Column(name = "author_type")
    private String authorType;

    @Column(name = "privacy")
    private String privacy;

    @Column(name = "content")
    private String content;

    @JsonProperty("is_active")
    @Column(name = "is_active")
    private boolean isActive;
}
