package pl.edu.agh.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int book_id;
    private int isbn;

    @Enumerated(EnumType.STRING)
    private CoverType coverType;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "book")
    private Loan loan;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "book")
    private List<HistoricalLoan> historicalLoans;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="title_id", referencedColumnName = "title_id")
    private Title title;
}
