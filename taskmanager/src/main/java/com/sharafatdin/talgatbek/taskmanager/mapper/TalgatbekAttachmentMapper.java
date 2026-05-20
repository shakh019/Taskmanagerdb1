package com.sharafatdin.talgatbek.taskmanager.mapper;

import com.sharafatdin.talgatbek.taskmanager.dto.response.AttachmentResponse;
import com.sharafatdin.talgatbek.taskmanager.entity.Attachment;
import org.mapstruct.*;

/** @author Talgatbek Sharafatdin */
@Mapper(componentModel = "spring", uses = {TalgatbekUserMapper.class})
public interface TalgatbekAttachmentMapper {
    @Mapping(target = "taskId", source = "task.id")
    @Mapping(target = "downloadUrl", ignore = true)
    AttachmentResponse toResponse(Attachment attachment);
}
