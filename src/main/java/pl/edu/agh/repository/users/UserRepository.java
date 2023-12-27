package pl.edu.agh.repository.users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.agh.model.users.User;

//@NoRepositoryBean
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
}