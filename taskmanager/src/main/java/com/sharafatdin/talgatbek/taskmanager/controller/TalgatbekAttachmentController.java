package com.sharafatdin.talgatbek.taskmanager.controller;

import com.sharafatdin.talgatbek.taskmanager.dto.response.*;
import com.sharafatdin.talgatbek.taskmanager.service.TalgatbekAttachmentService;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

/** @author Talgatbek Sharafatdin */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "Attachments", description = "File upload/download by Talgatbek Sharafatdin")
@SecurityRequirement(name = "bearerAuth")
public class TalgatbekAttachmentController {

    private final TalgatbekAttachmentService attachmentService;

    @Operation(summary = "Upload file to task")
    @PostMapping("/tasks/{taskId}/attachments")
    public ResponseEntity<ApiResponse<AttachmentResponse>> uploadFile(
            @PathVariable Long taskId,
            @RequestParam("file") MultipartFile file,
            Authentication auth) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("File uploaded",
                        attachmentService.uploadFile(taskId, file, auth.getName())));
    }

    @Operation(summary = "Get attachments for task")
    @GetMapping("/tasks/{taskId}/attachments")
    public ResponseEntity<ApiResponse<List<AttachmentResponse>>> getAttachments(@PathVariable Long taskId) {
        return ResponseEntity.ok(ApiResponse.success(attachmentService.getAttachmentsByTask(taskId)));
    }

    @Operation(summary = "Download attachment")
    @GetMapping("/attachments/{attachmentId}/download")
    public ResponseEntity<Resource> downloadFile(@PathVariable Long attachmentId) {
        Resource resource = attachmentService.downloadFile(attachmentId);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @Operation(summary = "Delete attachment")
    @DeleteMapping("/attachments/{attachmentId}")
    public ResponseEntity<ApiResponse<Void>> deleteAttachment(
            @PathVariable Long attachmentId, Authentication auth) {
        attachmentService.deleteAttachment(attachmentId, auth.getName());
        return ResponseEntity.ok(ApiResponse.success("Attachment deleted", null));
    }
}
