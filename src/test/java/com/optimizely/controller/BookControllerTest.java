package com.optimizely.controller;

import com.optimizely.model.Book;
import com.optimizely.service.AuthorHasBookService;
import com.optimizely.service.BookService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class BookControllerTest {
    static PodamFactory factory = new PodamFactoryImpl();
    private static List<Book> listOfMockBooks;
    private static String mockAuthorEmail;
    private static Book mockBook;

    /**
     * The controller that we want to test.
     */
    @Autowired
    private LibraryController controller;

    /**
     * A mock version of the bookService, authorService for use in our tests.
     */
    @MockBean
    private BookService bookService;

    @MockBean
    private AuthorHasBookService authorHasBookService;


    @BeforeAll
    static void init() {
        // create mock data
        listOfMockBooks = factory.manufacturePojo(List.class, Book.class);
        mockBook = factory.manufacturePojo(Book.class);
        mockAuthorEmail = factory.manufacturePojo(String.class);
    }

    @Test
    public void getBooks_WhenBooksPresent_ShouldReturnTheListOfBooksAndSucessResponse() throws Exception {
        // ARRANGE
        doReturn(listOfMockBooks).when(bookService).findAllBooks();

        // ACT
        ResponseEntity<List<Book>> responseEntity = controller.getAllBooks(Optional.empty(), Optional.empty());

        // ASSERT
        assertThat(responseEntity)
                .isNotNull()
                .isInstanceOf(ResponseEntity.class)
                .satisfies(status -> assertThat(status.getStatusCode()).isNotNull()
                        .satisfies(code -> assertThat(code)).isEqualTo(HttpStatus.OK))
                .satisfies(body -> assertThat(body.getBody()).isNotNull().isInstanceOf(List.class).hasSize(listOfMockBooks.size()));
    }

    @Test
    public void getBooks_WhenSortedbyTitle_ShouldReturnTheListOfBooksAndSucessResponse() throws Exception {
        // ARRANGE
        doReturn(listOfMockBooks).when(bookService).findAllSortedBy("title");

        // ACT
        ResponseEntity<List<Book>> responseEntity = controller.getAllBooks(Optional.empty(), Optional.of("title"));

        // ASSERT
        assertThat(responseEntity)
                .isNotNull()
                .isInstanceOf(ResponseEntity.class)
                .satisfies(status -> assertThat(status.getStatusCode()).isNotNull()
                        .satisfies(code -> assertThat(code)).isEqualTo(HttpStatus.OK))
                .satisfies(body -> assertThat(body.getBody()).isNotNull().isInstanceOf(List.class).hasSize(listOfMockBooks.size()));
    }

    @Test
    public void getBooks_WhenPassedAuthor_ShouldReturnTheListOfBooksAndSucessResponse() throws Exception {
        // ARRANGE
        doReturn(listOfMockBooks).when(authorHasBookService).findBookByAuthor(mockAuthorEmail);

        // ACT
        ResponseEntity<List<Book>> responseEntity = controller.getAllBooks(Optional.of(mockAuthorEmail), Optional.empty());

        // ASSERT
        assertThat(responseEntity)
                .isNotNull()
                .isInstanceOf(ResponseEntity.class)
                .satisfies(status -> assertThat(status.getStatusCode()).isNotNull()
                        .satisfies(code -> assertThat(code)).isEqualTo(HttpStatus.OK))
                .satisfies(body -> assertThat(body.getBody()).isNotNull().isInstanceOf(List.class).hasSize(listOfMockBooks.size()));
    }


    @Test
    public void getAvailableBooks_WhenAvailableBooksExist_ShouldReturnTheListOfBooksAndSucessResponse() throws Exception {
        // ARRANGE
        doReturn(listOfMockBooks).when(bookService).findAllBooks();

        // ACT
        ResponseEntity<List<Book>> responseEntity = controller.getAllBooks(Optional.empty(), Optional.empty());

        // ASSERT
        assertThat(responseEntity)
                .isNotNull()
                .isInstanceOf(ResponseEntity.class)
                .satisfies(status -> assertThat(status.getStatusCode()).isNotNull()
                        .satisfies(code -> assertThat(code)).isEqualTo(HttpStatus.OK))
                .satisfies(body -> assertThat(body.getBody()).isNotNull().isInstanceOf(List.class).hasSize(listOfMockBooks.size()));
    }

    @ParameterizedTest
    @CsvSource({"2547-8548-2541",
            "2587-2448-2541",
            "2547-8548-2556"})
    public void getBookByIsbn_WithIsbnWhichExists_ShouldReturnTheListOfBooksAndSucessResponse(String isbn) throws Exception {
        // ARRANGE
        doReturn(mockBook).when(bookService).findByIsbn(any());

        // ACT
        ResponseEntity<Book> responseEntity = controller.getBookByIsbn(isbn);

        // ASSERT
        assertThat(responseEntity)
                .isNotNull()
                .isInstanceOf(ResponseEntity.class)
                .satisfies(status -> assertThat(status.getStatusCode()).isNotNull()
                        .satisfies(code -> assertThat(code)).isEqualTo(HttpStatus.OK))
                .satisfies(body -> assertThat(body.getBody()).isNotNull().isInstanceOf(Book.class));
    }
//
//
//    @ParameterizedTest
//    @CsvSource({"0, 2018/11/25, 2020/12/25",
//            "-1, 2018/10/25, 2018/12/25",
//            "12, 2019/07/25, 2018/12/25"})
//    public void getBooksByUser_WithInvalidArguments_ShouldThrowBadRequestException(Integer userId, @ConvertWith(SlashyDateConverter.class) LocalDate from, @ConvertWith(SlashyDateConverter.class) LocalDate to) throws Exception {
//        // ASSERT
//        assertThatThrownBy(() -> {
//            // ACT
//            controller.getBooksByBorrower(userId, from, to);
//        }).isInstanceOf(BadRequestException.class);
//    }
}

