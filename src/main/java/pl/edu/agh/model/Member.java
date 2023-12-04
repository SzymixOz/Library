package pl.edu.agh.model;

import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
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
