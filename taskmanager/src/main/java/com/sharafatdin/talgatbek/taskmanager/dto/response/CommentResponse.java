package com.sharafatdin.talgatbek.taskmanager.dto.response;

import lombok.*;
import java.time.LocalDateTime;

/** @author Talgatbek Sharafatdin */
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class CommentResponse {
    private Long id;
    private String content;
    private Long taskId;
    private UserResponse author;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
