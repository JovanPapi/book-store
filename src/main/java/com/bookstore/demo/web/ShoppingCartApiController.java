package com.bookstore.demo.web;

import com.bookstore.demo.model.Book;
import com.bookstore.demo.model.ShoppingCart;
import com.bookstore.demo.service.Impl.ShoppingCartImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/shopping-cart")
public class ShoppingCartApiController {
    private final ShoppingCartImpl shoppingCart;

    public ShoppingCartApiController(ShoppingCartImpl shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    @PostMapping(value = "/create/{id}")
    public String createCart(@PathVariable String id) {
        shoppingCart.createCart(id);
        return "The cart was succesfully created. Go back and REFRESH the page!";
    }

    @PostMapping(value = "/add-book-to-cart/{id}")
    public String addBookToCart(@PathVariable String id) {
        if (shoppingCart.fetchAllCarts().size() == 0) {
            return "You must first create shopping cart! Click the button below to create cart";
        }
        Book book = shoppingCart.addBookToCart(id);
        return "Success! The book " + book.getName() + " was successfully added in cart. Go back and REFRESH the page.";
    }

    @PostMapping(value = "/delete-from-cart/{id}")
    public String deleteBookFromCart(@PathVariable String id) {
        Book book = shoppingCart.deleteBookFromCart(id);
        return "Success! The book " + book.getName() + " was successfully removed from cart. Go back and REFRESH the page.";
    }
}
