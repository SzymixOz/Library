package pl.edu.agh.model;

import javax.persistence.*;
import java.util.Date;

@Entity
public class HistoricalLoan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int loan_id;
    private Date start_loan_date;
    private Date end_loan_date;
    private Date return_loan_date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", referencedColumnName = "id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="book_id", referencedColumnName = "book_id")
    private Book book;
}
