package com.sharafatdin.talgatbek.taskmanager.service.impl;

import com.sharafatdin.talgatbek.taskmanager.dto.response.AttachmentResponse;
import com.sharafatdin.talgatbek.taskmanager.entity.*;
import com.sharafatdin.talgatbek.taskmanager.exception.*;
import com.sharafatdin.talgatbek.taskmanager.mapper.TalgatbekAttachmentMapper;
import com.sharafatdin.talgatbek.taskmanager.repository.*;
import com.sharafatdin.talgatbek.taskmanager.service.TalgatbekAttachmentService;
import com.sharafatdin.talgatbek.taskmanager.util.TalgatbekFileStorageUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.util.List;

/** @author Talgatbek Sharafatdin */
@Service @RequiredArgsConstructor @Slf4j
public class TalgatbekAttachmentServiceImpl implements TalgatbekAttachmentService {
    private final AttachmentRepository attachmentRepository;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final TalgatbekFileStorageUtil fileStorageUtil;
    private final TalgatbekAttachmentMapper attachmentMapper;

    @Override @Transactional
    public AttachmentResponse uploadFile(Long taskId, MultipartFile file, String username) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TalgatbekResourceNotFoundException("Task", taskId));
        User uploader = userRepository.findByUsername(username)
                .orElseThrow(() -> new TalgatbekResourceNotFoundException("User not found"));
        String storedName = fileStorageUtil.storeFile(file);
        Attachment attachment = Attachment.builder()
                .originalName(file.getOriginalFilename())
                .storedName(storedName)
                .filePath(fileStorageUtil.getUploadDir() + "/" + storedName)
                .contentType(file.getContentType())
                .fileSize(file.getSize())
                .task(task).uploadedBy(uploader).build();
        Attachment saved = attachmentRepository.save(attachment);
        log.info("Uploaded file {} to task {}", file.getOriginalFilename(), taskId);
        AttachmentResponse response = attachmentMapper.toResponse(saved);
        response.setDownloadUrl("/api/attachments/" + saved.getId() + "/download");
        return response;
    }

    @Override
    public List<AttachmentResponse> getAttachmentsByTask(Long taskId) {
        return attachmentRepository.findByTaskId(taskId).stream()
                .map(a -> {
                    AttachmentResponse r = attachmentMapper.toResponse(a);
                    r.setDownloadUrl("/api/attachments/" + a.getId() + "/download");
                    return r;
                }).toList();
    }

    @Override
    public Resource downloadFile(Long attachmentId) {
        Attachment attachment = attachmentRepository.findById(attachmentId)
                .orElseThrow(() -> new TalgatbekResourceNotFoundException("Attachment", attachmentId));
        try {
            Path filePath = fileStorageUtil.loadFile(attachment.getStoredName());
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                log.info("Downloading file: {}", attachment.getOriginalName());
                return resource;
            } else {
                throw new TalgatbekFileStorageException("File not found: " + attachment.getOriginalName());
            }
        } catch (MalformedURLException e) {
            throw new TalgatbekFileStorageException("File not found", e);
        }
    }

    @Override @Transactional
    public void deleteAttachment(Long attachmentId, String username) {
        Attachment attachment = attachmentRepository.findById(attachmentId)
                .orElseThrow(() -> new TalgatbekResourceNotFoundException("Attachment", attachmentId));
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new TalgatbekResourceNotFoundException("User not found"));
        if (!attachment.getUploadedBy().getUsername().equals(username) && user.getRole() != User.Role.ROLE_ADMIN) {
            throw new TalgatbekUnauthorizedException("Not authorized to delete this attachment");
        }
        fileStorageUtil.deleteFile(attachment.getStoredName());
        attachmentRepository.deleteById(attachmentId);
        log.info("Deleted attachment id: {}", attachmentId);
    }
}
