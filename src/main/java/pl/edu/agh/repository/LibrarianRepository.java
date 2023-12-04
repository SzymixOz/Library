package pl.edu.agh.repository;

import org.springframework.stereotype.Repository;
import pl.edu.agh.model.Librarian;

@Repository
public interface LibrarianRepository extends UserRepository<Librarian> {
}
