package com.sharafatdin.talgatbek.taskmanager.async;

import com.sharafatdin.talgatbek.taskmanager.entity.Task;
import com.sharafatdin.talgatbek.taskmanager.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/** @author Talgatbek Sharafatdin */
@Service
@RequiredArgsConstructor
@Slf4j
public class TalgatbekAsyncService {

    private final TaskRepository taskRepository;

    @Async("talgatbekTaskExecutor")
    public CompletableFuture<Long> countTasksByUserAsync(Long userId, Task.TaskStatus status) {
        log.info("[ASYNC] Counting tasks for user {} with status {}", userId, status);
        try {
            Thread.sleep(100); // simulate processing
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        long count = taskRepository.countByAssigneeIdAndStatus(userId, status);
        log.info("[ASYNC] Found {} tasks for user {} with status {}", count, userId, status);
        return CompletableFuture.completedFuture(count);
    }

    @Async("talgatbekTaskExecutor")
    public CompletableFuture<String> sendTaskNotificationAsync(Long taskId, String assigneeEmail, String action) {
        log.info("[ASYNC] Sending notification to {} for task {} - action: {}", assigneeEmail, taskId, action);
        try {
            Thread.sleep(200); // simulate email sending
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        String result = String.format("Notification sent to %s for task %d: %s", assigneeEmail, taskId, action);
        log.info("[ASYNC] Notification result: {}", result);
        return CompletableFuture.completedFuture(result);
    }

    @Async("talgatbekTaskExecutor")
    public CompletableFuture<List<Task>> getOverdueTasksAsync() {
        log.info("[ASYNC] Checking for overdue tasks...");
        List<Task> allTasks = taskRepository.findAll();
        List<Task> overdue = allTasks.stream()
                .filter(t -> t.getDueDate() != null
                        && t.getDueDate().isBefore(java.time.LocalDate.now())
                        && t.getStatus() != Task.TaskStatus.DONE
                        && t.getStatus() != Task.TaskStatus.CANCELLED)
                .toList();
        log.info("[ASYNC] Found {} overdue tasks", overdue.size());
        return CompletableFuture.completedFuture(overdue);
    }
}
