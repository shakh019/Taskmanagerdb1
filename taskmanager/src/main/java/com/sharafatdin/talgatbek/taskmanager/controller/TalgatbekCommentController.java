package com.sharafatdin.talgatbek.taskmanager.controller;

import com.sharafatdin.talgatbek.taskmanager.dto.request.CommentRequest;
import com.sharafatdin.talgatbek.taskmanager.dto.response.*;
import com.sharafatdin.talgatbek.taskmanager.service.TalgatbekCommentService;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

/** @author Talgatbek Sharafatdin */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "Comments", description = "Comment management by Talgatbek Sharafatdin")
@SecurityRequirement(name = "bearerAuth")
public class TalgatbekCommentController {

    private final TalgatbekCommentService commentService;

    @Operation(summary = "Add comment to task")
    @PostMapping("/tasks/{taskId}/comments")
    public ResponseEntity<ApiResponse<CommentResponse>> createComment(
            @PathVariable Long taskId,
            @Valid @RequestBody CommentRequest request,
            Authentication auth) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Comment added",
                        commentService.createComment(taskId, request, auth.getName())));
    }

    @Operation(summary = "Get comments for task with pagination")
    @GetMapping("/tasks/{taskId}/comments")
    public ResponseEntity<ApiResponse<PagedResponse<CommentResponse>>> getComments(
            @PathVariable Long taskId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return ResponseEntity.ok(ApiResponse.success(commentService.getCommentsByTask(taskId, pageable)));
    }

    @Operation(summary = "Update comment")
    @PutMapping("/comments/{commentId}")
    public ResponseEntity<ApiResponse<CommentResponse>> updateComment(
            @PathVariable Long commentId,
            @Valid @RequestBody CommentRequest request,
            Authentication auth) {
        return ResponseEntity.ok(ApiResponse.success("Comment updated",
                commentService.updateComment(commentId, request, auth.getName())));
    }

    @Operation(summary = "Delete comment")
    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<ApiResponse<Void>> deleteComment(
            @PathVariable Long commentId, Authentication auth) {
        commentService.deleteComment(commentId, auth.getName());
        return ResponseEntity.ok(ApiResponse.success("Comment deleted", null));
    }
}
