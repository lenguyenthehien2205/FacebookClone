package com.project.facebook.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.facebook.components.LocalizationUtils;
import com.project.facebook.utils.MessageKeys;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data//toString
@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class PostDTO {
    @JsonProperty("author_id")
    @NotNull(message = MessageKeys.AUTHOR_IS_REQUIRED)
    private Long authorId;

    @JsonProperty("author_type")
    @NotBlank(message = MessageKeys.AUTHOR_TYPE_IS_REQUIRED)
    private String authorType;

    @JsonProperty("privacy")
    @NotBlank(message = MessageKeys.PRIVACY_IS_REQUIRED)
    private String privacy;

    @JsonProperty("content")
    private String content;
}
