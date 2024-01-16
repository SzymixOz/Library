package pl.edu.agh.repository.loans;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.edu.agh.model.response.IBookResponse;
import pl.edu.agh.model.extra.LoanDetails;
import pl.edu.agh.model.loans.Loan;
import pl.edu.agh.model.users.User;

import java.util.Date;
import java.util.List;

public interface LoanRepository extends JpaRepository<Loan, Integer> {
    @Query("SELECT l.member.email, l.title.title FROM Loan l WHERE l.endLoanDate = :endDate AND l.member.emailNotification = true")
    List<String> findEmailsAndBooksForEmailNotification(@Param("endDate") Date endDate);

    @Query("SELECT l.member.email FROM Loan l WHERE l.member.newsLetter = true")
    List<String> findEmailsForNewsLetter();

    @Query("SELECT l.title.title as title, count(*) as amount FROM Loan l GROUP BY l.title ORDER BY count(*) DESC")
    List<IBookResponse> findTheMostFrequentlyBorrowedBooks();

    @Query("SELECT l.title.title as title, count(*) as amount FROM Loan l WHERE l.member=:user GROUP BY l.title ORDER BY count(*) DESC")
    List<IBookResponse> findTheMostFrequentlyBorrowedBooksByUser(@Param("user") User user);

    @Query("SELECT COUNT(l) FROM Loan l")
    Integer getAmountOfCurrentlyBorrowedBooks();

    @Query("SELECT COUNT(l) FROM Loan l where l.member=:user")
    Integer getAmountOfCurrentlyBorrowedBooksByUser(@Param ("user") User user);
    @Query("SELECT new pl.edu.agh.model.extra.LoanDetails(l.title, l.startLoanDate, l.endLoanDate, l.loanId) FROM Loan l WHERE l.member.userId = :userId")
    List<LoanDetails> findAllLoansByUserId(@Param("userId") long userId);

    @Modifying
    @Query("DELETE FROM Loan l WHERE l.loanId = :loanId")
    void deleteByLoanId(@Param("loanId") int loanId);

    //SELECT t.title_id, title, author, COUNT(hl.loan_id)+COUNT(l.loan_id) AS together FROM title t
    // JOIN book b ON t.title_id=b.title_id
    // LEFT JOIN historical_loan hl ON hl.book_id=b.book_id
    // LEFT JOIN loan l ON l.book_id=b.book_id group by title HAVING COUNT(hl.loan_id)+COUNT(l.loan_id)>0 ORDER BY 4 DESC;
}
