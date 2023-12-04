package pl.edu.agh.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.agh.model.HistoricalLoan;

public interface HistoricalLoanRepository extends JpaRepository<HistoricalLoan, Integer> {
}
