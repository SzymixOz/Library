package pl.edu.agh.session;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.stereotype.Component;
import pl.edu.agh.model.books.Title;
import pl.edu.agh.service.BookService;

import java.util.List;

@Component
public class BooksSession {
    private ObservableList<Title> titles;
    private List<Integer> mostPopularTitlesId;
    private List<Integer> leastPopularTitlesId;
    private List<Integer> titlesIdWithBestRankings;
    private final BookService bookService;

    public BooksSession(BookService bookService) {
        this.bookService = bookService;
        initialize();
    }

    private void initialize() {
        List<Title> titlesFromDb = bookService.getAllTitles();
        titles = FXCollections.observableList(titlesFromDb);

        List<Integer> titlesIdSortedByRankings = bookService.getTitlesIdSortedByRankings();
        titlesIdWithBestRankings = titlesIdSortedByRankings.subList(0, Math.min(titlesIdSortedByRankings.size(), 3));

        List<Integer> titlesIdSortedByPopularity = bookService.getTitlesidSortedByPopularity();
        mostPopularTitlesId = titlesIdSortedByPopularity.subList(0, Math.min(titlesIdSortedByPopularity.size(), 3));
        leastPopularTitlesId = titlesIdSortedByPopularity.subList(Math.max(0, titlesIdSortedByPopularity.size() - 3), titlesIdSortedByPopularity.size());
    }

    public ObservableList<Title> getTitles() {
        return titles;
    }

    public List<Integer> getMostPopularTitlesId() {
        return mostPopularTitlesId;
    }

    public List<Integer> getLeastPopularTitlesId() {
        return leastPopularTitlesId;
    }

    public List<Integer> getTitlesIdWithBestRankings() {
        return titlesIdWithBestRankings;
    }

    public void resetTitles() {
        List<Title> titlesFromDb = bookService.getAllTitles();
        titles = FXCollections.observableList(titlesFromDb);
    }

    public void resetBestRatings() {
        List<Integer> titlesIdSortedByRankings = bookService.getTitlesIdSortedByRankings();
        titlesIdWithBestRankings = titlesIdSortedByRankings.subList(0, Math.min(titlesIdSortedByRankings.size(), 3));
    }

    public void resetPopularity() {
        List<Integer> titlesIdSortedByPopularity = bookService.getTitlesidSortedByPopularity();
        mostPopularTitlesId = titlesIdSortedByPopularity.subList(0, Math.min(titlesIdSortedByPopularity.size(), 3));
        leastPopularTitlesId = titlesIdSortedByPopularity.subList(Math.max(0, titlesIdSortedByPopularity.size() - 3), titlesIdSortedByPopularity.size());
    }
}
