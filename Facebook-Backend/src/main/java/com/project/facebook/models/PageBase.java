package com.project.facebook.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "page_bases")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PageBase extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @JsonProperty("path_name")
    @Column(name = "path_name")
    private String pathName;

    @Column(name = "avatar")
    private String avatar;

    @JsonProperty("cover_photo")
    @Column(name = "cover_photo")
    private String coverPhoto;

    @Column(name = "biography")
    private String biography;

    @JsonProperty("is_active")
    @Column(name = "is_active")
    private Boolean isActive;

    @PrePersist
    protected void onCreate() {
        isActive = true;
        setCreatedAt(LocalDateTime.now());
        setUpdatedAt(LocalDateTime.now());
    }
}
