package pl.edu.agh.model.loans;

import jakarta.persistence.*;
import pl.edu.agh.model.books.Book;
import pl.edu.agh.model.users.Member;

import java.util.Date;

@Entity
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int loan_id;
    private Date start_loan_date;
    private Date end_loan_date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", referencedColumnName = "id")
    private Member member;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="book_id", referencedColumnName = "book_id")
    private Book book;

}
