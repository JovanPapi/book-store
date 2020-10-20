package com.bookstore.demo.service;

import com.bookstore.demo.model.Book;
import com.bookstore.demo.model.DTO.BookDTO;

import java.io.IOException;
import java.util.List;

public interface BookService {
    List<Book> getAllBooks();
    void updateBook(BookDTO editBook,String bookId) throws IOException;
    void saveBook(BookDTO newBook) throws IOException;
    void deleteBook(String id);
}
