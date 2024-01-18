package pl.edu.agh.controller;

import javafx.beans.binding.Bindings;
import javafx.collections.ListChangeListener.Change;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
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
import pl.edu.agh.model.books.Rating;
import pl.edu.agh.model.books.Title;
import pl.edu.agh.service.BookService;
import pl.edu.agh.session.BooksSession;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Component
public class CatalogController {

    @FXML
    public TableView<Title> booksTable;
    @FXML
    public TableColumn<Title, String> AuthorColumn;
    @FXML
    public TableColumn<Title, String> TitleColumn;
    @FXML
    public TableColumn<Title, BookCategory> CategoryColumn;
    @FXML
    private Button selectButton;
    @FXML
    private Button searchButton;
    @FXML
    private TextField searchTextField;
    @FXML
    private RadioButton tableRadioButton;
    @FXML
    private RadioButton cardsRadioButton;
    @FXML
    private ScrollPane cardsContainer;
    @FXML
    private FlowPane bookCards;
    @FXML
    private ChoiceBox<BookCategory> categoryChoiceBox;
    @FXML
    private ChoiceBox<Integer> ratingChoiceBox;
    private Stage primaryStage;
    private ApplicationContext context;
    private final BookService bookService;
    private final BooksSession booksSession;
    private FilteredList<Title> filteredTitles;
    private SortedList<Title> sortedTitles;
    @Autowired
    public CatalogController(BookService bookService, BooksSession booksSession) {
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
            loader.setLocation(CatalogController.class.getResource("/view/CatalogView.fxml"));
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
        booksTable.getItems().clear();
        booksTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        AuthorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        TitleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        CategoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));

        ObservableList<Title> initialTitlesList = booksSession.getTitles();
        filteredTitles = new FilteredList<>(initialTitlesList, p -> true);
        Predicate<Title> categoryPredicate = title -> {
            BookCategory category = categoryChoiceBox.getValue();
            return category == null || title.getCategory().equals(category);
        };
        Predicate<Title> searchPredicate = title -> {
            if(searchTextField.getText() == null) return true;
            String filter = searchTextField.getText().toLowerCase();
            return filter.isEmpty() || title.getTitle().toLowerCase().contains(filter);
        };
        Predicate<Title> ratingPredicate = title -> {
            Integer rating = ratingChoiceBox.getValue();
            return rating == null || bookService.getTitleAverageRating(title.getTitleId()) >= rating;
        };
        Predicate<Title> combinedPredicate = searchPredicate.and(categoryPredicate).and(ratingPredicate);
        searchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredTitles.setPredicate(null);
            filteredTitles.setPredicate(combinedPredicate);
        });
        categoryChoiceBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            filteredTitles.setPredicate(null);
            filteredTitles.setPredicate(combinedPredicate);
        });
        ratingChoiceBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            filteredTitles.setPredicate(null);
            filteredTitles.setPredicate(combinedPredicate);
        });
        sortedTitles = new SortedList<>(filteredTitles);
        sortedTitles.comparatorProperty().bind(booksTable.comparatorProperty());
        sortedTitles.addListener((Change<? extends Title> change) -> refreshCards());

        booksTable.setItems(sortedTitles);

        List<Integer> titlesIdWithBestRankings = booksSession.getTitlesIdWithBestRankings();
        List<Integer> mostPopularTitlesId = booksSession.getMostPopularTitlesId();
        List<Integer> leastPopularTitlesId = booksSession.getLeastPopularTitlesId();

        booksTable.setRowFactory(tv -> new TableRow<Title>() {
            @Override
            protected void updateItem(Title title, boolean empty) {
                super.updateItem(title, empty);
                String styleString = "";
                if (empty || title == null) {
                    setStyle("");
                } else {
                    int id = title.getTitleId();
                    if (titlesIdWithBestRankings.contains(id)){
                        styleString += "-fx-border-color: #9efc95; -fx-border-width: 2px; -fx-padding: 0px; ";
                    } else {
                        styleString += "-fx-padding:2px; ";
                    }
                    if(mostPopularTitlesId.contains(id)) {
                        styleString += "-fx-background-color: #65fa02; ";
                    } else if(leastPopularTitlesId.contains(id)) {
                        styleString += "-fx-background-color: #ed6674; ";
                    }
                    setStyle(styleString);
                }
            }
        });

        categoryChoiceBox.getItems().addAll(BookCategory.values());
        ratingChoiceBox.getItems().addAll(List.of(1, 2, 3, 4, 5));
        selectButton.disableProperty().bind(Bindings.isEmpty(booksTable.getSelectionModel().getSelectedItems()));
        initRadioButtons();
        refreshCards();
    }

    private void refreshCards() {
        bookCards.getChildren().clear();
        for (Title book : sortedTitles) {
            AnchorPane card = createBookCard(book);
            bookCards.getChildren().add(card);
        }
    }

    private void initRadioButtons() {
        ToggleGroup toggleGroup = new ToggleGroup();
        tableRadioButton.setToggleGroup(toggleGroup);
        cardsRadioButton.setToggleGroup(toggleGroup);
        tableRadioButton.setSelected(true);
        cardsContainer.setVisible(false);
        cardsContainer.setManaged(false);
        tableRadioButton.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                booksTable.setVisible(true);
                booksTable.setManaged(true);
                cardsContainer.setVisible(false);
                cardsContainer.setManaged(false);
            }
        });

        cardsRadioButton.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                booksTable.setVisible(false);
                booksTable.setManaged(false);
                cardsContainer.setVisible(true);
                cardsContainer.setManaged(true);
            }
        });
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


    public void handleBackClickAction() {
        MainController mainController = context.getBean(MainController.class);
        mainController.setPrimaryStage(primaryStage);
        mainController.loadView();
    }

    public void handleSelectAction() {
        handleDetailsAction(booksTable.getSelectionModel().getSelectedItem());
    }

    public void handleResetFiltersAction() {
        searchTextField.setText(null);
        ratingChoiceBox.setValue(null);
        categoryChoiceBox.setValue(null);
    }
}
