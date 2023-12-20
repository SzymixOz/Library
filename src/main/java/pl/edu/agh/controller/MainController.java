package pl.edu.agh.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class MainController {

    private Stage primaryStage;
    private ApplicationContext context;

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
            loader.setLocation(MainController.class.getResource("/view/mainView.fxml"));
            BorderPane mainLayout = loader.load();

            Scene scene = new Scene(mainLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void handleCatalogClickAction() {
        CatalogController catalogController = context.getBean(CatalogController.class);
        catalogController.setPrimaryStage(primaryStage);
        catalogController.loadView();
    }
    public void handleAddUserClickAction() {
        AddUserController addUserController = context.getBean(AddUserController.class);
        addUserController.setPrimaryStage(primaryStage);
        addUserController.loadView();
    }
    public void handleAddBookClickAction() {
        AddBookController addBookController = context.getBean(AddBookController.class);
        addBookController.setPrimaryStage(primaryStage);
        addBookController.loadView();
    }
    public void handleAdminClickAction() {
        AdminController adminController = context.getBean(AdminController.class);
        adminController.setPrimaryStage(primaryStage);
        adminController.loadView();
    }
    public void handleLogoutClickAction() {
        LoginController loginController = context.getBean(LoginController.class);
        loginController.setPrimaryStage(primaryStage);
        loginController.loadView();
    }

}
