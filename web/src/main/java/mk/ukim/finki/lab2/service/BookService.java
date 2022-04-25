package mk.ukim.finki.lab2.service;

import mk.ukim.finki.lab2.model.Book;
import mk.ukim.finki.lab2.model.enumerations.Category;

import java.util.List;
import java.util.Optional;

public interface BookService {
    List<Book> findAll();
    Optional<Book> addNewBook(String name, Category category, Long author, Integer availableCopies);
    void deleteBook(Long id);
    Optional<Book> editBook(Long id, String name, Category category, Long author, Integer availableCopies);
    Optional<Book> markAsTaken(String name);
    Optional<Book> findBookById(Long id);
}
