package com.exlibris.exbliris.api;

import com.exlibris.exbliris.models.Book;
import com.exlibris.exbliris.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("api/book")
public class BookRestController {

    private final BookService bookService;

    @Autowired
    public BookRestController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    private ResponseEntity<Object> addBook(Book book) {
        try {
            bookService.addBook(book);

            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (HttpClientErrorException e) {
            HashMap error = new HashMap<>();
            error.put("Message", e.getMessage());
            error.put("Status", e.getStatusCode());
            error.put("Cause", e.getCause());

            return new ResponseEntity<>(error, e.getStatusCode());
        }
    }

    @GetMapping("/{id}")
    private ResponseEntity<Object> getBook(@PathVariable("id") Long id) {
        try {
            Book book = bookService.getBook(id);

            return new ResponseEntity<>(book, HttpStatus.OK);
        } catch (HttpClientErrorException e) {
            HashMap error = new HashMap<>();
            error.put("Message", e.getMessage());
            error.put("Status", e.getStatusCode());
            error.put("Cause", e.getCause());

            return new ResponseEntity<>(error, e.getStatusCode());
        }
    }

    @GetMapping("/all")
    private ResponseEntity<Object> getAllBooks() {
        try {
            List<Book> books = bookService.getAllBooks();

            return new ResponseEntity<>(books, HttpStatus.OK);
        } catch (HttpClientErrorException e) {
            HashMap error = new HashMap<>();
            error.put("Message", e.getMessage());
            error.put("Status", e.getStatusCode());
            error.put("Cause", e.getCause());

            return new ResponseEntity<>(error, e.getStatusCode());
        }
    }

    @PutMapping("/{id}")
    private ResponseEntity<Object> editBook(@PathVariable("id") Long id, @RequestBody Book book) {
        try {
            bookService.editBook(id, book);

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (HttpClientErrorException e) {
            HashMap error = new HashMap<>();
            error.put("Message", e.getMessage());
            error.put("Status", e.getStatusCode());
            error.put("Cause", e.getCause());

            return new ResponseEntity<>(error, e.getStatusCode());
        }
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<Object> deleteBook(@PathVariable("id") Long id) {
        try {
            bookService.deleteBook(id);

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (HttpClientErrorException e) {
            HashMap error = new HashMap<>();
            error.put("Message", e.getMessage());
            error.put("Status", e.getStatusCode());
            error.put("Cause", e.getCause());

            return new ResponseEntity<>(error, e.getStatusCode());
        }
    }
}
