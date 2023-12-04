package pl.edu.agh.model.books;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Title {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int title_id;

    private String title;
    private String author;

    //raczej go nie interesuje lista booków, więc jej nie wrzucam
}
