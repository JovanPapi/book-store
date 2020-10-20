package com.bookstore.demo.web;

import com.bookstore.demo.model.Book;
import com.bookstore.demo.model.Category;
import com.bookstore.demo.model.DTO.BookDTO;
import com.bookstore.demo.model.User;
import com.bookstore.demo.service.Impl.BookServiceImpl;
import com.bookstore.demo.service.Impl.CategoryServiceImpl;
import com.bookstore.demo.service.Impl.UserServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookServiceImpl bookService;
    private final CategoryServiceImpl categoryService;
    private final UserServiceImpl userService;

    public BookController(BookServiceImpl bookService, CategoryServiceImpl categoryService, UserServiceImpl userService) {
        this.bookService = bookService;
        this.categoryService = categoryService;
        this.userService = userService;
    }

    @GetMapping
    public String getAllBooksPage(Model model) {
        List<Book> books = bookService.getAllBooks();
        List<User> users = userService.fetchAllUsers();
        model.addAttribute("books", books);
        model.addAttribute("user", users.get(0));

        return "show-all-books";
    }

    @GetMapping("/add-new")
    public String addNewBookPage(Model model) {
        List<Category> categories = categoryService.getAllCategories();

        model.addAttribute("categories", categories);
        model.addAttribute("book", new BookDTO());
        return "new-book";
    }

    @PostMapping
    public String saveNewBook(BookDTO newBook) {
        if (newBook.getNumberOfCopies() < 0) {
            return "redirect:/books/add-new" + "?error=The value of this input must not be negative!";
        }
        try {
            bookService.saveBook(newBook);
        } catch (IOException e) {
            e.printStackTrace();
        }
        User user = userService.fetchAllUsers().get(0);
        return "redirect:/books";
    }

    @GetMapping("/edit/{id}")
    public String editBookPage(Model model, @PathVariable String id) {
        List<Book> books = bookService.getAllBooks();
        Book book = books.stream()
                .filter(book1 -> book1.getId().equals(id))
                .findFirst()
                .get();
        List<Category> categories = categoryService.getAllCategories();

        model.addAttribute("book", book);
        model.addAttribute("categories", categories);
        return "edit-book";
    }

    @PostMapping("/edit")
    public String saveEditBook(BookDTO editBook, @RequestParam String bookId) {
        if (editBook.getNumberOfCopies() < 0) {
            return "redirect:/books/edit/" + bookId + "?error=The value of this input must not be negative!";
        }
        try {
            bookService.updateBook(editBook, bookId);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:/books";
    }

    @PostMapping("/delete/{id}")
    public String deleteBook(@PathVariable String id) {
        bookService.deleteBook(id);
        return "redirect:/books";
    }
}
