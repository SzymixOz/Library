package pl.edu.agh.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.agh.model.books.Book;
import pl.edu.agh.model.books.CoverType;
import pl.edu.agh.model.books.Title;
import pl.edu.agh.repository.books.BookRepository;
import pl.edu.agh.repository.books.TitleRepository;
import pl.edu.agh.validator.BookValidator;

import java.sql.Blob;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final TitleRepository TitleRepository;

    @Autowired
    public BookService(BookRepository bookRepository, TitleRepository TitleRepository) {
        this.bookRepository = bookRepository;
        this.TitleRepository = TitleRepository;
    }

    public String addBook(String title, String author, String isbn, Blob image, String softCoverQuantity, String hardCoverQuantity) {

        if (!BookValidator.isTitleValid(title)) {
            return "Niepoprawny tytul";
        }

        if (!BookValidator.isAuthorValid(author)) {
            return "Niepoprawny autor";
        }

        if (!BookValidator.isIsbnValid(isbn)) {
            return "Niepoprawny ISBN";
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

        Title title_db = new Title(isbnLong, title, author, image);
        try {
            TitleRepository.save(title_db);
        } catch (Exception e) {
            return "Ksiazka o podanym ISBN juz istnieje";
        }

        for (int i = 0; i < softCoverQuantityInt; i++) {
            Book book = new Book(CoverType.SOFT, title_db);
            try {
                bookRepository.save(book);
            } catch (Exception e) {
                return "Nie mozna dodac egzemplarza ksiazki";
            }
        }
        for (int i = 0; i < hardCoverQuantityInt; i++) {
            Book book = new Book(CoverType.HARD, title_db);
            try {
                bookRepository.save(book);
            } catch (Exception e) {
                return "Nie mozna dodac egzemplarza ksiazki";
            }
        }
        return "Ksiazka zostala dodana";
    }

}
