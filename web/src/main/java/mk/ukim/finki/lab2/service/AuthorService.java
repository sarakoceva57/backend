package mk.ukim.finki.lab2.service;

import mk.ukim.finki.lab2.model.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorService {
    List<Author> findAll();
    Optional<Author> findById(Long id);
    Optional<Author> findByFullName(String name, String surname);
}
