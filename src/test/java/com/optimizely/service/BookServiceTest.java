package com.optimizely.service;

import com.optimizely.constants.ExceptionConstants;
import com.optimizely.helper.exception.ResourceNotFoundException;
import com.optimizely.model.Book;
import com.optimizely.repository.BookRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class BookServiceTest {
    static PodamFactory factory = new PodamFactoryImpl();
    private static List<Book> listOfMockBooks;
    private static Book mockBook;

    /**
     * The service that we want to test.
     */
    @Autowired
    private BookService service;

    /**
     * A mock version of the BookRepository for use in our tests.
     */
    @MockBean
    private BookRepository repository;

    @BeforeAll
    static void init() {
        // create mock data
        listOfMockBooks = factory.manufacturePojo(List.class, Book.class);
        mockBook = factory.manufacturePojo(Book.class);

    }

    @Test
    public void findByIsbn_WhenRequestedWithValidIsbn_ShouldReturnItsBook() throws Exception {
        // ARRANGE
        doReturn(mockBook).when(repository).findByIsbn(any());

        // ACT
        Book returnedBook = service.findByIsbn(any());

        // ASSERT
        assertThat(returnedBook)
                .isNotNull()
                .isInstanceOf(Book.class)
                .isEqualTo(mockBook);
    }

    @Test
    public void findByIsbn_WhenRequestedWithInvalidIsbn_ShouldReturnResouceNotFoundException() {
        // ARRANGE
        doReturn(null).when(repository).findByIsbn(any());

        // ASSERT
        assertThatThrownBy(() -> {
            // ACT
            service.findByIsbn(any());
        }).isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining(ExceptionConstants.BOOK_NOT_FOUND_FOR_ISBN);
    }


    @Test
    public void findAllBooks_WhenBooksAreAvailable_ShouldReturnAListOfBook() throws Exception {
        // ARRANGE
        doReturn(listOfMockBooks).when(repository).findAll();

        // ACT
        List<Book> availableBooks = service.findAllBooks();

        // ASSERT
        assertThat(availableBooks)
                .isNotNull()
                .isInstanceOf(List.class)
                .hasSize(listOfMockBooks.size());
    }

    @Test
    public void findBooks_WhenItReturnsNull_ShouldThrowNotFoundException() throws Exception {
        // ARRANGE
        doReturn(Collections.emptyList()).when(repository).findAll();

        // ASSERT
        assertThatThrownBy(() -> {
            // ACT
            service.findAllBooks();
        }).isInstanceOf(ResourceNotFoundException.class).hasMessage(ExceptionConstants.BOOK_NOT_FOUND);
    }

    @Test
    public void findAllBooks_SortedByTitle_ShouldReturnCorrectOrder() throws Exception {
        // ARRANGE
        doReturn(listOfMockBooks).when(repository).findAll((Sort) any());

        // ACT
        List<Book> availableBooks = service.findAllSortedBy("title");

        // ASSERT
        assertThat(availableBooks)
                .isNotNull()
                .isInstanceOf(List.class)
                .hasSize(listOfMockBooks.size());
    }
}