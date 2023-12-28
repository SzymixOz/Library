package pl.edu.agh.repository.loans;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.edu.agh.model.books.Book;
import pl.edu.agh.model.books.Title;
import pl.edu.agh.model.loans.HistoricalLoan;
import pl.edu.agh.model.users.User;

public interface HistoricalLoanRepository extends JpaRepository<HistoricalLoan, Integer> {
    @Query("SELECT COUNT(hl) > 0 FROM HistoricalLoan hl WHERE hl.book.title = :title AND hl.member.userId = :userId")
    boolean hasUserBorrowedBook(@Param("title") Title title, @Param("userId") int userId);
}
