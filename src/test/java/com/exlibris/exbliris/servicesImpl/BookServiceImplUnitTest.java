package com.exlibris.exbliris.servicesImpl;

import com.exlibris.exbliris.database.BookRepository;
import com.exlibris.exbliris.models.Book;
import com.exlibris.exbliris.models.Library;
import com.exlibris.exbliris.models.user.Users;
import com.exlibris.exbliris.services.BookService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class BookServiceImplUnitTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookService bookService;

    private List<String> author = new ArrayList<>();
    private List<Library> libraries = new ArrayList<>();

    private Users users = new Users(1L, "NightsWolf", "123", "dawi@wp.pl", "Dawid", "Całkowksi");
    private Library library = new Library(1L, "Bokshelf", users, "kaowpdkawd", new Date());
    private Book book = new Book(1L, author, "Wydawnictowo", libraries, new Date());

    @BeforeEach
    void setUp() {
        bookService = new BookServiceImpl(bookRepository);
    }

    @Test
    void shouldAddBook() {
        Mockito.when(bookRepository.save(book)).thenReturn(book);

        bookService.addBook(book);

        ArgumentCaptor<Book> bookArgumentCaptor = ArgumentCaptor.forClass(Book.class);
        Mockito.verify(bookRepository).save(bookArgumentCaptor.capture());
        Book capturedBook = bookArgumentCaptor.getValue();

        Assertions.assertEquals(book, capturedBook);
    }

    @Test
    void shouldGetBook() {
        Mockito.when(bookRepository.findById(book.getId())).thenReturn(Optional.ofNullable(book));

        Book response = bookService.getBook(book.getId());

        Assertions.assertEquals(book, response);
    }

    @Test
    void shouldThrowBookNotFound() {
        Mockito.when(bookRepository.findById(book.getId())).thenThrow(HttpClientErrorException.class);

        Assertions.assertThrows(HttpClientErrorException.class, () -> bookService.getBook(book.getId()));
    }

    @Test
    void shouldGetAllBooks() {
        List<Book> books = new ArrayList<>();
        books.add(book);

        Mockito.when(bookRepository.findAll()).thenReturn(books);

        List<Book> response = bookService.getAllBooks();

        Assertions.assertEquals(books, response);
    }

    @Test
    void shouldEditBook() {
        Book editedBook = new Book(1L, author, "Inne", libraries, new Date());

        Mockito.when(bookRepository.findById(book.getId())).thenReturn(Optional.ofNullable(book));

        bookService.editBook(book.getId(), editedBook);

        ArgumentCaptor<Book> bookArgumentCaptor = ArgumentCaptor.forClass(Book.class);
        Mockito.verify(bookRepository).save(bookArgumentCaptor.capture());
        Book capturedBook = bookArgumentCaptor.getValue();

        Assertions.assertEquals(editedBook, capturedBook);
    }

    @Test
    void shouldThrowBookToEditNotFound() {
        Book editedBook = new Book(1L, author, "Inne", libraries, new Date());

        Mockito.when(bookRepository.findById(book.getId())).thenThrow(HttpClientErrorException.class);

        Assertions.assertThrows(HttpClientErrorException.class, () -> bookService.editBook(book.getId(), editedBook));
    }

    @Test
    void deleteBook() {
        bookService.deleteBook(book.getId());

        Mockito.verify(bookRepository).deleteById(book.getId());
    }
}