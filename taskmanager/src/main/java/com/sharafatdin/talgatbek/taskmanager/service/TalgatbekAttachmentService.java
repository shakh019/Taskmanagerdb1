package com.sharafatdin.talgatbek.taskmanager.service;

import com.sharafatdin.talgatbek.taskmanager.dto.response.AttachmentResponse;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

/** @author Talgatbek Sharafatdin */
public interface TalgatbekAttachmentService {
    AttachmentResponse uploadFile(Long taskId, MultipartFile file, String username);
    List<AttachmentResponse> getAttachmentsByTask(Long taskId);
    org.springframework.core.io.Resource downloadFile(Long attachmentId);
    void deleteAttachment(Long attachmentId, String username);
}
