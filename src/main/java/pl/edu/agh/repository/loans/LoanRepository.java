package pl.edu.agh.repository.loans;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.edu.agh.model.books.Book;
import pl.edu.agh.model.loans.Loan;
import pl.edu.agh.model.users.User;

import java.util.Date;

public interface LoanRepository extends JpaRepository<Loan, Integer> {
}
