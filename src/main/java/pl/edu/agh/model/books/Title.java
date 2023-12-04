package pl.edu.agh.model.books;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Title {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int title_id;

    private String title;
    private String author;

    //raczej go nie interesuje lista booków, więc jej nie wrzucam
}
