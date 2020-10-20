package com.bookstore.demo.service.Impl;

import com.bookstore.demo.model.Book;
import com.bookstore.demo.model.Category;
import com.bookstore.demo.model.DTO.BookDTO;
import com.bookstore.demo.model.User;
import com.bookstore.demo.repository.BookRepository;
import com.bookstore.demo.repository.CategoryRepository;
import com.bookstore.demo.repository.UserRepository;
import com.bookstore.demo.service.BookService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    public BookServiceImpl(BookRepository bookRepository, CategoryRepository categoryRepository, UserRepository userRepository) {
        this.bookRepository = bookRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
    }


    @Override
    public List<Book> getAllBooks() {
        if (categoryRepository.findAll().isEmpty()) {
            List<Category> categories = new ArrayList<Category>() {{
                add(new Category(UUID.randomUUID().toString(), "Drama", "ne e dobra", null));
                add(new Category(UUID.randomUUID().toString(), "Roman", "ne e dobra", null));
                add(new Category(UUID.randomUUID().toString(), "Tragedija", "ne e dobra", null));
            }};
            categoryRepository.saveAll(categories);
        }

        return bookRepository.findAll();
    }

    @Override
    public void updateBook(BookDTO bookToEdit, String bookId) throws IOException {
        Book book = bookRepository.findById(bookId).get();
        Category category = categoryRepository.findById(bookToEdit.getCategoryId()).get();
        book.setCategory(category);
        book.setName(bookToEdit.getName());
        book.setNumberOfCopies(bookToEdit.getNumberOfCopies());

        byte[] imageBytes = bookToEdit.getImage().getBytes();
        String imageBase64 = String.format("data:%s;base64,%s", bookToEdit.getImage().getContentType(),
                Base64.getEncoder().encodeToString(imageBytes));

        book.setImage(imageBase64);
        bookRepository.save(book);
    }

    @Override
    public void saveBook(BookDTO newBook) throws IOException {
        Category category = new Category();
        if (newBook.getCategoryId() != null) {
            category = categoryRepository.findById(newBook.getCategoryId()).get();
        } else {
            category = null;
        }

        byte[] imageBytes = newBook.getImage().getBytes();
        String imageBase64 = String.format("data:%s;base64,%s", newBook.getImage().getContentType(),
                Base64.getEncoder().encodeToString(imageBytes));

        Random r = new Random();
        Book createBook = new Book(UUID.randomUUID().toString(), newBook.getName(), newBook.getNumberOfCopies(),
                category, imageBase64, null, r.nextInt(101));

        bookRepository.save(createBook);
    }

    @Override
    public void deleteBook(String id) {
        bookRepository.deleteById(id);
    }
}
