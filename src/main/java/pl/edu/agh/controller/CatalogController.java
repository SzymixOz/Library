package pl.edu.agh.controller;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import pl.edu.agh.model.books.Book;
import pl.edu.agh.model.books.CoverType;
import pl.edu.agh.model.books.Title;
import pl.edu.agh.model.users.AccountType;
import pl.edu.agh.model.users.User;
import pl.edu.agh.service.AdminService;
import pl.edu.agh.service.BookService;

import java.io.IOException;
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
