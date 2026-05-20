package com.sharafatdin.talgatbek.taskmanager.dto.request;

import com.sharafatdin.talgatbek.taskmanager.entity.Project;
import jakarta.validation.constraints.*;
import lombok.*;

/** @author Talgatbek Sharafatdin */
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class ProjectRequest {
    @NotBlank(message = "Name is required")
    @Size(max = 100)
    private String name;
    @Size(max = 500)
    private String description;
    private Project.ProjectStatus status;
}
