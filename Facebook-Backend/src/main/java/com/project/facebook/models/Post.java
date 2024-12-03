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
    @JsonProperty("id")
    @Column(name = "id")
    private Long id;

    @JsonProperty("author_id")
    @Column(name = "author_id")
    private Long authorId;

    @JsonProperty("author_type")
    @Column(name = "author_type")
    private String authorType;

    @JsonProperty("post_type")
    @Column(name = "post_type")
    private String postType;

    @Column(name = "privacy")
    private String privacy;

    @Column(name = "content")
    private String content;

    @JsonProperty("is_active")
    @Column(name = "is_active")
    private boolean isActive;

    public static String PUBLIC = "public";
    public static String FRIENDS = "friends";
    public static String PRIVATE = "only me";
}
