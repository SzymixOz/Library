package pl.edu.agh.model.books;

import jakarta.persistence.*;
import pl.edu.agh.model.loans.HistoricalLoan;
import pl.edu.agh.model.loans.Loan;

import java.util.List;

@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int bookId;

    @Enumerated(EnumType.STRING)
    private CoverType coverType;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "book")
    private Loan loan;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "book")
    private List<HistoricalLoan> historicalLoans;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="title_id", referencedColumnName = "titleId")
    private Title title;

    public Book(CoverType coverType, Title title) {
        this.coverType = coverType;
        this.title = title;
    }

    public Book() {

    }
}
