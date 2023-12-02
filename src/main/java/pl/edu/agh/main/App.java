package pl.edu.agh.main;

import javafx.application.Application;
import javafx.stage.Stage;
import pl.edu.agh.controller.AddUserController;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) {

        AddUserController addUserController = new AddUserController(primaryStage);
        addUserController.initRootLayout();

    }
}
