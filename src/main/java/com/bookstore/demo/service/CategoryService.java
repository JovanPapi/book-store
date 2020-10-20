package com.bookstore.demo.service;

import com.bookstore.demo.model.Category;
import com.bookstore.demo.model.DTO.CategoryDTO;

import java.util.List;

public interface CategoryService {
    List<Category> getAllCategories();
    void saveCategory(CategoryDTO newCategory);
    void updateCategory(CategoryDTO editCategory);
    void deleteCategory(String id);
}
