package com.sharafatdin.talgatbek.taskmanager.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;

/** @author Talgatbek Sharafatdin */
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class CommentRequest {
    @NotBlank(message = "Content is required")
    @Size(max = 2000)
    private String content;
}
