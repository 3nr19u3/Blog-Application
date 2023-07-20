package com.lgutierrez.blogrestapi.service.impl;

import com.lgutierrez.blogrestapi.entity.Category;
import com.lgutierrez.blogrestapi.exception.ResourceNotFoundException;
import com.lgutierrez.blogrestapi.payload.CategoryDto;
import com.lgutierrez.blogrestapi.repository.CategoryRepository;
import com.lgutierrez.blogrestapi.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    private CategoryRepository categoryRepository;
    private ModelMapper modelMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository,
                               ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CategoryDto addCategory(CategoryDto categoryDto) {
        Category category = mapToEntity(categoryDto);
        Category newCategory = categoryRepository.save(category);
        return mapToDto(newCategory);
    }

    @Override
    public CategoryDto getCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow( () -> new ResourceNotFoundException("Category","id",categoryId));

        return mapToDto(category);
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream().map((category) -> mapToDto(category)).collect(Collectors.toList());
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));

        category.setName(categoryDto.getName());
        category.setDescription(categoryDto.getDescription());
        category.setId(categoryId);
        Category category1 = categoryRepository.save(category);
        return mapToDto(category1);
    }

    @Override
    public void deleteCategory(long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                                              .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));
        categoryRepository.delete(category);
    }

    private Category mapToEntity(CategoryDto categoryDto){
        return modelMapper.map(categoryDto, Category.class);
     }

    private CategoryDto mapToDto(Category category){
        return modelMapper.map(category, CategoryDto.class);
    }
}
