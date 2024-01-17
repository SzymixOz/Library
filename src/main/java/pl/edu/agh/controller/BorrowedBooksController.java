package pl.edu.agh.controller;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import pl.edu.agh.model.books.BookCategory;
import pl.edu.agh.model.books.Title;
import pl.edu.agh.model.extra.HistoricalLoanDetails;
import pl.edu.agh.model.extra.LoanDetails;
import pl.edu.agh.model.loans.HistoricalLoan;
import pl.edu.agh.model.users.Member;
import pl.edu.agh.model.users.User;
import pl.edu.agh.service.BookService;
import pl.edu.agh.session.BooksSession;
import pl.edu.agh.session.UserSession;

import java.io.IOException;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class BorrowedBooksController {
    @FXML
    public TextField amountCurrentLoansField;
    @FXML
    public TextField amountHistoricalLoansField;
    @FXML
    public TextField mostPopularCategory;
    @FXML
    public TextField bestMonth;
    @FXML
    public TableView<LoanDetails> borrowedBooksTable;
    @FXML
    public TableColumn<LoanDetails, String> AuthorColumn;
    @FXML
    public TableColumn<LoanDetails, String> TitleColumn;
    @FXML
    public TableColumn<LoanDetails, Date> StartDateColumn;
    @FXML
    public TableColumn<LoanDetails, Date> EndDateColumn;
    @FXML
    public TableView<HistoricalLoanDetails> historicalBorrowsTable;
    @FXML
    public TableColumn<HistoricalLoanDetails, String> HistoricalAuthorColumn;
    @FXML
    public TableColumn<HistoricalLoanDetails, String> HistoricalTitleColumn;
    @FXML
    public TableColumn<HistoricalLoanDetails, Date> HistoricalStartDateColumn;
    @FXML
    public TableColumn<HistoricalLoanDetails, Date> HistoricalEndDateColumn;
    @FXML
    public TableColumn<HistoricalLoanDetails, Date> HistoricalReturnDateColumn;
    @FXML
    private ScrollPane cardsContainer;
    @FXML
    private FlowPane bookCards;
    @FXML
    private Button returnButton;
    private Stage primaryStage;
    private ApplicationContext context;

    private final UserSession session;
    private ObservableList<LoanDetails> loansList;
    private ObservableList<HistoricalLoanDetails> historicalLoansList;
    private final BookService bookService;
    private final BooksSession booksSession;
    @Autowired
    public BorrowedBooksController(UserSession userSession, BookService bookService, BooksSession booksSession) {
        this.session = userSession;
        this.bookService = bookService;
        this.booksSession = booksSession;
    }
    @Autowired
    public void setContext(ApplicationContext context) {
        this.context = context;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void loadView() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setControllerFactory(aClass -> context.getBean(aClass));
            loader.setLocation(CatalogController.class.getResource("/view/BorrowedBooks.fxml"));
            BorderPane mainLayout = loader.load();
            Scene scene = new Scene(mainLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void initialize() {
        User user = session.getUser();

        borrowedBooksTable.getItems().clear();
        borrowedBooksTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        loansList = session.getLoanDetails();
        borrowedBooksTable.getItems().addAll(loansList);
        AuthorColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().title().getAuthor()));
        TitleColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().title().getTitle()));
        StartDateColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().startLoanDate()));
        EndDateColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().endLoanDate()));
        borrowedBooksTable.setPlaceholder(new Label("Nie masz żadnych wypożyczonych książek"));

        returnButton.disableProperty().bind(Bindings.isEmpty(borrowedBooksTable.getSelectionModel().getSelectedItems()));

        historicalBorrowsTable.getItems().clear();
        historicalLoansList = session.getHistoricalLoanDetails();
        historicalBorrowsTable.getItems().addAll(historicalLoansList);
        HistoricalAuthorColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().title().getAuthor()));
        HistoricalTitleColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().title().getTitle()));
        HistoricalStartDateColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().startLoanDate()));
        HistoricalEndDateColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().endLoanDate()));
        HistoricalReturnDateColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().returnLoanDate()));
        historicalBorrowsTable.setPlaceholder(new Label("Nie masz żadnych historycznych wypożyczeń"));

        Integer amountCurrentlyBorrowedBooks = loansList.size();
        amountCurrentLoansField.setText(String.format("Aktualnie wypożyczone książki (%d)", amountCurrentlyBorrowedBooks));

        Integer amountHistoricallyBorrowedBooks = historicalLoansList.size();
        amountHistoricalLoansField.setText(String.format("Historycznie wypożyczone książki (%d)", amountHistoricallyBorrowedBooks));


        Map<BookCategory, Long> categoryCounts = historicalLoansList.stream()
                .collect(Collectors.groupingBy(loanDetail -> loanDetail.title().getCategory(), Collectors.counting()));

        BookCategory mostCommonCategory = categoryCounts.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
        mostPopularCategory.setText(mostCommonCategory == null ? "Brak" : mostCommonCategory.toString());

        Map<YearMonth, Long> monthYearCounts = historicalLoansList.stream()
                .collect(Collectors.groupingBy(
                        loanDetail -> toYearMonth(loanDetail.startLoanDate()),
                        Collectors.counting()
                ));
        YearMonth bestYearMonth = monthYearCounts.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
        bestMonth.setText(bestYearMonth == null ? "Brak" : bestYearMonth.format(DateTimeFormatter.ofPattern("yyyy-MM")));


        Set<Title> myTitles = historicalLoansList.stream().map(HistoricalLoanDetails::title).collect(Collectors.toSet());

        List<Title> titlesToRead = bookService.getBestBooksToRead(myTitles);

        Set<Title> finalSet = titlesToRead.stream().limit(3).collect(Collectors.toSet());

        if(finalSet.size() < 3) {
            int numberOfMostPopularToRecommend = 3 - finalSet.size();
            Set<Integer> finalSetId = finalSet.stream().map(Title::getTitleId).collect(Collectors.toSet());
            Set<Integer> popularTitlesIdToRecommend = booksSession.getMostPopularTitlesId().stream().filter(id -> !finalSetId.contains(id)).limit(numberOfMostPopularToRecommend).collect(Collectors.toSet());
            popularTitlesIdToRecommend.forEach(id -> finalSet.add(bookService.getTitleById(id)));
        }

        bookCards.getChildren().clear();
        for (Title book : finalSet) {
            AnchorPane card = createBookCard(book);
            bookCards.getChildren().add(card);
        }

    }
    private AnchorPane createBookCard(Title book) {
        int id = book.getTitleId();
        List<Integer> titlesIdWithBestRankings = booksSession.getTitlesIdWithBestRankings();
        List<Integer> mostPopularTitlesId = booksSession.getMostPopularTitlesId();
        List<Integer> leastPopularTitlesId = booksSession.getLeastPopularTitlesId();
        String styleString = "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.4), 10, 0.5, 0.0, 0.0);";
        if (titlesIdWithBestRankings.contains(id)){
            styleString += "-fx-border-color: #9efc95; -fx-border-width: 3px; -fx-padding: 2px; ";
        } else {
            styleString += "-fx-padding:5px; ";
        }
        if(mostPopularTitlesId.contains(id)) {
            styleString += "-fx-background-color: #65fa02; ";
        } else if(leastPopularTitlesId.contains(id)) {
            styleString += "-fx-background-color: #ed6674; ";
        } else {
            styleString += "-fx-background-color: white;";
        }

        AnchorPane card = new AnchorPane();
        card.setPrefSize(180, 280);
        card.setStyle(styleString);

        ImageView imageView = null;
        try {
            imageView = new ImageView(new Image(book.getImage().getBinaryStream()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        imageView.setFitWidth(180);
        imageView.setFitHeight(230);
        imageView.setOnMouseClicked(event -> handleDetailsAction(book));

        AnchorPane.setTopAnchor(imageView, 0.0);
        AnchorPane.setLeftAnchor(imageView, 0.0);
        AnchorPane.setRightAnchor(imageView, 0.0);

        Label titleLabel = new Label(book.getTitle());
        titleLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: black;");
        AnchorPane.setTopAnchor(titleLabel, 240.0);
        AnchorPane.setLeftAnchor(titleLabel, 5.0);
        AnchorPane.setRightAnchor(titleLabel, 5.0);

        Label authorLabel = new Label(book.getAuthor());
        authorLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #666666;");
        AnchorPane.setTopAnchor(authorLabel, 260.0);
        AnchorPane.setLeftAnchor(authorLabel, 5.0);
        AnchorPane.setRightAnchor(authorLabel, 5.0);

        card.getChildren().addAll(imageView, titleLabel, authorLabel);
        return card;
    }
    public void handleDetailsAction(Title title) {
        SingleBookController singleBookController = context.getBean(SingleBookController.class);
        singleBookController.setTitle(title);
        singleBookController.setPrimaryStage(primaryStage);
        singleBookController.loadView();
    }
    private YearMonth toYearMonth(Date date) {
        Instant instant = date.toInstant();
        LocalDate localDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();
        return YearMonth.from(localDate);
    }

    public void handleBackClickAction() {
        MainController mainController = context.getBean(MainController.class);
        mainController.setPrimaryStage(primaryStage);
        mainController.loadView();
    }

    public void handleReturnAction() {
        LoanDetails loanDetails = borrowedBooksTable.getSelectionModel().getSelectedItem();
        HistoricalLoan historicalLoan = bookService.returnBook(loanDetails.loanId());
        session.resetLoans();
        if(historicalLoan != null) {
            loansList = session.getLoanDetails();
            historicalLoansList = session.getHistoricalLoanDetails();

            borrowedBooksTable.getItems().clear();
            borrowedBooksTable.getItems().addAll(loansList);

            historicalBorrowsTable.getItems().clear();
            historicalBorrowsTable.getItems().addAll(historicalLoansList);
        }

        Integer amountCurrentlyBorrowedBooks = loansList.size();
        amountCurrentLoansField.setText(String.format("Aktualnie wypożyczone książki (%d)", amountCurrentlyBorrowedBooks));

        Integer amountHistoricallyBorrowedBooks = historicalLoansList.size();
        amountHistoricalLoansField.setText(String.format("Historycznie wypożyczone książki (%d)", amountHistoricallyBorrowedBooks));
    }
}
