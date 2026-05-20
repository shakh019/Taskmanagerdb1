package com.sharafatdin.talgatbek.taskmanager.dto.request;

import com.sharafatdin.talgatbek.taskmanager.entity.Task;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;

/** @author Talgatbek Sharafatdin */
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class TaskRequest {
    @NotBlank(message = "Title is required")
    @Size(max = 200, message = "Title max 200 characters")
    private String title;
    @Size(max = 2000)
    private String description;
    private Task.TaskStatus status;
    private Task.Priority priority;
    private LocalDate dueDate;
    private Long projectId;
    private Long assigneeId;
    private Long categoryId;
}
