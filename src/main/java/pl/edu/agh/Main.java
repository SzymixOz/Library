package pl.edu.agh;

import javafx.application.Application;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.logging.Logger;

@SpringBootApplication
public class Main {

    private static final Logger log = Logger.getLogger(Main.class.toString());

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
        log.info("Hello world");
        Application.launch(App.class);
    }
}
