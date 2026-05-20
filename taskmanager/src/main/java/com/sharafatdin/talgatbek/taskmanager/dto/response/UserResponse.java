package com.sharafatdin.talgatbek.taskmanager.dto.response;

import com.sharafatdin.talgatbek.taskmanager.entity.User;
import lombok.*;
import java.time.LocalDateTime;

/** @author Talgatbek Sharafatdin */
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class UserResponse {
    private Long id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private User.Role role;
    private boolean enabled;
    private LocalDateTime createdAt;
}
