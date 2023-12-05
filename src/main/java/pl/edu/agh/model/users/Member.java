package pl.edu.agh.model.users;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import pl.edu.agh.model.loans.HistoricalLoan;
import pl.edu.agh.model.loans.Loan;

import java.util.List;

@Entity
public class Member extends User {
    private Boolean newsLetter;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "member")
    private List<Loan> loans;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "member")
    private List<HistoricalLoan> historicalLoans;

    public Member(String firstName, String lastName, String email, Boolean newsLetter) {
        super(firstName, lastName, email);
        this.newsLetter = newsLetter;
    }
    public Member() {
    }

    public Boolean getNewsLetter() {
        return newsLetter;
    }
}
