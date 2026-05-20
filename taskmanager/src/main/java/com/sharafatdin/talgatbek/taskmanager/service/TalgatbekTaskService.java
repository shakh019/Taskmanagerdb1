package com.sharafatdin.talgatbek.taskmanager.service;

import com.sharafatdin.talgatbek.taskmanager.dto.request.TaskRequest;
import com.sharafatdin.talgatbek.taskmanager.dto.response.*;
import com.sharafatdin.talgatbek.taskmanager.entity.Task;
import org.springframework.data.domain.Pageable;

/** @author Talgatbek Sharafatdin */
public interface TalgatbekTaskService {
    TaskResponse createTask(TaskRequest request, String username);
    TaskResponse getTaskById(Long id);
    PagedResponse<TaskResponse> getAllTasks(Task.TaskStatus status, Task.Priority priority,
                                            Long projectId, Long assigneeId,
                                            String search, Pageable pageable);
    PagedResponse<TaskResponse> getTasksByProject(Long projectId, Pageable pageable);
    TaskResponse updateTask(Long id, TaskRequest request, String username);
    void deleteTask(Long id, String username);
}
