package mk.ukim.finki.lab2.service.impl;


import mk.ukim.finki.lab2.model.Author;
import mk.ukim.finki.lab2.model.Book;
import mk.ukim.finki.lab2.model.enumerations.Category;
import mk.ukim.finki.lab2.model.exceptions.AuthorNotFoundException;
import mk.ukim.finki.lab2.model.exceptions.BookNotFoundException;
import mk.ukim.finki.lab2.repository.jpa.BookRepository;
import mk.ukim.finki.lab2.service.AuthorService;
import mk.ukim.finki.lab2.service.BookService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final AuthorService authorService;

    public BookServiceImpl(BookRepository bookRepository, AuthorService authorService) {
        this.bookRepository = bookRepository;
        this.authorService = authorService;
    }

    @Override
    public List<Book> findAll() {
        return this.bookRepository.findAll();
    }

    @Override
    public Optional<Book> findBookById(Long id) {
        return this.bookRepository.findById(id);
    }

    @Override
    public Optional<Book> addNewBook(String name, Category category, Long author, Integer availableCopies) {
        Author bookAuthor = this.authorService.findById(author).orElseThrow(() -> new AuthorNotFoundException(author));

        return Optional.of(this.bookRepository.save(new Book(name, category, bookAuthor, availableCopies)));
    }

    @Override
    public void deleteBook(Long id) {
        Book book = this.bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException(""+id));
        this.bookRepository.deleteById(id);
    }

    @Override
    public Optional<Book> editBook(Long id, String name, Category category, Long author, Integer availableCopies) {
        Book book = this.bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException(name));
        Author bookAuthor = this.authorService.findById(author).orElseThrow(() -> new AuthorNotFoundException(author));

        book.setName(name);
        book.setCategory(category);
        book.setAuthor(bookAuthor);
        book.setAvailableCopies(availableCopies);

        return Optional.of(this.bookRepository.save(book));
    }

    @Override
    public Optional<Book> markAsTaken(String name) {
        Book book = this.bookRepository.findByName(name).orElseThrow(() -> new BookNotFoundException(name));
        Integer copies = book.getAvailableCopies()-1;
        book.setAvailableCopies(copies);

        return Optional.of(this.bookRepository.save(book));
    }
}
