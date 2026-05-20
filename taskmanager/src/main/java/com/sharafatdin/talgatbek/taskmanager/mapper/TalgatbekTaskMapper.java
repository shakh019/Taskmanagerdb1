package com.sharafatdin.talgatbek.taskmanager.mapper;

import com.sharafatdin.talgatbek.taskmanager.dto.response.TaskResponse;
import com.sharafatdin.talgatbek.taskmanager.entity.Task;
import org.mapstruct.*;

/** @author Talgatbek Sharafatdin */
@Mapper(componentModel = "spring", uses = {TalgatbekUserMapper.class, TalgatbekCategoryMapper.class})
public interface TalgatbekTaskMapper {
    @Mapping(target = "projectId", source = "project.id")
    @Mapping(target = "projectName", source = "project.name")
    @Mapping(target = "commentsCount", expression = "java(task.getComments() != null ? task.getComments().size() : 0)")
    @Mapping(target = "attachmentsCount", expression = "java(task.getAttachments() != null ? task.getAttachments().size() : 0)")
    TaskResponse toResponse(Task task);
}
