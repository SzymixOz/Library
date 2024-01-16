package pl.edu.agh.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.agh.model.response.IBookResponse;
import pl.edu.agh.repository.books.TitleRepository;
import pl.edu.agh.repository.loans.HistoricalLoanRepository;
import pl.edu.agh.repository.loans.LoanRepository;

import java.util.List;

@Service
public class LibrarianStatsService {
    private final LoanRepository loanRepository;
    private final HistoricalLoanRepository historicalLoanRepository;
    private final TitleRepository titleRepository;

    @Autowired
    public LibrarianStatsService(LoanRepository loanRepository, HistoricalLoanRepository historicalLoanRepository, TitleRepository titleRepository) {
        this.loanRepository = loanRepository;
        this.historicalLoanRepository = historicalLoanRepository;
        this.titleRepository = titleRepository;
    }

    public List<IBookResponse> findTheMostFrequentlyCurrentlyBorrowedBooks() {
        return loanRepository.findTheMostFrequentlyBorrowedBooks();
    }

    public List<IBookResponse> findTheMostFrequentlyHistoricalBorrowedBooks() {
        return historicalLoanRepository.findTheMostFrequentlyBorrowedBooks();
    }

    public Integer getAmountOfTitles() {
        return titleRepository.getAmountOfTitles();
    }
    public Integer getAmountOfAllBooks() {
        return titleRepository.getAmountOfAllBooks();
    }
    public Integer getAmountOfCurrentlyBorrowedBooks() {
        return loanRepository.getAmountOfCurrentlyBorrowedBooks();
    }
}
