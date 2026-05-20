package com.sharafatdin.talgatbek.taskmanager.controller;

import com.sharafatdin.talgatbek.taskmanager.dto.request.ProjectRequest;
import com.sharafatdin.talgatbek.taskmanager.dto.response.*;
import com.sharafatdin.talgatbek.taskmanager.service.TalgatbekProjectService;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/** @author Talgatbek Sharafatdin */
@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
@Tag(name = "Projects", description = "Project management by Talgatbek Sharafatdin")
@SecurityRequirement(name = "bearerAuth")
public class TalgatbekProjectController {

    private final TalgatbekProjectService projectService;

    @Operation(summary = "Create project")
    @PostMapping
    public ResponseEntity<ApiResponse<ProjectResponse>> createProject(
            @Valid @RequestBody ProjectRequest request, Authentication auth) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Project created", projectService.createProject(request, auth.getName())));
    }

    @Operation(summary = "Get all projects")
    @GetMapping
    public ResponseEntity<ApiResponse<List<ProjectResponse>>> getAllProjects() {
        return ResponseEntity.ok(ApiResponse.success(projectService.getAllProjects()));
    }

    @Operation(summary = "Get my projects")
    @GetMapping("/my")
    public ResponseEntity<ApiResponse<List<ProjectResponse>>> getMyProjects(Authentication auth) {
        return ResponseEntity.ok(ApiResponse.success(projectService.getMyProjects(auth.getName())));
    }

    @Operation(summary = "Get project by ID")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProjectResponse>> getProjectById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(projectService.getProjectById(id)));
    }

    @Operation(summary = "Update project")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ProjectResponse>> updateProject(
            @PathVariable Long id, @Valid @RequestBody ProjectRequest request, Authentication auth) {
        return ResponseEntity.ok(ApiResponse.success("Project updated",
                projectService.updateProject(id, request, auth.getName())));
    }

    @Operation(summary = "Delete project")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteProject(@PathVariable Long id, Authentication auth) {
        projectService.deleteProject(id, auth.getName());
        return ResponseEntity.ok(ApiResponse.success("Project deleted", null));
    }
}
