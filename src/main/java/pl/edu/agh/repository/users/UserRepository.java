package pl.edu.agh.repository.users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import pl.edu.agh.model.users.User;

@NoRepositoryBean
public interface UserRepository<T extends User> extends JpaRepository<T, Integer> {
    T findByEmail(String email);
}
