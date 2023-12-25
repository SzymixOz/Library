package pl.edu.agh.model.users;

import jakarta.persistence.*;
import pl.edu.agh.model.loans.HistoricalLoan;
import pl.edu.agh.model.loans.Loan;

import java.util.List;

@Entity
@PrimaryKeyJoinColumn(name = "userId")
public class Member extends User {

    private Boolean newsLetter;
    private Boolean banned;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "member")
    private List<Loan> loans;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "member")
    private List<HistoricalLoan> historicalLoans;

    public Member(String firstName, String lastName, String email, Boolean newsLetter) {
        super(firstName, lastName, email);
        this.newsLetter = newsLetter;
        this.banned = false;
    }
    public Member() {
    }

    public Boolean getNewsLetter() {
        return newsLetter;
    }

    public void ban() {
        this.banned = true;
    }
    public void unban() {
        this.banned = false;
    }
}
