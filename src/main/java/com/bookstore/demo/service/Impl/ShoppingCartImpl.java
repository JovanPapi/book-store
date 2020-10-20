package com.bookstore.demo.service.Impl;

import com.bookstore.demo.model.Book;
import com.bookstore.demo.model.ShoppingCart;
import com.bookstore.demo.model.StatusOfCart;
import com.bookstore.demo.model.User;
import com.bookstore.demo.repository.ShoppingCartRepository;
import com.bookstore.demo.repository.UserRepository;
import com.bookstore.demo.service.ShoppingCartService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class ShoppingCartImpl implements ShoppingCartService {
    private final UserRepository userRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final BookServiceImpl bookService;

    public ShoppingCartImpl(UserRepository userRepository, ShoppingCartRepository shoppingCartRepository, BookServiceImpl bookService) {
        this.userRepository = userRepository;
        this.shoppingCartRepository = shoppingCartRepository;
        this.bookService = bookService;
    }

    @Override
    public void createCart(String userId) {
        User user = userRepository.findById(userId).get();
        LocalDateTime dateTime = LocalDateTime.now();
        ShoppingCart shoppingCart = new ShoppingCart(UUID.randomUUID().toString(),
                StatusOfCart.CREATE, dateTime, null, user, null);

        shoppingCartRepository.save(shoppingCart);
    }

    @Override
    public List<ShoppingCart> fetchAllCarts() {
        return shoppingCartRepository.findAll();
    }

    @Override
    public Book addBookToCart(String id) {
        Book book = bookService.getAllBooks().stream()
                .filter(book1 -> book1.getId().equals(id))
                .findFirst()
                .get();
        List<ShoppingCart> carts = shoppingCartRepository.findAll();
        if (carts.size() == 0) {
            return null;
        }
        carts.get(0).getBooks().add(book);
        book.setCarts(carts);

        shoppingCartRepository.saveAll(carts);

        return book;
    }

    @Override
    public Book deleteBookFromCart(String id) {
        List<ShoppingCart> carts = shoppingCartRepository.findAll();
        List<Book> cartBooks = carts.get(0).getBooks();
        Book book = cartBooks.stream()
                .filter(book1 -> book1.getId().equals(id))
                .findFirst()
                .get();
        cartBooks.remove(book);
        carts.get(0).setBooks(cartBooks);

        shoppingCartRepository.saveAll(carts);
        return book;
    }
}
