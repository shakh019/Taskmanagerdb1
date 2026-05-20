package com.sharafatdin.talgatbek.taskmanager.mapper;

import com.sharafatdin.talgatbek.taskmanager.dto.response.CommentResponse;
import com.sharafatdin.talgatbek.taskmanager.entity.Comment;
import org.mapstruct.*;

/** @author Talgatbek Sharafatdin */
@Mapper(componentModel = "spring", uses = {TalgatbekUserMapper.class})
public interface TalgatbekCommentMapper {
    @Mapping(target = "taskId", source = "task.id")
    CommentResponse toResponse(Comment comment);
}
