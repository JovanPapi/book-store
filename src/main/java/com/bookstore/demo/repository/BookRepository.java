package com.bookstore.demo.repository;

import com.bookstore.demo.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
// it does not create bean if the repository is abstract class and implements JpaRep. interface
// must check this problem why it occurs
public interface BookRepository extends JpaRepository<Book,String> {

}
