package pl.edu.agh.session;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import pl.edu.agh.enums.UserRoleEnum;
import pl.edu.agh.model.books.CoverType;
import pl.edu.agh.model.books.Title;
import pl.edu.agh.model.extra.HistoricalLoanDetails;
import pl.edu.agh.model.extra.LoanDetails;
import pl.edu.agh.model.loans.HistoricalLoan;
import pl.edu.agh.model.loans.Loan;
import pl.edu.agh.model.users.User;
import pl.edu.agh.service.BookService;

@Component
public class UserSession {
    private User user;
    private UserRoleEnum role;
    private ObservableList<LoanDetails> loanDetails;
    private ObservableList<HistoricalLoanDetails> historicalLoanDetails;
    private final BookService bookService;
    private UserSession(BookService bookService) {
        this.bookService = bookService;
    }
    public void setUser(User user) {
        this.user = user;
        if(user == null) return;
        loanDetails = FXCollections.observableList(bookService.getAllUserLoans(user));
        historicalLoanDetails = FXCollections.observableList(bookService.getAllUserHistoricalLoans(user));
    }
    public User getUser() {
        return this.user;
    }

    public UserRoleEnum getRole() {
        return role;
    }

    public void setRole(UserRoleEnum role) {
        this.role = role;
    }

    public ObservableList<LoanDetails> getLoanDetails() {
        return loanDetails;
    }
    public ObservableList<HistoricalLoanDetails> getHistoricalLoanDetails() {
        return historicalLoanDetails;
    }

    public void resetLoans() {
        loanDetails = FXCollections.observableList(bookService.getAllUserLoans(user));
        historicalLoanDetails = FXCollections.observableList(bookService.getAllUserHistoricalLoans(user));
    }
}
