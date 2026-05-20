package com.sharafatdin.talgatbek.taskmanager.service.impl;

import com.sharafatdin.talgatbek.taskmanager.dto.request.CommentRequest;
import com.sharafatdin.talgatbek.taskmanager.dto.response.*;
import com.sharafatdin.talgatbek.taskmanager.entity.*;
import com.sharafatdin.talgatbek.taskmanager.exception.*;
import com.sharafatdin.talgatbek.taskmanager.mapper.TalgatbekCommentMapper;
import com.sharafatdin.talgatbek.taskmanager.repository.*;
import com.sharafatdin.talgatbek.taskmanager.service.TalgatbekCommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** @author Talgatbek Sharafatdin */
@Service @RequiredArgsConstructor @Slf4j
public class TalgatbekCommentServiceImpl implements TalgatbekCommentService {
    private final CommentRepository commentRepository;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final TalgatbekCommentMapper commentMapper;

    @Override @Transactional
    public CommentResponse createComment(Long taskId, CommentRequest request, String username) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TalgatbekResourceNotFoundException("Task", taskId));
        User author = userRepository.findByUsername(username)
                .orElseThrow(() -> new TalgatbekResourceNotFoundException("User not found"));
        Comment comment = Comment.builder().content(request.getContent()).task(task).author(author).build();
        log.info("Comment added to task {} by {}", taskId, username);
        return commentMapper.toResponse(commentRepository.save(comment));
    }

    @Override
    public PagedResponse<CommentResponse> getCommentsByTask(Long taskId, Pageable pageable) {
        if (!taskRepository.existsById(taskId)) throw new TalgatbekResourceNotFoundException("Task", taskId);
        Page<Comment> page = commentRepository.findByTaskId(taskId, pageable);
        return PagedResponse.<CommentResponse>builder()
                .content(page.getContent().stream().map(commentMapper::toResponse).toList())
                .page(page.getNumber()).size(page.getSize())
                .totalElements(page.getTotalElements()).totalPages(page.getTotalPages()).last(page.isLast()).build();
    }

    @Override @Transactional
    public CommentResponse updateComment(Long commentId, CommentRequest request, String username) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new TalgatbekResourceNotFoundException("Comment", commentId));
        if (!comment.getAuthor().getUsername().equals(username)) {
            throw new TalgatbekUnauthorizedException("Not authorized to update this comment");
        }
        comment.setContent(request.getContent());
        return commentMapper.toResponse(commentRepository.save(comment));
    }

    @Override @Transactional
    public void deleteComment(Long commentId, String username) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new TalgatbekResourceNotFoundException("Comment", commentId));
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new TalgatbekResourceNotFoundException("User not found"));
        if (!comment.getAuthor().getUsername().equals(username) && user.getRole() != User.Role.ROLE_ADMIN) {
            throw new TalgatbekUnauthorizedException("Not authorized to delete this comment");
        }
        commentRepository.deleteById(commentId);
        log.info("Deleted comment id: {}", commentId);
    }
}
