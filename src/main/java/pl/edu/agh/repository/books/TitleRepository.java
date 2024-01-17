package pl.edu.agh.repository.books;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.edu.agh.model.books.CoverType;
import pl.edu.agh.model.books.Rating;
import pl.edu.agh.model.books.Title;
import pl.edu.agh.model.extra.TitleDetails;
import pl.edu.agh.model.users.Member;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface TitleRepository extends JpaRepository<Title, Integer> {
    @Query("SELECT COUNT(t) FROM Title t")
    Integer getAmountOfTitles();

    @Transactional
    @Query("SELECT COUNT(t) FROM Title t JOIN Loan l ON l.title=t " +
            "WHERE t.titleId = :titleId AND l.coverType = :coverType")
    Integer countBorrowedBooksByTitleIdAndCover(@Param("titleId") int titleId,
                                                @Param("coverType") CoverType coverType);

    @Query("SELECT DISTINCT hl.member FROM HistoricalLoan hl WHERE hl.title IN :titles")
    Set<Member> findMembersByTitle(@Param("titles") Set<Title> titles);

    @Query("SELECT (SUM(softCoverQuantity) + SUM(hardCoverQuantity)) FROM Title t")
    Integer getAmountOfAllBooks();
}
