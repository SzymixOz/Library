package pl.edu.agh.repository;

import org.springframework.stereotype.Repository;
import pl.edu.agh.model.Admin;

@Repository
public interface AdminRepository extends UserRepository<Admin> {
}
