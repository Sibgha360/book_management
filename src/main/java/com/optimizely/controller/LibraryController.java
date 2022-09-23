package com.optimizely.controller;

import com.optimizely.model.Book;
import com.optimizely.model.Megazine;
import com.optimizely.service.AuthorHasBookService;
import com.optimizely.service.BookService;
import com.optimizely.service.MegazineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api")
public class LibraryController {
    @Autowired
    BookService bookService;

    @Autowired
    MegazineService megazineService;

    @Autowired
    AuthorHasBookService authorHasBookService;

    @GetMapping("/books")
    public ResponseEntity<List<Book>> getAllBooks(@RequestParam("author_email") Optional<String> authorEmail, @RequestParam("sort_by") Optional<String> sortByproperty) throws Exception {
        List<Book> books;

        if (authorEmail.isPresent()) {
            books = authorHasBookService.findBookByAuthor(authorEmail.get());
        }
        else if(sortByproperty.isPresent())
        {
            books = bookService.findAllSortedBy(sortByproperty.get());
        }
        else {
            books = bookService.findAllBooks();
        }
        return ResponseEntity.status(HttpStatus.OK).body(books);
    }

    @GetMapping("/book")
    public ResponseEntity<Book> getBookByIsbn(@RequestParam("isbn") String isbn) throws Exception {
        Book magazine = bookService.findByIsbn(isbn);
        return ResponseEntity.status(HttpStatus.OK).body(magazine);
    }

    @GetMapping("/megazines")
    public ResponseEntity<List<Megazine>> getAllMegazines
            (@RequestParam("author_email") Optional<String> authorEmail, @RequestParam("sort_by") Optional<String> sortByproperty) throws Exception {
        List<Megazine> magazines = null;

        if (authorEmail.isPresent()) {
            magazines = authorHasBookService.findMegazineByAuthor(authorEmail.get());

        }
        else if(sortByproperty.isPresent())
        {
            magazines = megazineService.findAllSortedBy(sortByproperty.get());
        }
        else {
            magazines = megazineService.findAllMegazines();
        }

        return ResponseEntity.status(HttpStatus.OK).body(magazines);
    }

    @GetMapping("/megazine")
    public ResponseEntity<Megazine> getMegazineByIsbn(@RequestParam("isbn") String isbn) throws Exception {
        Megazine magazine = megazineService.findByIsbn(isbn);
        return ResponseEntity.status(HttpStatus.OK).body(magazine);
    }
}