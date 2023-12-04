package pl.edu.agh.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.agh.model.Loan;

public interface LoanRepository extends JpaRepository<Loan, Integer> {
}
