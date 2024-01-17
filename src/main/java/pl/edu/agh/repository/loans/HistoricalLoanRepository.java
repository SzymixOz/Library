package pl.edu.agh.repository.loans;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.edu.agh.model.response.IBookResponse;
import pl.edu.agh.model.books.Title;
import pl.edu.agh.model.extra.HistoricalLoanDetails;
import pl.edu.agh.model.loans.HistoricalLoan;
import pl.edu.agh.model.users.Member;
import pl.edu.agh.model.users.User;

import java.util.List;
import java.util.Set;

public interface HistoricalLoanRepository extends JpaRepository<HistoricalLoan, Integer> {
    @Query("SELECT COUNT(hl) > 0 FROM HistoricalLoan hl WHERE hl.title = :title AND hl.member.userId = :userId")
    boolean hasUserBorrowedBook(@Param("title") Title title, @Param("userId") int userId);

    @Query("SELECT l.title.title as title, count(*) as amount FROM HistoricalLoan l GROUP BY l.title ORDER BY count(*) DESC")
    List<IBookResponse> findTheMostFrequentlyBorrowedBooks();

    @Query("SELECT DISTINCT hl FROM HistoricalLoan hl WHERE hl.member IN :members")
    Set<HistoricalLoan> findAllHistoricalLoansByMembers(@Param("members") Set<Member> members);

    @Query("SELECT DISTINCT hl.title FROM HistoricalLoan hl " +
            "WHERE hl.member IN :members AND hl IN :historicalLoans " +
            "GROUP BY hl.title " +
            "ORDER BY COUNT(hl) DESC")
    List<Title> findBestPropositions(
            @Param("members") Set<Member> members,
            @Param("historicalLoans") Set<HistoricalLoan> historicalLoans
    );
    @Query("SELECT new pl.edu.agh.model.extra.HistoricalLoanDetails(h.title, h.startLoanDate, h.endLoanDate, h.returnLoanDate) FROM HistoricalLoan h WHERE h.member.userId = :userId")
    List<HistoricalLoanDetails> findAllHistoricalLoansByUserId(@Param("userId") long userId);
}
