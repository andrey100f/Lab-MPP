package ro.mpp2024.concertpro;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ro.mpp2024.concertpro.controller.LogInController;
import ro.mpp2024.concertpro.utils.ControllerGetter;

import java.io.IOException;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        LogInController logInController = ControllerGetter.getLoginController();

        FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("login-view.fxml"));
        loader.setController(logInController);
        Scene scene = new Scene(loader.load(), 600, 400);
        stage.setScene(scene);
        stage.setTitle("ConcertPro");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
