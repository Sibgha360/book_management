package com.optimizely.service;

import com.optimizely.constants.ExceptionConstants;
import com.optimizely.helper.exception.ResourceNotFoundException;
import com.optimizely.model.Book;
import com.optimizely.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    @Autowired
    BookRepository bookRepository;

    public List<Book> findAllBooks() throws Exception {
        List<Book> books = bookRepository.findAll();
        if (books.isEmpty()) {
            throw new ResourceNotFoundException(ExceptionConstants.BOOK_NOT_FOUND);
        }
        return books;
    }

    public Book findByIsbn(String isbn) throws Exception {
        Book book = bookRepository.findByIsbn(isbn);
        if (book == null) {
            throw new ResourceNotFoundException(ExceptionConstants.BOOK_NOT_FOUND_FOR_ISBN + isbn);
        }
        return book;
    }

    public List<Book> findAllSortedBy(String property) throws Exception
    {
        Sort by = Sort.by(Sort.Direction.ASC, property);
        return bookRepository.findAll(by);
    }
}
