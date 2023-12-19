package pl.edu.agh.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import pl.edu.agh.service.MemberService;
import pl.edu.agh.stage.StageReadyEvent;

import java.io.IOException;


@Component
public class AddUserController implements ApplicationListener<StageReadyEvent> {

    private Stage primaryStage;

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField mailField;
    @FXML
    private Label resultLabel;


    private MemberService memberService;
    private ApplicationContext context;

    @Autowired
    public void setMemberService(MemberService memberService) {
        this.memberService = memberService;
    }

    @Autowired
    public void setContext(ApplicationContext context) {
        this.context = context;
    }

    @Override
    public void onApplicationEvent(StageReadyEvent event) {
        this.primaryStage = event.getStage();
        initRootLayout();
    }

    public void initRootLayout() {
        try {
            this.primaryStage.setTitle("Library");
            // load layout from FXML file
            FXMLLoader loader = new FXMLLoader();
            loader.setControllerFactory(aClass -> context.getBean(aClass));
            loader.setLocation(AddUserController.class
                    .getResource("/view/AddUserView.fxml"));
            BorderPane rootLayout = loader.load();


            // add layout to a scene and show them all
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleAddUserAction() {
        resultLabel.setText(null);

        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String email = mailField.getText();

        String result = this.memberService.addUser(firstName, lastName, email);
        showResult(result);
    }

    private void showResult(String labelText) {
        if ("Uzytkownik zostal dodany".equals(labelText)) {
            resultLabel.setStyle("-fx-text-fill: green;");

            // clear fields if succesfully added
            clearInputFields();
        } else {
            resultLabel.setStyle("-fx-text-fill: red;");
        }
        resultLabel.setText(labelText);
    }

    private void clearInputFields() {
        firstNameField.setText("");
        lastNameField.setText("");
        mailField.setText("");
    }
}
