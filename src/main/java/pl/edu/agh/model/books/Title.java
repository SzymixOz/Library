package pl.edu.agh.model.books;


import jakarta.persistence.*;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.agh.model.loans.HistoricalLoan;
import pl.edu.agh.model.loans.Loan;

import java.sql.Blob;
import java.util.List;

@Entity
public class Title {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int titleId;
    private long isbn;
    private String title;
    private String author;
    private BookCategory category;
    private int softCoverQuantity;
    private int hardCoverQuantity;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "title")
    private List<Loan> loans;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "title")
    private List<HistoricalLoan> historicalLoans;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "title")
    private List<Rating> ratings;

    @Lob
    private Blob image;
    public Title(Long isbn, String title, String author, BookCategory category, Blob image, int softCoverQuantity, int hardCoverQuantity) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.category = category;
        this.image = image;
        this.softCoverQuantity = softCoverQuantity;
        this.hardCoverQuantity = hardCoverQuantity;
    }

    public int getTitleId() {
        return titleId;
    }

    public void setTitleId(int titleId) {
        this.titleId = titleId;
    }

    public long getIsbn() {
        return isbn;
    }

    public void setIsbn(long isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public BookCategory getCategory() {
        return category;
    }

    public void setCategory(BookCategory category) {
        this.category = category;
    }

    public Blob getImage() {
        return image;
    }

    public void setImage(Blob image) {
        this.image = image;
    }

    public Title() {

    }
    public int getSoftCoverQuantity() {
        return softCoverQuantity;
    }

    public void setSoftCoverQuantity(int softCoverQuantity) {
        this.softCoverQuantity = softCoverQuantity;
    }

    public int getHardCoverQuantity() {
        return hardCoverQuantity;
    }

    public void setHardCoverQuantity(int hardCoverQuantity) {
        this.hardCoverQuantity = hardCoverQuantity;
    }

    public List<Loan> getLoans() {
        return loans;
    }

    public void setLoans(List<Loan> loans) {
        this.loans = loans;
    }

    public List<HistoricalLoan> getHistoricalLoans() {
        return historicalLoans;
    }

    public void setHistoricalLoans(List<HistoricalLoan> historicalLoans) {
        this.historicalLoans = historicalLoans;
    }

    public List<Rating> getRatings() {
        return ratings;
    }

    public void setRatings(List<Rating> ratings) {
        this.ratings = ratings;
    }
}
