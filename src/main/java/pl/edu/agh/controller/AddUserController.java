package pl.edu.agh.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import pl.edu.agh.validator.UserValidator;

import java.io.IOException;

public class AddUserController {

    private Stage primaryStage;

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField mailField;

    // DON'T REMOVE THIS
    public AddUserController() {
    }

    public AddUserController(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void initRootLayout() {
        try {
            this.primaryStage.setTitle("Library");

            // load layout from FXML file
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(AddUserController.class
                    .getResource("../../../../view/AddUser.fxml"));
            BorderPane rootLayout = loader.load();


            // add layout to a scene and show them all
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void addUser() {
        if (!isUserValid()) {
            return;
        }
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String email = mailField.getText();


        System.out.println(firstName + lastName + email);
        // HERE SAVE IN DATABASE

        firstNameField.setText("");
        lastNameField.setText("");
        mailField.setText("");
    }

    private boolean isUserValid() {
        boolean valid = UserValidator.isFirstNameValid(firstNameField.getText());
        if (!valid) {
            showError("Niepoprawne imie");
            return false;
        }
        valid = UserValidator.isLastNameValid(lastNameField.getText());
        if (!valid) {
            showError("Niepoprawne nazwisko");
            return false;
        }
        valid = UserValidator.isMailValid(mailField.getText());
        if (!valid) {
            showError("Niepoprawny mail");
            return false;
        }
        return true;
    }


    private void showError(String label){
        //    TO IMPLEMENT
        System.out.println("BLAD " + label);
    }
}
