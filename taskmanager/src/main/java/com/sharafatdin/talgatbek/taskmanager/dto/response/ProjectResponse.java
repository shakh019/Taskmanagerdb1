package com.sharafatdin.talgatbek.taskmanager.dto.response;

import com.sharafatdin.talgatbek.taskmanager.entity.Project;
import lombok.*;
import java.time.LocalDateTime;

/** @author Talgatbek Sharafatdin */
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class ProjectResponse {
    private Long id;
    private String name;
    private String description;
    private Project.ProjectStatus status;
    private UserResponse owner;
    private int tasksCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
