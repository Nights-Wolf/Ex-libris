package com.exlibris.exbliris.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Setter
@Getter
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id", scope = Book.class)
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private List<String> author;
    private String publishingHouse;
    @OneToMany(cascade = CascadeType.MERGE)
    @JoinColumn(name = "library_id", referencedColumnName = "id")
    private List<Library> library;
    private Date created;

    public Book(Long id, List<String> author, String publishingHouse, List<Library> library, Date created) {
        this.id = id;
        this.author = author;
        this.publishingHouse = publishingHouse;
        this.library = library;
        this.created = created;
    }
}
