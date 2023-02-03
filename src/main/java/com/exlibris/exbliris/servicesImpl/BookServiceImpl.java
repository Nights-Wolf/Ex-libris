package com.exlibris.exbliris.servicesImpl;

import com.exlibris.exbliris.database.BookRepository;
import com.exlibris.exbliris.models.Book;
import com.exlibris.exbliris.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public void addBook(Book book) {
        bookRepository.save(book);
    }

    @Override
    public Book getBook(Long id) {
        Optional<Book> book = bookRepository.findById(id);

        if (book.isEmpty()) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
        }

        return book.get();
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public void editBook(Long id, Book book) {
        Optional<Book> getBook = bookRepository.findById(id).map(editedBook -> book);

        if (getBook.isEmpty()) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
        }

        bookRepository.save(getBook.get());
    }

    @Override
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }
}
