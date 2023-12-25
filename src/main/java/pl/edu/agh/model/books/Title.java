package pl.edu.agh.model.books;


import jakarta.persistence.*;

import java.sql.Blob;

@Entity
public class Title {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int titleId;

    private int isbn;
    private String title;
    private String author;

    @Lob
    private Blob image;

    //raczej go nie interesuje lista booków, więc jej nie wrzucam
}
