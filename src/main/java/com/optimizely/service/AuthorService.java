package com.optimizely.service;

import com.optimizely.model.Author;
import com.optimizely.model.AuthorHasBook;
import com.optimizely.model.Book;
import com.optimizely.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthorService {
    @Autowired
    AuthorRepository authorRepository;

    public Author findByEmail(String email) {
        Author author = authorRepository.findByEmail(email);
        return author;
    }

    public List<Book> findByAuthor(String authorEmail) {

        List<Book> books = new ArrayList<>();
        List<AuthorHasBook> authorHasBooks = findByEmail(authorEmail).getAuthorHasBook();
        authorHasBooks.forEach(authorHasBook -> {
            books.add(authorHasBook.getBook());
        });

        return books;
    }
}
