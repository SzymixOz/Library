package pl.edu.agh.model.loans;

import jakarta.persistence.*;
import pl.edu.agh.model.books.Book;
import pl.edu.agh.model.users.Member;

import java.util.Date;

@Entity
public class HistoricalLoan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int loanId;
    private Date startLoanDate;
    private Date endLoanDate;
    private Date returnLoanDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", referencedColumnName = "userId")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="book_id", referencedColumnName = "bookId")
    private Book book;
}
