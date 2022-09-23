package com.optimizely.repository;

import com.optimizely.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    Author findByEmail(String email);
}
