package com.project.facebook.responses.user;

import com.project.facebook.models.User;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterResponse {
    private String message;
    private User user;
}
