package pl.edu.agh.repository.loans;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.edu.agh.model.loans.Loan;

import java.sql.Timestamp;
import java.util.List;

public interface LoanRepository extends JpaRepository<Loan, Integer> {

    @Query("SELECT l.member.email, l.book.title.title FROM Loan l WHERE l.endLoanDate < :endDate AND l.member.emailNotification = true")
    List<String> findEmailsAndBooksForEmailNotification(@Param("endDate") Timestamp endDate);
}