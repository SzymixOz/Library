package pl.edu.agh.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.agh.model.Book;

public interface BookRepository extends JpaRepository<Book, Integer> {
}
