package com.sharafatdin.talgatbek.taskmanager.service;

import com.sharafatdin.talgatbek.taskmanager.dto.request.CommentRequest;
import com.sharafatdin.talgatbek.taskmanager.dto.response.*;
import org.springframework.data.domain.Pageable;

/** @author Talgatbek Sharafatdin */
public interface TalgatbekCommentService {
    CommentResponse createComment(Long taskId, CommentRequest request, String username);
    PagedResponse<CommentResponse> getCommentsByTask(Long taskId, Pageable pageable);
    CommentResponse updateComment(Long commentId, CommentRequest request, String username);
    void deleteComment(Long commentId, String username);
}
