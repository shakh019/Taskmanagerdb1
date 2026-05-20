package com.sharafatdin.talgatbek.taskmanager.mapper;

import com.sharafatdin.talgatbek.taskmanager.dto.response.ProjectResponse;
import com.sharafatdin.talgatbek.taskmanager.entity.Project;
import org.mapstruct.*;

/** @author Talgatbek Sharafatdin */
@Mapper(componentModel = "spring", uses = {TalgatbekUserMapper.class})
public interface TalgatbekProjectMapper {
    @Mapping(target = "tasksCount", expression = "java(project.getTasks() != null ? project.getTasks().size() : 0)")
    ProjectResponse toResponse(Project project);
}
