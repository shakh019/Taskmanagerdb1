package com.sharafatdin.talgatbek.taskmanager.repository;

import com.sharafatdin.talgatbek.taskmanager.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

/** @author Talgatbek Sharafatdin */
@Repository
public interface TaskRepository extends JpaRepository<Task, Long>, JpaSpecificationExecutor<Task> {
    Page<Task> findByProjectId(Long projectId, Pageable pageable);
    Page<Task> findByAssigneeId(Long assigneeId, Pageable pageable);
    List<Task> findByProjectId(Long projectId);
    @Query("SELECT t FROM Task t WHERE " +
           "(:status IS NULL OR t.status = :status) AND " +
           "(:priority IS NULL OR t.priority = :priority) AND " +
           "(:projectId IS NULL OR t.project.id = :projectId) AND " +
           "(:assigneeId IS NULL OR t.assignee.id = :assigneeId) AND " +
           "(:search IS NULL OR LOWER(t.title) LIKE LOWER(CONCAT('%',:search,'%')))")
    Page<Task> findTasksWithFilters(
            @Param("status") Task.TaskStatus status,
            @Param("priority") Task.Priority priority,
            @Param("projectId") Long projectId,
            @Param("assigneeId") Long assigneeId,
            @Param("search") String search,
            Pageable pageable);
    long countByAssigneeIdAndStatus(Long assigneeId, Task.TaskStatus status);
}
