package pl.edu.agh.controller;

import javafx.beans.binding.Bindings;
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
import pl.edu.agh.model.books.Title;
import pl.edu.agh.service.BookService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@Component
public class CatalogController {

    @FXML
    public TableView<Title> booksTable;
    @FXML
    public TableColumn<Title, String> AuthorColumn;
    @FXML
    public TableColumn<Title, String> TitleColumn;
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
    private Stage primaryStage;
    private ApplicationContext context;
    private final BookService bookService;
    private List<Title> titleList;

    @Autowired
    public CatalogController(BookService bookService) {
        this.bookService = bookService;
        titleList = bookService.getAllTitles();
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
        List<Title> titleList = bookService.getAllTitles();
        booksTable.getItems().addAll(titleList);
        AuthorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        TitleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));

        selectButton.disableProperty().bind(Bindings.isEmpty(booksTable.getSelectionModel().getSelectedItems()));

        initRadioButtons();
    }

    private void initRadioButtons() {
        ToggleGroup toggleGroup = new ToggleGroup();
        tableRadioButton.setToggleGroup(toggleGroup);
        cardsRadioButton.setToggleGroup(toggleGroup);
        tableRadioButton.setSelected(true);
        cardsContainer.setVisible(false);
        cardsContainer.setManaged(false);
        for (Title book : titleList) {
            AnchorPane card = createBookCard(book);
            bookCards.getChildren().add(card);
        }

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
        AnchorPane card = new AnchorPane();
        card.setPrefSize(180, 280);
        card.setStyle("-fx-padding: 5px;-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.4), 10, 0.5, 0.0, 0.0);-fx-background-color: white;");

        ImageView imageView = null;
        try {
            imageView = new ImageView(new Image(book.getImage().getBinaryStream()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        imageView.setFitWidth(180);
        imageView.setFitHeight(230);
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
    public void handleBackClickAction() {
        MainController mainController = context.getBean(MainController.class);
        mainController.setPrimaryStage(primaryStage);
        mainController.loadView();
    }

    public void handleSelectAction() {
        Title title = booksTable.getSelectionModel().getSelectedItem();

        SingleBookController singleBookController = context.getBean(SingleBookController.class);
        singleBookController.setTitle(title);
        singleBookController.setPrimaryStage(primaryStage);
        singleBookController.loadView();
    }
    public void handleSearchAction() {
        booksTable.getItems().clear();
        String prefix = searchTextField.getText();
        booksTable.getItems().addAll(titleList.stream().filter(t -> t.getTitle().startsWith(prefix)).toList());
    }
}
