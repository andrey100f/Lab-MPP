package concertpro.client;

import concertpro.client.gui.DashboardController;
import concertpro.client.gui.LogInController;
import concertpro.network.rpc.ChatServicesProxy;
import concertpro.services.IChatService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Properties;

public class StartClient extends Application {

    private static int defaultChatPort = 55555;
    private static String defaultServer = "localhost";

    @Override
    public void start(Stage primaryStage) throws Exception {
        System.out.println("In start");
        Properties clientProps = new Properties();
        try {
            clientProps.load(StartClient.class.getResourceAsStream("/concertproclient.properties"));
            System.out.println("Client properties set. ");
            clientProps.list(System.out);
        } catch (IOException e) {
            System.err.println("Cannot find chatclient.properties " + e);
            return;
        }

        String serverIP = clientProps.getProperty("chat.server.host", defaultServer);
        int serverPort = defaultChatPort;

        try {
            serverPort = Integer.parseInt(clientProps.getProperty("chat.server.port"));
        } catch (NumberFormatException ex) {
            System.err.println("Wrong port number " + ex.getMessage());
            System.out.println("Using default port: " + defaultChatPort);
        }
        System.out.println("Using server IP " + serverIP);
        System.out.println("Using server port " + serverPort);

        IChatService server = new ChatServicesProxy(serverIP, serverPort);



        FXMLLoader loader = new FXMLLoader(
            getClass().getClassLoader().getResource("login-view.fxml"));
        Parent root=loader.load();
        LogInController ctrl =
            loader.<LogInController>getController();
        ctrl.setServer(server);

        FXMLLoader dashboardLoader = new FXMLLoader(
            getClass().getClassLoader().getResource("dashboard-view.fxml"));
        Parent croot = dashboardLoader.load();
        DashboardController dashboardController = dashboardLoader.getController();
        dashboardController.setServer(server);
        ctrl.setDashboardController(dashboardController);

        Scene scene = new Scene(root, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("ConcertPro");
        primaryStage.show();

        ctrl.setParent(croot);
    }
}
