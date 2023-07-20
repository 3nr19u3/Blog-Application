package com.lgutierrez.blogrestapi.service;

import com.lgutierrez.blogrestapi.payload.CategoryDto;

import java.util.List;

public interface CategoryService {
    CategoryDto addCategory(CategoryDto categoryDto);

    CategoryDto getCategory(Long categoryId);

    List<CategoryDto> getAllCategories();

    CategoryDto updateCategory(CategoryDto categoryDto, long categoryId);

    void deleteCategory(long categoryId);
}
