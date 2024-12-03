package com.project.facebook.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;


@Entity
@Table(name = "profiles")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Profile{
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

    @JsonProperty("first_name")
    @Column(name = "first_name")
    private String firstName;

    @JsonProperty("last_name")
    @Column(name = "last_name")
    private String lastName;

    @JsonProperty("display_format")
    @Column(name = "display_format")
    private String displayFormat;

    @Column(name = "gender")
    private String gender;

    @JsonProperty("date_of_birth")
    @Column(name = "date_of_birth")
    private Date dateOfBirth;

    @JsonProperty("is_online")
    @Column(name = "is_online")
    private Boolean isOnline;

    public static String LASTNAMEFIRSTNAME = "lastname_firstname";
    public static String FIRSTNAMELASTNAME  = "firstname_lastname";
    public static String DEFAULT_AVATAR  = "default_image.png";

    @PrePersist
    protected void onCreate() {
        setDisplayFormat("lastname_firstname");
        setIsOnline(false);
    }
}
