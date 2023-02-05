package com.exlibris.exbliris.api;

import com.exlibris.exbliris.database.BookRepository;
import com.exlibris.exbliris.models.Book;
import com.exlibris.exbliris.models.Library;
import com.exlibris.exbliris.models.user.Users;
import com.exlibris.exbliris.services.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(BookRestController.class)
class BookRestControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper = new ObjectMapper();
    @MockBean
    private BookService bookService;

    @MockBean
    private BookRepository bookRepository;

    private List<String> author = new ArrayList<>();
    private List<Library> libraries = new ArrayList<>();
    private Users users = new Users(1L, "NightsWolf", "123", "dawi@wp.pl", "Dawid", "Całkowksi");
    private Library library = new Library(1L, "Bokshelf", users, "kaowpdkawd", new Date());
    private Book book = new Book(1L, author, "Wydawnictowo", libraries, new Date());

    @BeforeEach
    void setUp() {
        bookRepository.save(book);
    }

    @Test
    void shouldAddBook() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/book")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(book)))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        Mockito.verify(bookService).addBook(Mockito.any(Book.class));
    }

    @Test
    void shouldReturnBadRequest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/book")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void shouldGetBook() throws Exception {
        Mockito.when(bookService.getBook(1L)).thenReturn(book);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/book/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(bookService).getBook(1L);
    }

    @Test
    void shouldThrowBookNotFound() throws Exception {
        Mockito.when(bookService.getBook(1L)).thenReturn(null);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/book/1")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(MockMvcResultMatchers.status().isNotFound());

        Mockito.verify(bookService).getBook(1L);
    }

    @Test
    void shouldGetAllBooks() throws Exception {
        List<Book> books = new ArrayList<>();
        books.add(book);

        Mockito.when(bookService.getAllBooks()).thenReturn(books);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/book/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(bookService).getAllBooks();
    }

    @Test
    void shouldEditBook() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/api/book/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(book)))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        Mockito.verify(bookService).editBook(Mockito.eq(1L), Mockito.any(Book.class));
    }

    @Test
    void shouldDeleteBook() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/book/1"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        Mockito.verify(bookService).deleteBook(1L);
    }
}