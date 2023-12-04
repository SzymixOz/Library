package pl.edu.agh.repository.users;

import org.springframework.stereotype.Repository;
import pl.edu.agh.model.users.Librarian;

@Repository
public interface LibrarianRepository extends UserRepository<Librarian> {
}
