package com.sharafatdin.talgatbek.taskmanager.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

/** @author Talgatbek Sharafatdin */
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class LoginRequest {
    @NotBlank(message = "Username is required")
    private String username;
    @NotBlank(message = "Password is required")
    private String password;
}
