package com.sharafatdin.talgatbek.taskmanager.dto.response;

import lombok.*;
import java.time.LocalDateTime;

/** @author Talgatbek Sharafatdin */
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class CategoryResponse {
    private Long id;
    private String name;
    private String description;
    private String color;
    private LocalDateTime createdAt;
}
