package pl.edu.agh.repository.books;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.edu.agh.model.books.CoverType;
import pl.edu.agh.model.books.Rating;
import pl.edu.agh.model.books.Title;
import pl.edu.agh.model.extra.TitleDetails;

import java.util.List;

public interface TitleRepository extends JpaRepository<Title, Integer> {
    @Query("SELECT COUNT(t) FROM Title t")
    Integer getAmountOfTitles();

    @Transactional
    @Query("SELECT COUNT(t) FROM Title t JOIN Loan l ON l.title=t " +
            "WHERE t.titleId = :titleId AND l.coverType = :coverType")
    Integer countBorrowedBooksByTitleIdAndCover(@Param("titleId") int titleId,
                                                @Param("coverType") CoverType coverType);

    //    @Query("SELECT b FROM Book b WHERE b.title.titleId = :titleId " +
//            "AND b.coverType = :coverType " +
//            "AND NOT EXISTS (SELECT l.book FROM Loan l WHERE l.book = b)")
//    List<Book> findAvailableBooksByTitleIdAndCoverType(@Param("titleId") int titleId,
//                                                       @Param("coverType") CoverType coverType);
//    @Query("SELECT b FROM Book b WHERE b.title.titleId = :titleId " +
//            "AND b.coverType = :coverType " +
//            "AND NOT EXISTS (SELECT l.book FROM Loan l WHERE l.book = b)")
//    List<Book> findAvailableBooksByTitleIdAndCoverType(@Param("titleId") int titleId,
//                                                       @Param("coverType") CoverType coverType);

    @Query("SELECT (SUM(softCoverQuantity) + SUM(hardCoverQuantity)) FROM Title t")
    Integer getAmountOfAllBooks();
}
