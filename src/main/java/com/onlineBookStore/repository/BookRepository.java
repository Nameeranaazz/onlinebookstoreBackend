package com.onlineBookStore.repository;

import com.onlineBookStore.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByCategory(String category);
}
