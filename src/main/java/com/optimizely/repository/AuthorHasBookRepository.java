package com.optimizely.repository;

import com.optimizely.model.Author;
import com.optimizely.model.AuthorHasBook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorHasBookRepository extends JpaRepository<AuthorHasBook, Long> {
    AuthorHasBook findByAuthor(Author author);

}
