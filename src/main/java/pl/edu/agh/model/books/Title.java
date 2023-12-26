package pl.edu.agh.model.books;


import jakarta.persistence.*;

import java.sql.Blob;

@Entity
public class Title {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int titleId;

    private long isbn;
    private String title;
    private String author;

    @Lob
    private Blob image;

    //raczej go nie interesuje lista booków, więc jej nie wrzucam

    public Title(Long isbn, String title, String author, Blob image) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.image = image;
    }

    public Title() {

    }
}
