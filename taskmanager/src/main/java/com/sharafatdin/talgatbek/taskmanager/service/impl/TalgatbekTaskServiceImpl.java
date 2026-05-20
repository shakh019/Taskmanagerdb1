package com.sharafatdin.talgatbek.taskmanager.service.impl;

import com.sharafatdin.talgatbek.taskmanager.async.TalgatbekAsyncService;
import com.sharafatdin.talgatbek.taskmanager.dto.request.TaskRequest;
import com.sharafatdin.talgatbek.taskmanager.dto.response.*;
import com.sharafatdin.talgatbek.taskmanager.entity.*;
import com.sharafatdin.talgatbek.taskmanager.exception.*;
import com.sharafatdin.talgatbek.taskmanager.mapper.TalgatbekTaskMapper;
import com.sharafatdin.talgatbek.taskmanager.repository.*;
import com.sharafatdin.talgatbek.taskmanager.service.TalgatbekTaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** @author Talgatbek Sharafatdin */
@Service @RequiredArgsConstructor @Slf4j
public class TalgatbekTaskServiceImpl implements TalgatbekTaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final CategoryRepository categoryRepository;
    private final TalgatbekTaskMapper taskMapper;
    private final TalgatbekAsyncService asyncService;

    @Override @Transactional
    public TaskResponse createTask(TaskRequest request, String username) {
        User creator = userRepository.findByUsername(username)
                .orElseThrow(() -> new TalgatbekResourceNotFoundException("User not found"));
        Task task = Task.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .status(request.getStatus() != null ? request.getStatus() : Task.TaskStatus.TODO)
                .priority(request.getPriority() != null ? request.getPriority() : Task.Priority.MEDIUM)
                .dueDate(request.getDueDate())
                .createdBy(creator)
                .build();
        if (request.getProjectId() != null) {
            task.setProject(projectRepository.findById(request.getProjectId())
                    .orElseThrow(() -> new TalgatbekResourceNotFoundException("Project", request.getProjectId())));
        }
        if (request.getAssigneeId() != null) {
            User assignee = userRepository.findById(request.getAssigneeId())
                    .orElseThrow(() -> new TalgatbekResourceNotFoundException("User", request.getAssigneeId()));
            task.setAssignee(assignee);
            asyncService.sendTaskNotificationAsync(0L, assignee.getEmail(), "ASSIGNED");
        }
        if (request.getCategoryId() != null) {
            task.setCategory(categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new TalgatbekResourceNotFoundException("Category", request.getCategoryId())));
        }
        Task saved = taskRepository.save(task);
        log.info("Created task id: {} by {}", saved.getId(), username);
        return taskMapper.toResponse(saved);
    }

    @Override
    public TaskResponse getTaskById(Long id) {
        return taskMapper.toResponse(taskRepository.findById(id)
                .orElseThrow(() -> new TalgatbekResourceNotFoundException("Task", id)));
    }

    @Override
    public PagedResponse<TaskResponse> getAllTasks(Task.TaskStatus status, Task.Priority priority,
                                                    Long projectId, Long assigneeId,
                                                    String search, Pageable pageable) {
        Page<Task> page = taskRepository.findTasksWithFilters(status, priority, projectId, assigneeId, search, pageable);
        return buildPagedResponse(page);
    }

    @Override
    public PagedResponse<TaskResponse> getTasksByProject(Long projectId, Pageable pageable) {
        if (!projectRepository.existsById(projectId)) throw new TalgatbekResourceNotFoundException("Project", projectId);
        Page<Task> page = taskRepository.findByProjectId(projectId, pageable);
        return buildPagedResponse(page);
    }

    @Override @Transactional
    public TaskResponse updateTask(Long id, TaskRequest request, String username) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TalgatbekResourceNotFoundException("Task", id));
        if (request.getTitle() != null) task.setTitle(request.getTitle());
        if (request.getDescription() != null) task.setDescription(request.getDescription());
        if (request.getStatus() != null) task.setStatus(request.getStatus());
        if (request.getPriority() != null) task.setPriority(request.getPriority());
        if (request.getDueDate() != null) task.setDueDate(request.getDueDate());
        if (request.getAssigneeId() != null) {
            User assignee = userRepository.findById(request.getAssigneeId())
                    .orElseThrow(() -> new TalgatbekResourceNotFoundException("User", request.getAssigneeId()));
            task.setAssignee(assignee);
            asyncService.sendTaskNotificationAsync(id, assignee.getEmail(), "UPDATED");
        }
        if (request.getCategoryId() != null) {
            task.setCategory(categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new TalgatbekResourceNotFoundException("Category", request.getCategoryId())));
        }
        log.info("Updated task id: {} by {}", id, username);
        return taskMapper.toResponse(taskRepository.save(task));
    }

    @Override @Transactional
    public void deleteTask(Long id, String username) {
        if (!taskRepository.existsById(id)) throw new TalgatbekResourceNotFoundException("Task", id);
        taskRepository.deleteById(id);
        log.info("Deleted task id: {} by {}", id, username);
    }

    private PagedResponse<TaskResponse> buildPagedResponse(Page<Task> page) {
        return PagedResponse.<TaskResponse>builder()
                .content(page.getContent().stream().map(taskMapper::toResponse).toList())
                .page(page.getNumber())
                .size(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .last(page.isLast())
                .build();
    }
}
