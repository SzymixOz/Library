package pl.edu.agh.repository.books;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.edu.agh.model.books.Rating;

import java.util.List;

public interface RatingRepository extends JpaRepository<Rating, Integer> {
    @Query("SELECT r FROM Rating r WHERE r.title.titleId = :titleId")
    List<Rating> findRatingsByTitleId(@Param("titleId") int titleId);
}
