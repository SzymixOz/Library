package pl.edu.agh.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import pl.edu.agh.service.EmailService;

import java.io.IOException;

@Component
public class SendEmailController {

    @FXML
    private Label resultLabel;

    private Stage primaryStage;
    private ApplicationContext context;
    private EmailService emailService;


    @Autowired
    public SendEmailController(EmailService emailService) {
        this.emailService = emailService;
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
            loader.setLocation(AdminController.class.getResource("/view/SendEmail.fxml"));
            BorderPane mainLayout = loader.load();
            Scene scene = new Scene(mainLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleSendEmailAction() {
        emailService.sendEmails();
        showResult("Maile zostały wysłane.");
    }

    private void showResult(String labelText) {
        resultLabel.setStyle("-fx-text-fill: green;");
        resultLabel.setText(labelText);
    }

    public void handleBackClickAction() {
        MainController mainController = context.getBean(MainController.class);
        mainController.setPrimaryStage(primaryStage);
        mainController.loadView();
    }

}
