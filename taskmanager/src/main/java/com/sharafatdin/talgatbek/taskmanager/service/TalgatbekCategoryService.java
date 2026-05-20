package com.sharafatdin.talgatbek.taskmanager.service;

import com.sharafatdin.talgatbek.taskmanager.dto.request.CategoryRequest;
import com.sharafatdin.talgatbek.taskmanager.dto.response.CategoryResponse;
import java.util.List;

/** @author Talgatbek Sharafatdin */
public interface TalgatbekCategoryService {
    CategoryResponse createCategory(CategoryRequest request);
    CategoryResponse getCategoryById(Long id);
    List<CategoryResponse> getAllCategories();
    CategoryResponse updateCategory(Long id, CategoryRequest request);
    void deleteCategory(Long id);
}
