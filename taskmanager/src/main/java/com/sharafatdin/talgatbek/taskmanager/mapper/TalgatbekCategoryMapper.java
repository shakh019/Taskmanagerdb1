package com.sharafatdin.talgatbek.taskmanager.mapper;

import com.sharafatdin.talgatbek.taskmanager.dto.response.CategoryResponse;
import com.sharafatdin.talgatbek.taskmanager.entity.Category;
import org.mapstruct.*;

/** @author Talgatbek Sharafatdin */
@Mapper(componentModel = "spring")
public interface TalgatbekCategoryMapper {
    CategoryResponse toResponse(Category category);
}
