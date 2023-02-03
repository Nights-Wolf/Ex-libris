package com.exlibris.exbliris.services;

import com.exlibris.exbliris.models.Book;

import java.util.List;

public interface BookService {

    void addBook(Book book);

    Book getBook (Long id);

    List<Book> getAllBooks();

    void editBook(Long id, Book book);

    void deleteBook(Long id);
}
