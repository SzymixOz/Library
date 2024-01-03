package pl.edu.agh.repository.loans;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.edu.agh.model.books.Book;
import pl.edu.agh.model.books.CoverType;
import pl.edu.agh.model.extra.LoanDetails;
import pl.edu.agh.model.loans.Loan;
import pl.edu.agh.model.users.User;

import java.util.Date;
import java.util.List;

public interface LoanRepository extends JpaRepository<Loan, Integer> {
    @Query("SELECT new pl.edu.agh.model.extra.LoanDetails(l.book.title, l.startLoanDate, l.endLoanDate, l.loanId) FROM Loan l WHERE l.member.userId = :userId")
    List<LoanDetails> findAllLoansByUserId(@Param("userId") long userId);

    @Modifying
    @Query("DELETE FROM Loan l WHERE l.loanId = :loanId")
    void deleteByLoanId(@Param("loanId") int loanId);
}
