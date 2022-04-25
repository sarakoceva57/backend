package mk.ukim.finki.lab2.web.rest;

import mk.ukim.finki.lab2.model.Author;
import mk.ukim.finki.lab2.model.Book;
import mk.ukim.finki.lab2.model.User;
import mk.ukim.finki.lab2.model.dto.BookDto;
import mk.ukim.finki.lab2.model.dto.UserDto;
import mk.ukim.finki.lab2.model.enumerations.Category;
import mk.ukim.finki.lab2.model.exceptions.BookNotFoundException;
import mk.ukim.finki.lab2.service.AuthorService;
import mk.ukim.finki.lab2.service.BookService;
import mk.ukim.finki.lab2.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api")
public class BookRestController {
    private final BookService bookService;
    private final AuthorService authorService;
    private final UserService userService;

    public BookRestController(BookService bookService, AuthorService authorService, UserService userService) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.userService = userService;
    }

    @GetMapping("/books")
    public List<Book> findAll() {
        return this.bookService.findAll()
                .stream()
                .sorted(Comparator.comparing(Book::getName))
                .collect(Collectors.toList());
    }

    @GetMapping("/categories")
    public List<Category> findAllCategories() {
        return Arrays.stream(Category.values()).collect(Collectors.toList());
    }

    @GetMapping("/authors")
    public List<Author> findAllAuthors() {
        return this.authorService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBook(@PathVariable Long id) {
        return this.bookService.findBookById(id)
                .map(book -> ResponseEntity.ok().body(book))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @PostMapping("/reserve/{id}")
    public List<Book> markAsTaken(@PathVariable Long id) {
        Optional<Book> book = this.bookService.findBookById(id);

        if (book.isEmpty()) {
            throw new BookNotFoundException("ERROR");
        }

        this.bookService.markAsTaken(book.get().getName());
        return this.findAll();
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<Book> editBook(@PathVariable Long id, @RequestBody BookDto bookDto) {
        return this.bookService.editBook(id, bookDto.getName(), bookDto.getCategory(), bookDto.getAuthor(), bookDto.getAvailableCopies())
                .map(book -> ResponseEntity.ok().body(book))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @PostMapping("/add")
    public ResponseEntity<Book> addBook(@RequestBody BookDto bookDto) {
        return this.bookService.addNewBook(bookDto.getName(), bookDto.getCategory(), bookDto.getAuthor(), bookDto.getAvailableCopies())
                .map(book -> ResponseEntity.ok().body(book))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Book> deleteById(@PathVariable Long id) {
        this.bookService.deleteBook(id);

        if (this.bookService.findBookById(id).isPresent()) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok().build();
    }

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        return this.userService.register(user.getUsername(), user.getPassword(), user.getName(), user.getSurname(), user.getRole().toString())
                .map(tmp -> ResponseEntity.ok().body(tmp))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @PostMapping("/login")
    public ResponseEntity<User> loginUser(@RequestBody UserDto userDto) throws Exception {
        return this.userService.login(userDto.getUsername(), userDto.getPassword())
                .map(x-> ResponseEntity.ok().body(x))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }
}
