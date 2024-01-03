package pl.edu.agh.repository.loans;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.edu.agh.model.books.Book;
import pl.edu.agh.model.books.Title;
import pl.edu.agh.model.extra.HistoricalLoanDetails;
import pl.edu.agh.model.loans.HistoricalLoan;
import pl.edu.agh.model.loans.Loan;
import pl.edu.agh.model.users.User;

import java.util.List;

public interface HistoricalLoanRepository extends JpaRepository<HistoricalLoan, Integer> {
    @Query("SELECT COUNT(hl) > 0 FROM HistoricalLoan hl WHERE hl.book.title = :title AND hl.member.userId = :userId")
    boolean hasUserBorrowedBook(@Param("title") Title title, @Param("userId") int userId);

    @Query("SELECT new pl.edu.agh.model.extra.HistoricalLoanDetails(h.book.title, h.startLoanDate, h.endLoanDate, h.returnLoanDate) FROM HistoricalLoan h WHERE h.member.userId = :userId")
    List<HistoricalLoanDetails> findAllHistoricalLoansByUserId(@Param("userId") long userId);
}
