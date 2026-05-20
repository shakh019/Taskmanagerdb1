package com.sharafatdin.talgatbek.taskmanager.service.impl;

import com.sharafatdin.talgatbek.taskmanager.dto.request.CategoryRequest;
import com.sharafatdin.talgatbek.taskmanager.dto.response.CategoryResponse;
import com.sharafatdin.talgatbek.taskmanager.entity.Category;
import com.sharafatdin.talgatbek.taskmanager.exception.*;
import com.sharafatdin.talgatbek.taskmanager.mapper.TalgatbekCategoryMapper;
import com.sharafatdin.talgatbek.taskmanager.repository.CategoryRepository;
import com.sharafatdin.talgatbek.taskmanager.service.TalgatbekCategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/** @author Talgatbek Sharafatdin */
@Service @RequiredArgsConstructor @Slf4j
public class TalgatbekCategoryServiceImpl implements TalgatbekCategoryService {
    private final CategoryRepository categoryRepository;
    private final TalgatbekCategoryMapper categoryMapper;

    @Override @Transactional
    public CategoryResponse createCategory(CategoryRequest request) {
        if (categoryRepository.existsByName(request.getName())) {
            throw new TalgatbekBadRequestException("Category already exists: " + request.getName());
        }
        Category cat = Category.builder().name(request.getName())
                .description(request.getDescription()).color(request.getColor()).build();
        log.info("Created category: {}", request.getName());
        return categoryMapper.toResponse(categoryRepository.save(cat));
    }

    @Override
    public CategoryResponse getCategoryById(Long id) {
        return categoryMapper.toResponse(categoryRepository.findById(id)
                .orElseThrow(() -> new TalgatbekResourceNotFoundException("Category", id)));
    }

    @Override
    public List<CategoryResponse> getAllCategories() {
        return categoryRepository.findAll().stream().map(categoryMapper::toResponse).toList();
    }

    @Override @Transactional
    public CategoryResponse updateCategory(Long id, CategoryRequest request) {
        Category cat = categoryRepository.findById(id)
                .orElseThrow(() -> new TalgatbekResourceNotFoundException("Category", id));
        if (request.getName() != null) cat.setName(request.getName());
        if (request.getDescription() != null) cat.setDescription(request.getDescription());
        if (request.getColor() != null) cat.setColor(request.getColor());
        return categoryMapper.toResponse(categoryRepository.save(cat));
    }

    @Override @Transactional
    public void deleteCategory(Long id) {
        if (!categoryRepository.existsById(id)) throw new TalgatbekResourceNotFoundException("Category", id);
        categoryRepository.deleteById(id);
        log.info("Deleted category id: {}", id);
    }
}
