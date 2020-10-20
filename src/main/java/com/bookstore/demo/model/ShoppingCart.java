package com.bookstore.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "shopping_carts")
public class ShoppingCart {
    @Id
    private String id;
    @Column
    private StatusOfCart statusOfCart;
    @Column
    private LocalDateTime dateOpenCart;
    @Column
    private LocalDateTime closedCart;
    @ManyToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    private User user;
    @ManyToMany
    private List<Book> books;
}
