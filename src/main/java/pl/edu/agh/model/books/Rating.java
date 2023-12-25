package pl.edu.agh.model.books;

import jakarta.persistence.*;
import pl.edu.agh.model.users.Member;

@Entity
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int ratingId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="title_id", referencedColumnName = "titleId")
    private Title title;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="user_id", referencedColumnName = "userId")
    private Member member;

    private int rate;

    private String comment;
}
