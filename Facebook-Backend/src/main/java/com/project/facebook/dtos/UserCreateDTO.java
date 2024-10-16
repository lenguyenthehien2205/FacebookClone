package com.project.facebook.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;

@Data//toString
@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor(force = true) // có thể khởi tạo LocalizationUtils mà không cần gán giá trị mặc định
public class UserCreateDTO {
    @JsonProperty("phone_number")
    @NotBlank(message = "Phone number is required")
    private String phoneNumber;

    @JsonProperty("password")
    @NotBlank(message = "Password is required")
    private String password;

    @JsonProperty("role_id")
    @NotNull(message = "Role is required")
    private Long roleId;

    @JsonProperty("first_name")
    @NotBlank(message = "First name is required")
    private String firstName;

    @JsonProperty("last_name")
    @NotBlank(message = "Last name is required")
    private String lastName;

    @NotBlank(message = "Gender is required")
    private String gender;

    @JsonProperty("date_of_birth")
    @NotNull(message = "Date of birth is required")
    private Date dateOfBirth;

    @JsonProperty("path_name")
    @NotBlank(message = "Path name is required")
    private String pathName;

    private String avatar;

    @JsonProperty("cover_photo")
    private String coverPhoto;

    private String biography;
}
