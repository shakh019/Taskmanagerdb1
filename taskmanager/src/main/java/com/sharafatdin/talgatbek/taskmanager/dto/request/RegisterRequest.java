package com.sharafatdin.talgatbek.taskmanager.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;

/** @author Talgatbek Sharafatdin */
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class RegisterRequest {
    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 50, message = "Username must be 3-50 characters")
    private String username;
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;
    @NotBlank(message = "Password is required")
    @Size(min = 6, max = 100, message = "Password must be 6-100 characters")
    private String password;
    @Size(max = 50)
    private String firstName;
    @Size(max = 50)
    private String lastName;
}
