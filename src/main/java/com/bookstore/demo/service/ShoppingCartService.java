package com.bookstore.demo.service;

import com.bookstore.demo.model.Book;
import com.bookstore.demo.model.DTO.ShoppingCartDTO;
import com.bookstore.demo.model.ShoppingCart;

import java.util.List;

public interface ShoppingCartService {
    void createCart(String userId);
    List<ShoppingCart> fetchAllCarts();
    Book addBookToCart(String id);
    Book deleteBookFromCart(String id);
}
