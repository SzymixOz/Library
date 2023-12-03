package pl.edu.agh.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
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
    @FXML
    private Label resultLabel;

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

    @FXML
    public void handleAddUserAction() {
        resultLabel.setText(null);
        if (!isUserValid()) {
            return;
        }
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String email = mailField.getText();


        System.out.println(firstName + lastName + email);
        // HERE SAVE IN DATABASE
//        TO DO:
//        Utworzyc commanda/servis ktory bedzie ogarnial dodanie usera
//        przekleic z controllera validacje do commanda/servisu
//        dodatkowo dodac jeszcze validacje sprawdzajaca czy dany email jest juz w bazie danych
//        dobrze by bylo jakby command do controllera zwracal jakiegos enuma
//        i na podstawie tego enuma by byly wyswietlane odpowiednie komunikaty:
//        "Uzytkownik zostal dodany" / "niepoprawne imie" ...

        showResult("Uzytkownik zostal dodany", false);
    }

    private boolean isUserValid() {
        boolean valid = UserValidator.isFirstNameValid(firstNameField.getText());
        if (!valid) {
            showResult("Niepoprawne imie", true);
            return false;
        }
        valid = UserValidator.isLastNameValid(lastNameField.getText());
        if (!valid) {
            showResult("Niepoprawne nazwisko", true);
            return false;
        }
        valid = UserValidator.isMailValid(mailField.getText());
        if (!valid) {
            showResult("Niepoprawny mail", true);
            return false;
        }
        return true;
    }

    private void clearInputFields() {
        firstNameField.setText("");
        lastNameField.setText("");
        mailField.setText("");
    }


    private void showResult(String labelText, boolean isError){
        if (isError) {
            resultLabel.setStyle("-fx-text-fill: red;");
        } else {
            resultLabel.setStyle("-fx-text-fill: green;");

            // clear fields if succesfully added
            clearInputFields();
        }
        resultLabel.setText(labelText);
    }
}
