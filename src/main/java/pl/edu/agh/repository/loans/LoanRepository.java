package pl.edu.agh.repository.loans;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.agh.model.loans.Loan;

public interface LoanRepository extends JpaRepository<Loan, Integer> {
}
