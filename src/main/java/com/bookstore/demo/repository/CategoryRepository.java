package com.bookstore.demo.repository;

import com.bookstore.demo.model.Category;
import lombok.Getter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category,String> {

}
