package pl.edu.agh.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.agh.model.books.*;
import pl.edu.agh.model.books.CoverType;
import pl.edu.agh.model.books.Rating;
import pl.edu.agh.model.books.Title;
import pl.edu.agh.model.extra.HistoricalLoanDetails;
import pl.edu.agh.model.extra.LoanDetails;
import pl.edu.agh.model.extra.TitleDetails;
import pl.edu.agh.model.loans.HistoricalLoan;
import pl.edu.agh.model.loans.Loan;
import pl.edu.agh.model.users.Member;
import pl.edu.agh.model.users.User;
import pl.edu.agh.repository.books.RatingRepository;
import pl.edu.agh.repository.books.TitleRepository;
import pl.edu.agh.repository.loans.HistoricalLoanRepository;
import pl.edu.agh.repository.loans.LoanRepository;
import pl.edu.agh.repository.users.MemberRepository;
import pl.edu.agh.session.BooksSession;
import pl.edu.agh.validator.BookValidator;

import java.sql.Blob;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BookService {
    private final TitleRepository titleRepository;
    private final LoanRepository loanRepository;
    private final MemberRepository memberRepository;
    private final HistoricalLoanRepository historicalLoanRepository;
    private final RatingRepository ratingRepository;


    @Autowired
    public BookService(TitleRepository titleRepository, LoanRepository loanRepository, MemberRepository memberRepository, HistoricalLoanRepository historicalLoanRepository, RatingRepository ratingRepository) {
        this.titleRepository = titleRepository;
        this.loanRepository = loanRepository;
        this.memberRepository = memberRepository;
        this.historicalLoanRepository = historicalLoanRepository;
        this.ratingRepository = ratingRepository;
    }

    public LoanRepository getLoanRepository() {
        return loanRepository;
    }

    public String addBook(String title, String author, String isbn, BookCategory category, Blob image, String softCoverQuantity, String hardCoverQuantity) {

        if (!BookValidator.isTitleValid(title)) {
            return "Niepoprawny tytul";
        }

        if (!BookValidator.isAuthorValid(author)) {
            return "Niepoprawny autor";
        }

        if (!BookValidator.isIsbnValid(isbn)) {
            return "Niepoprawny ISBN";
        }

        if (category == null) {
            return "Niepoprawna kategoria";
        }

        int softCoverQuantityInt;
        int hardCoverQuantityInt;
        long isbnLong = Long.parseLong(isbn);

        try {
            softCoverQuantityInt = Integer.parseInt(softCoverQuantity);
            hardCoverQuantityInt = Integer.parseInt(hardCoverQuantity);
        } catch (Exception e) {
            return "Niepoprawna ilosc ksiazek";
        }

        Title title_db = new Title(isbnLong, title, author, category, image, softCoverQuantityInt, hardCoverQuantityInt);
        try {
            titleRepository.save(title_db);
        } catch (Exception e) {
            return "Ksiazka o podanym ISBN juz istnieje";
        }

        return "Ksiazka zostala dodana";
    }

    public List<Title> getAllTitles() {
        return titleRepository.findAll();
    }

    public Integer getNumberOfAvailableBooks(Title title, CoverType coverType) {
        return (coverType.equals(CoverType.SOFT) ? title.getSoftCoverQuantity() : title.getHardCoverQuantity())
                - titleRepository.countBorrowedBooksByTitleIdAndCover(title.getTitleId(), coverType);
    }

    @Transactional
    public Loan reserveBook(int titleId, CoverType coverType, User user) {
        Title titleToBorrow = titleRepository.findById(titleId).orElseThrow(RuntimeException::new);

        Date currentDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.MONTH, 1);
        Date nextMonthDate = calendar.getTime();

        Member member = memberRepository.findById(user.getUserId()).orElseThrow(RuntimeException::new);

        Loan loan = new Loan(currentDate, nextMonthDate, member, titleToBorrow, coverType);

        return loanRepository.saveAndFlush(loan);
    }

    public List<Rating> findRatingsByTitleId(int title_id) {
        return ratingRepository.findRatingsByTitleId(title_id);
    }

    public boolean hasUserBorrowedBook(Title title, int userId) {
        return historicalLoanRepository.hasUserBorrowedBook(title, userId);
    }

    @Transactional
    public Rating rateBook(Title title, User user, int rate, String comment) {
        if(rate < 0 || rate > 5) return null;
        if(!hasUserBorrowedBook(title, user.getUserId())) return null;

        Title titleToRate = titleRepository.findById(title.getTitleId()).orElseThrow(RuntimeException::new);
        Member member = memberRepository.findById(user.getUserId()).orElseThrow(RuntimeException::new);
        Rating rating = new Rating(titleToRate, member, rate, comment);

        return ratingRepository.saveAndFlush(rating);
    }
    public List<LoanDetails> getAllUserLoans(User user) {
        return loanRepository.findAllLoansByUserId(user.getUserId());
    }

    public List<HistoricalLoanDetails> getAllUserHistoricalLoans(User user) {
        return historicalLoanRepository.findAllHistoricalLoansByUserId(user.getUserId());
    }

    @Transactional
    public HistoricalLoan returnBook(int loanId) {
        Loan loan = loanRepository.findById(loanId).orElseThrow(RuntimeException::new);

        HistoricalLoan historicalLoan = new HistoricalLoan(loan.getStartLoanDate(), loan.getEndLoanDate(), new Date(), loan.getMember(), loan.getTitle(), loan.getCoverType());

        HistoricalLoan savedHistoricalLoan = historicalLoanRepository.saveAndFlush(historicalLoan);
        loanRepository.deleteByLoanId(loan.getLoanId());

        return savedHistoricalLoan;
    }
    @Transactional
    public List<Integer> getTitlesIdSortedByRankings() {
        List<Title> titles = titleRepository.findAll();

        return titles.stream()
                .filter(t -> !t.getRatings().isEmpty())
                .sorted(Comparator.comparingDouble((Title t) -> t.getRatings().stream().mapToDouble(Rating::getRate).average().orElse(0.0))
                        .reversed())
                .map(Title::getTitleId)
                .toList();
    }
    @Transactional
    public List<Integer> getTitlesidSortedByPopularity() {
        List<Title> titles = titleRepository.findAll();

        return titles.stream()
                .sorted(Comparator.comparingInt((Title t) -> (t.getLoans().size() + t.getHistoricalLoans().size())).reversed())
                .map(Title::getTitleId)
                .toList();
    }

    @Transactional
    public List<Title> getBestBooksToRead(Set<Title> allTitles) {
        Set<Integer> allTitlesIds = allTitles.stream().map(Title::getTitleId).collect(Collectors.toSet());
        Set<Member> allMembers = titleRepository.findMembersByTitle(allTitles);

        Set<HistoricalLoan> historicalLoans = historicalLoanRepository.findAllHistoricalLoansByMembers(allMembers);

        List<Title> titles = historicalLoanRepository.findBestPropositions(allMembers, historicalLoans);

        return titles.stream().filter(t -> !allTitlesIds.contains(t.getTitleId())).collect(Collectors.toList());
    }

    public Double getTitleAverageRating(int titleId) {
        Title title = getTitleById(titleId);
        return title.getRatings().stream().mapToDouble(Rating::getRate).average().orElse(0.0);
    }

    public Title getTitleById(int id) {
        return titleRepository.findById(id).orElse(null);
    }
}
