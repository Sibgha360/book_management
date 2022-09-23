package com.optimizely.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "author_has_book")
@NoArgsConstructor
@Getter
public class AuthorHasBook {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "author_has_book_id")
    private Integer authorHasBookId;

    @ManyToOne
    @JoinColumn(name = "book_id")
    @JsonIgnore
    Book book;

    @ManyToOne
    @JoinColumn(name = "author_id")
    @JsonIgnore
    Author author;

    public AuthorHasBook(Book book, Author author) {
        this.book = book;
        this.author = author;
    }
}
