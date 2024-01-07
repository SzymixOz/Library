package pl.edu.agh.repository.loans;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.edu.agh.model.response.IBookResponse;
import pl.edu.agh.model.books.Title;
import pl.edu.agh.model.loans.HistoricalLoan;

import java.util.List;

public interface HistoricalLoanRepository extends JpaRepository<HistoricalLoan, Integer> {
    @Query("SELECT COUNT(hl) > 0 FROM HistoricalLoan hl WHERE hl.book.title = :title AND hl.member.userId = :userId")
    boolean hasUserBorrowedBook(@Param("title") Title title, @Param("userId") int userId);

    @Query("SELECT l.book.title.title as title, count(*) as amount FROM HistoricalLoan l GROUP BY l.book.title ORDER BY count(*) DESC")
    List<IBookResponse> findTheMostFrequentlyBorrowedBooks();
}
