package ro.mpp2024.concertpro.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ro.mpp2024.concertpro.MainApplication;
import ro.mpp2024.concertpro.dao.exception.ValidationException;
import ro.mpp2024.concertpro.service.UserService;
import ro.mpp2024.concertpro.utils.ControllerGetter;
import ro.mpp2024.concertpro.utils.MessageAlert;

import java.io.IOException;

public class LogInController {
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Button logInButton;

    private final UserService userService;
    private static final Logger logger = LogManager.getLogger();

    public LogInController(UserService userService) {
        logger.info("Initializing LogInController.");
        
        this.usernameField = new TextField();
        this.passwordField = new PasswordField();
        this.userService = userService;
    }

    public void handleLogIn() throws IOException {
        try {
            logger.traceEntry("Initializing login task");
            
            String username = this.usernameField.getText();
            String password = this.passwordField.getText();
            if(username.isBlank() || password.isBlank()) {
                throw new ValidationException("The text inputs should not be blank!!");
            }

            this.userService.loginUser(username, password);

            FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("dashboard-view.fxml"));
            DashboardController dashboardController = ControllerGetter.getDashboardController();
            loader.setController(dashboardController);
            Parent root = loader.load();
            Stage window = (Stage) this.logInButton.getScene().getWindow();
            window.setScene(new Scene(root, 950, 500));
        }
        catch (ValidationException exception) {
            logger.error(exception.getMessage());
            MessageAlert.showErrorMessage(null, exception.getMessage());
        }

    }
}
