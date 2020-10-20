package com.bookstore.demo.service.Impl;

import com.bookstore.demo.model.Category;
import com.bookstore.demo.model.DTO.CategoryDTO;
import com.bookstore.demo.repository.CategoryRepository;
import com.bookstore.demo.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public void saveCategory(CategoryDTO newCategory) {
        Category saveCategory = new Category(UUID.randomUUID().toString(), newCategory.getName(),
                newCategory.getDescription(), null);
        categoryRepository.save(saveCategory);
    }

    @Override
    public void updateCategory(CategoryDTO editCategory) {
        Category category = categoryRepository.findById(editCategory.getId()).get();
        category.setName(editCategory.getName());
        category.setDescription(editCategory.getDescription());
        categoryRepository.save(category);
    }

    @Override
    public void deleteCategory(String id) {
        categoryRepository.deleteById(id);
    }
}
