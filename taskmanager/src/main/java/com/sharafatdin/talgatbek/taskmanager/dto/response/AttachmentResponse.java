package com.sharafatdin.talgatbek.taskmanager.dto.response;

import lombok.*;
import java.time.LocalDateTime;

/** @author Talgatbek Sharafatdin */
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class AttachmentResponse {
    private Long id;
    private String originalName;
    private String contentType;
    private Long fileSize;
    private Long taskId;
    private UserResponse uploadedBy;
    private String downloadUrl;
    private LocalDateTime createdAt;
}
