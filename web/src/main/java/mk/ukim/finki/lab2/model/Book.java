package mk.ukim.finki.lab2.model;

import lombok.Data;
import mk.ukim.finki.lab2.model.enumerations.Category;

import javax.persistence.*;


@Data
@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    @Enumerated(value = EnumType.STRING)
    private Category category;

    @OneToOne
    private Author author;

    private Integer availableCopies;

    public Book(){

    }
    public Book(String name, Category category, Author author, Integer availableCopies) {
        this.name = name;
        this.category = category;
        this.author = author;
        this.availableCopies = availableCopies;
    }
}