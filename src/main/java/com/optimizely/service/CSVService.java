package com.optimizely.service;

import java.util.List;

import com.optimizely.helper.CSVHelper;
import com.optimizely.model.Book;
import com.optimizely.model.Author;
import com.optimizely.model.Megazine;
import com.optimizely.repository.AuthorRepository;
import com.optimizely.repository.BookRepository;
import com.optimizely.repository.MegazineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class CSVService {
    @Autowired
    BookRepository bookRepository;
    @Autowired
    AuthorRepository authorRepository;
    @Autowired
    MegazineRepository megazineRepository;

    @Autowired
    BookService bookService;

    @Autowired
    AuthorService authorService;

    public void saveAuthors(MultipartFile file) throws Exception {
        List<Author> authors = CSVHelper.csvToAuthors(file.getInputStream());
        authorRepository.saveAll(authors);
    }

    public void saveBooks(MultipartFile file) throws Exception {
        List<Book> books = CSVHelper.csvToBooks(file.getInputStream(), authorService);
        bookRepository.saveAll(books);
    }

    public void saveMegazine(MultipartFile file) throws Exception {
        List<Megazine> megazine = CSVHelper.csvToMegazine(file.getInputStream(), authorService);
        megazineRepository.saveAll(megazine);
    }
}
