package com.sharafatdin.talgatbek.taskmanager.repository;

import com.sharafatdin.talgatbek.taskmanager.entity.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/** @author Talgatbek Sharafatdin */
@Repository
public interface AttachmentRepository extends JpaRepository<Attachment, Long> {
    List<Attachment> findByTaskId(Long taskId);
    void deleteByTaskId(Long taskId);
}
