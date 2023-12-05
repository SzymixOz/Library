package pl.edu.agh.repository.users;

import org.springframework.stereotype.Repository;
import pl.edu.agh.model.users.Admin;

@Repository
public interface AdminRepository extends UserRepository<Admin> {
}
