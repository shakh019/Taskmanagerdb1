package com.sharafatdin.talgatbek.taskmanager.dto.response;

import com.sharafatdin.talgatbek.taskmanager.entity.Task;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

/** @author Talgatbek Sharafatdin */
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class TaskResponse {
    private Long id;
    private String title;
    private String description;
    private Task.TaskStatus status;
    private Task.Priority priority;
    private LocalDate dueDate;
    private Long projectId;
    private String projectName;
    private UserResponse assignee;
    private UserResponse createdBy;
    private CategoryResponse category;
    private int commentsCount;
    private int attachmentsCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
