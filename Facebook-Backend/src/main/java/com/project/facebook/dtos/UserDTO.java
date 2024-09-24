package com.project.facebook.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.facebook.components.LocalizationUtils;

import com.project.facebook.utils.MessageKeys;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data//toString
@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor(force = true)
public class UserDTO {
    private final LocalizationUtils localizationUtils;
    @JsonProperty("username")
    @NotBlank(message = MessageKeys.USERNAME_IS_REQUIRED)
    private String username;

    @JsonProperty("password")
    @NotBlank(message = MessageKeys.PASSWORD_IS_REQUIRED)
    private String password;

    @JsonProperty("role_id")
    @NotNull(message = MessageKeys.ROLE_IS_REQUIRED)
    private Long roleId;

    @JsonProperty("avatar")
    private String avatar;

    @JsonProperty("phone_number")
    @NotBlank(message = MessageKeys.PHONE_NUMBER_IS_REQUIRED)
    private String phoneNumber;
}
