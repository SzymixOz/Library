package pl.edu.agh.repository.loans;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.agh.model.loans.HistoricalLoan;

public interface HistoricalLoanRepository extends JpaRepository<HistoricalLoan, Integer> {
}
