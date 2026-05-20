package com.sharafatdin.talgatbek.taskmanager.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;

/** @author Talgatbek Sharafatdin */
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class CategoryRequest {
    @NotBlank(message = "Name is required")
    @Size(max = 100)
    private String name;
    @Size(max = 300)
    private String description;
    @Pattern(regexp = "^#[0-9A-Fa-f]{6}$", message = "Color must be a valid hex color")
    private String color;
}
