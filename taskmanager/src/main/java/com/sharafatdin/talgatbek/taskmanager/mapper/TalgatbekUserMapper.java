package com.sharafatdin.talgatbek.taskmanager.mapper;

import com.sharafatdin.talgatbek.taskmanager.dto.response.UserResponse;
import com.sharafatdin.talgatbek.taskmanager.entity.User;
import org.mapstruct.*;

/** @author Talgatbek Sharafatdin */
@Mapper(componentModel = "spring")
public interface TalgatbekUserMapper {
    UserResponse toResponse(User user);
}
