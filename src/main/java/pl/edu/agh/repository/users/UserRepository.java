package pl.edu.agh.repository.users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.agh.model.users.User;

@Repository
public interface UserRepository<T extends User> extends JpaRepository<T, Integer> {

}