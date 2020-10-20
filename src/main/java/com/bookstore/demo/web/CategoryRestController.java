package com.bookstore.demo.web;

import com.bookstore.demo.model.Book;
import com.bookstore.demo.model.Category;
import com.bookstore.demo.model.DTO.CategoryDTO;
import com.bookstore.demo.service.Impl.CategoryServiceImpl;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryRestController {

    private final CategoryServiceImpl categoryService;

    public CategoryRestController(CategoryServiceImpl categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public String getAllCategories(Model model) {
        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        return "show-all-categories";
    }

    @GetMapping("/add-new")
    public String addNewCategoryPage(Model model) {
        model.addAttribute("category", new Category());
        return "add-new-category";
    }

    @PostMapping
    public String saveNewCategory(@RequestBody CategoryDTO newCategory) {
        categoryService.saveCategory(newCategory);
        return "redirect:/api/categories";
    }

    @GetMapping("/edit/{id}")
    public String editCategoryPage(@PathVariable String id, Model model) {
        List<Category> categories = categoryService.getAllCategories();
        Category categoryToEdit = categories.stream()
                .filter(category -> category.getId().equals(id))
                .findFirst().get();

        model.addAttribute("category", categoryToEdit);
        return "edit-category";
    }

    @PostMapping("/edit")
    public String saveEditCategory(@RequestBody CategoryDTO editCategory) {
        categoryService.updateCategory(editCategory);
        return "redirect:/api/categories";
    }

    @PostMapping("/delete/{id}")
    public String deleteCategory(@PathVariable String id) {
        categoryService.deleteCategory(id);
        return "redirect:/api/categories";
    }

}
