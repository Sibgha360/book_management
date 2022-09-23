package com.optimizely.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.optimizely.model.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
//    List<Book> findByAuthor(String title);
    Book findByIsbn(String title);
}
