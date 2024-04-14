package concertpro.client.gui;

import concertpro.model.User;
import concertpro.services.ChatException;
import concertpro.services.IChatService;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;

public class LogInController {
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Button logInButton;

    private IChatService server;
    private DashboardController dashboardController;
    Parent mainChatParent;

    public void setServer(IChatService server) {
        this.server = server;
    }

    public void setDashboardController(DashboardController dashboardController) {
        this.dashboardController = dashboardController;
    }

    public void setParent(Parent p) {
        mainChatParent = p;
    }

    public void handleLogIn(ActionEvent actionEvent) throws IOException {
        String username = usernameField.getText();
        String password = passwordField.getText();
        System.out.println("Username: " + username + " Password: " + password);
        User crtUser = new User(username, password, false);

        try {
            server.login(crtUser, dashboardController);

            FXMLLoader dashboardLoader = new FXMLLoader(
                getClass().getClassLoader().getResource("dashboard-view.fxml"));
            Parent croot = dashboardLoader.load();
            this.dashboardController = dashboardLoader.getController();
            this.setDashboardController(this.dashboardController);
            this.setParent(croot);

            Stage stage = new Stage();
            stage.setTitle("ConcertPro");
            stage.setScene(new Scene(mainChatParent));
            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    System.exit(0);
                }
            });

            stage.show();


            ((Node)(actionEvent.getSource())).getScene().getWindow().hide();

            this.dashboardController.setServer(this.server);
            this.dashboardController.spectaclesUpdated();
            this.dashboardController.setUser(crtUser);
        } catch (ChatException e) {
            MessageAlert.showErrorMessage(null, e.getMessage());
        }
    }
}
