package com.bookstore.demo.service;

import com.bookstore.demo.model.DTO.UserDTO;
import com.bookstore.demo.model.User;

import java.util.List;

public interface UserService {
    List<User> fetchAllUsers();

    void updateUser(UserDTO userDTO);

    void saveNewUser(UserDTO userDTO);

    void deleteUser(String id);

    void deleteCart(String userId);

    boolean userBuyCart(String userId);
}
