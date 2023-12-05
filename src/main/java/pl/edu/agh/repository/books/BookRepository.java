package pl.edu.agh.repository.books;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.agh.model.books.Book;

public interface BookRepository extends JpaRepository<Book, Integer> {
}
