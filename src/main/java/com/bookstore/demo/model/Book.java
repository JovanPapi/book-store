package com.bookstore.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "books")
public class Book {
    @Id
    private String id;
    private String name;
    @Column
    private int numberOfCopies;
    @ManyToOne
    private Category category;
    @Column(length = 100000000)
    private String image;
    @ManyToMany(mappedBy = "books",
            cascade = CascadeType.ALL)
    @JsonIgnore
    private List<ShoppingCart> carts;

    @Column
    private int price;

}
