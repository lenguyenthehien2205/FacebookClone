package com.project.facebook.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "pages")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Page{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JsonProperty("user_id")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JsonProperty("base_id")
    @JoinColumn(name = "base_id")
    private PageBase pageBase;

    @JsonProperty("page_name")
    @Column(name = "page_name")
    private String pageName;

    @JsonProperty("page_type")
    @Column(name = "page_type")
    private String pageType;
}
