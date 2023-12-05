package pl.edu.agh.repository.books;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.agh.model.books.Title;

public interface TitleRepository extends JpaRepository<Title, Integer> {
}
