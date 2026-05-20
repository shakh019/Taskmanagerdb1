package com.sharafatdin.talgatbek.taskmanager.controller;

import com.sharafatdin.talgatbek.taskmanager.async.TalgatbekAsyncService;
import com.sharafatdin.talgatbek.taskmanager.dto.request.TaskRequest;
import com.sharafatdin.talgatbek.taskmanager.dto.response.*;
import com.sharafatdin.talgatbek.taskmanager.entity.Task;
import com.sharafatdin.talgatbek.taskmanager.service.TalgatbekTaskService;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

/** @author Talgatbek Sharafatdin */
@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
@Tag(name = "Tasks", description = "Task management by Talgatbek Sharafatdin")
@SecurityRequirement(name = "bearerAuth")
public class TalgatbekTaskController {

    private final TalgatbekTaskService taskService;
    private final TalgatbekAsyncService asyncService;

    @Operation(summary = "Create task")
    @PostMapping
    public ResponseEntity<ApiResponse<TaskResponse>> createTask(
            @Valid @RequestBody TaskRequest request, Authentication auth) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Task created", taskService.createTask(request, auth.getName())));
    }

    @Operation(summary = "Get all tasks with pagination, sorting, filtering and search")
    @GetMapping
    public ResponseEntity<ApiResponse<PagedResponse<TaskResponse>>> getAllTasks(
            @RequestParam(required = false) Task.TaskStatus status,
            @RequestParam(required = false) Task.Priority priority,
            @RequestParam(required = false) Long projectId,
            @RequestParam(required = false) Long assigneeId,
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "DESC") String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("ASC") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return ResponseEntity.ok(ApiResponse.success(
                taskService.getAllTasks(status, priority, projectId, assigneeId, search, pageable)));
    }

    @Operation(summary = "Get task by ID")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TaskResponse>> getTaskById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(taskService.getTaskById(id)));
    }

    @Operation(summary = "Get tasks by project with pagination")
    @GetMapping("/project/{projectId}")
    public ResponseEntity<ApiResponse<PagedResponse<TaskResponse>>> getTasksByProject(
            @PathVariable Long projectId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "DESC") String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("ASC") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return ResponseEntity.ok(ApiResponse.success(taskService.getTasksByProject(projectId, pageable)));
    }

    @Operation(summary = "Update task")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<TaskResponse>> updateTask(
            @PathVariable Long id, @Valid @RequestBody TaskRequest request, Authentication auth) {
        return ResponseEntity.ok(ApiResponse.success("Task updated",
                taskService.updateTask(id, request, auth.getName())));
    }

    @Operation(summary = "Delete task")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteTask(@PathVariable Long id, Authentication auth) {
        taskService.deleteTask(id, auth.getName());
        return ResponseEntity.ok(ApiResponse.success("Task deleted", null));
    }

    @Operation(summary = "Get async task count for user by status")
    @GetMapping("/async/count")
    public CompletableFuture<ApiResponse<Long>> getTaskCountAsync(
            @RequestParam Long userId,
            @RequestParam(defaultValue = "TODO") Task.TaskStatus status) {
        return asyncService.countTasksByUserAsync(userId, status)
                .thenApply(count -> ApiResponse.success("Task count retrieved", count));
    }
}
