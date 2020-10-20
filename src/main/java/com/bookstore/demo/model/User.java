package com.bookstore.demo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "users")
public class User {
    @Id
    private String id;
    @Column(unique = true)
    private String username;
    @Column
    private String password;
    @OneToMany(mappedBy = "user")
    private List<ShoppingCart> shoppingCarts;
}
