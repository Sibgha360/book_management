package com.optimizely.service;

import com.optimizely.model.*;
import com.optimizely.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthorHasBookService {
    @Autowired
    AuthorRepository authorRepository;

    public List<Book> findBookByAuthor(String authorEmail) {

        List<Book> books = new ArrayList<>();
        List<AuthorHasBook> authorHasBooks = authorRepository.findByEmail(authorEmail).getAuthorHasBook();
        authorHasBooks.forEach(authorHasBook -> {
            books.add(authorHasBook.getBook());
        });

        return books;
    }

    public List<Megazine> findMegazineByAuthor(String authorEmail) {

        List<Megazine> megazine = new ArrayList<>();
        List<AuthorHasMegazine> authorHasBooks = authorRepository.findByEmail(authorEmail).getAuthorHasMegazine();
        authorHasBooks.forEach(authorHasBook -> {
            megazine.add(authorHasBook.getMegazine());
        });

        return megazine;
    }

}
