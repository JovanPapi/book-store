package com.bookstore.demo.service.Impl;

import com.bookstore.demo.model.Book;
import com.bookstore.demo.model.DTO.UserDTO;
import com.bookstore.demo.model.ShoppingCart;
import com.bookstore.demo.model.StatusOfCart;
import com.bookstore.demo.model.User;
import com.bookstore.demo.repository.ShoppingCartRepository;
import com.bookstore.demo.repository.UserRepository;
import com.bookstore.demo.service.UserService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ShoppingCartRepository cartRepository;

    public UserServiceImpl(UserRepository userRepository, ShoppingCartRepository cartRepository) {
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
    }

    @Override
    public List<User> fetchAllUsers() {
        if (userRepository.findAll().isEmpty()) {
            userRepository.save(new User(UUID.randomUUID().toString(), "Jovan",
                    "papalazoski", new ArrayList<>()));
        }
        return userRepository.findAll();
    }

    @Override
    public void updateUser(UserDTO userDTO) {
        User user = userRepository.findById(userDTO.getId()).get();
        user.setPassword(userDTO.getPassword());
        user.setUsername(userDTO.getUsername());
        userRepository.save(user);
    }

    @Override
    public void saveNewUser(UserDTO userDTO) {
        User user = new User(UUID.randomUUID().toString(), userDTO.getUsername(), userDTO.getPassword(), null);
        userRepository.save(user);
    }

    @Override
    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }

    @Override
    public void deleteCart(String userId) {
        User user = userRepository.findById(userId).get();
        cartRepository.deleteById(user.getShoppingCarts().get(0).getId());

    }

    @Override
    public boolean userBuyCart(String userId) {
        User user = userRepository.findById(userId).get();
        ShoppingCart cart = user.getShoppingCarts().get(0);
        List<Book> cartBooks = cart.getBooks();
        for (Book b : cartBooks) {
            if (b.getNumberOfCopies() - 1 < 0) {
                return false;
            } else {
                b.setNumberOfCopies(b.getNumberOfCopies() - 1);
            }
        }
        LocalDateTime dateTime = LocalDateTime.now();
        cart.setClosedCart(dateTime);
        cart.setStatusOfCart(StatusOfCart.SUCCESS);

        cartRepository.deleteById(cart.getId());
        return true;
    }
}
