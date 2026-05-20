package com.sharafatdin.talgatbek.taskmanager.dto.response;

import lombok.*;

/** @author Talgatbek Sharafatdin */
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class AuthResponse {
    private String accessToken;
    private String refreshToken;
    private String tokenType;
    private UserResponse user;
}
