package concertpro.client.gui;

import concertpro.model.Order;
import concertpro.model.Spectacle;
import concertpro.model.User;
import concertpro.services.ChatException;
import concertpro.services.IChatObserver;
import concertpro.services.IChatService;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class DashboardController implements Initializable, IChatObserver {

    @FXML Button logoutButton;
    @FXML TableView<Spectacle> spectacleTable;
    @FXML TableColumn<Spectacle, Long> spectacleIdColumn;
    @FXML TableColumn<Spectacle, String> spectacleNameColumn;
    @FXML TableColumn<Spectacle, LocalDate> spectacleDateColumn;
    @FXML TableColumn<Spectacle, String> spectaclePlaceColumn;
    @FXML TableColumn<Spectacle, Long> seatsAvailableColumn;
    @FXML TableColumn<Spectacle, Long> seatsSoldColumn;

    @FXML TableView<Spectacle> filterSpectacleTable;
    @FXML TableColumn<Spectacle, Long> filterSpectacleIdColumn;
    @FXML TableColumn<Spectacle, String> filterSpectacleNameColumn;
    @FXML TableColumn<Spectacle, LocalDate> filterSpectacleDateColumn;
    @FXML TableColumn<Spectacle, String> filterSpectaclePlaceColumn;
    @FXML TableColumn<Spectacle, Long> filterSeatsAvailableColumn;
    @FXML TableColumn<Spectacle, Long> filterSeatsSoldColumn;

    @FXML DatePicker spectacleDateInput;
    @FXML TextField buyerNameInput;
    @FXML TextField numberOfTicketsInput;

    private ObservableList<Spectacle> model;
    private ObservableList<Spectacle> filterModel = FXCollections.observableArrayList();

    private IChatService server;
    private User user;

    public DashboardController() {
        System.out.println("DashboardController created");
    }

    @FXML
    public void setSpectacles() {
        try {
            Spectacle[] spectacles = this.server.getAllSpectacles();

            spectacleTable.getItems().clear();
            this.model = FXCollections.observableArrayList();
            this.model.setAll(Arrays.stream(spectacles).toList());
        } catch (ChatException e) {
            throw new RuntimeException(e);
        }
        spectacleTable.setItems(this.model);
    }

    public DashboardController(IChatService server) {
        this.server = server;
    }

    @Override
    public void userLoggedIn(User friend) throws ChatException {

    }

    @Override
    public void userLoggedOut(User friend) throws ChatException {

    }

    @Override
    public void updateSpectacles(Spectacle[] spectacles) {
        Platform.runLater(() -> {

            this.spectacleIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
            this.spectacleNameColumn.setCellValueFactory(new PropertyValueFactory<>("artistName"));
            this.spectacleDateColumn.setCellValueFactory(new PropertyValueFactory<>("spectacleDate"));
            this.spectaclePlaceColumn.setCellValueFactory(new PropertyValueFactory<>("spectaclePlace"));
            this.seatsAvailableColumn.setCellValueFactory(new PropertyValueFactory<>("seatsAvailable"));
            this.seatsSoldColumn.setCellValueFactory(new PropertyValueFactory<>("seatsSold"));

            ObservableList<Spectacle> newModel = FXCollections.observableArrayList();
            newModel.setAll(spectacles);
            spectacleTable.setItems(newModel);
        });
    }

    public void spectaclesUpdated() throws ChatException {
        Spectacle[] spectacles = this.server.getAllSpectacles();

        ObservableList<Spectacle> newModel = FXCollections.observableArrayList();
        newModel.setAll(spectacles);

        spectacleTable.setItems(newModel);

        System.out.println(Arrays.toString(spectacles));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.spectacleIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        this.spectacleNameColumn.setCellValueFactory(new PropertyValueFactory<>("artistName"));
        this.spectacleDateColumn.setCellValueFactory(new PropertyValueFactory<>("spectacleDate"));
        this.spectaclePlaceColumn.setCellValueFactory(new PropertyValueFactory<>("spectaclePlace"));
        this.seatsAvailableColumn.setCellValueFactory(new PropertyValueFactory<>("seatsAvailable"));
        this.seatsSoldColumn.setCellValueFactory(new PropertyValueFactory<>("seatsSold"));
    }

    @FXML
    public void updateTable(List<Spectacle> spectacles) {
        spectacleTable.getItems().clear();
        this.model= FXCollections.observableArrayList();
        this.model.setAll(spectacles);
        spectacleTable.setItems(this.model);
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void handleBuy(ActionEvent actionEvent) throws ChatException {
        try {
            Order order = getOrder();
            this.server.addOrder(order);

            MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "Adaugare comanda",
                "Comanda adaugata cu success!");

            this.spectaclesUpdated();

        }
        catch (ChatException exception) {
            MessageAlert.showErrorMessage(null, exception.getMessage());
        }


        this.handleResetFilter(actionEvent);
        this.buyerNameInput.clear();
        this.numberOfTicketsInput.clear();

    }

    public void handleLogOut(ActionEvent actionEvent) throws IOException, ChatException {
        this.server.logout(user, this);

        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("login-view.fxml"));
        FXMLLoader dashboardLoader = new FXMLLoader(getClass().getClassLoader().getResource("dashboard-view.fxml"));
        Parent root = loader.load();
        Parent croot = dashboardLoader.load();
        Stage window = (Stage) this.logoutButton.getScene().getWindow();

        LogInController logInController = loader.getController();
        logInController.setServer(this.server);
        logInController.setParent(croot);
        logInController.setDashboardController(this);
        window.setScene(new Scene(root, 600, 400));
    }

    public void handleFilter(ActionEvent actionEvent) throws ChatException {
        try {
            LocalDate date = this.spectacleDateInput.getValue();

            if(date == null) {
                throw new ChatException("Date should not be null!!");
            }

            Spectacle[] spectacles = this.server.getSpectaclesByDate(date);

            this.initializeFilterTable(Arrays.stream(spectacles).toList());
        }
        catch (ChatException exception) {
            MessageAlert.showErrorMessage(null, exception.getMessage());
        }
    }

    @FXML
    public void initializeFilterTable(List<Spectacle> spectacles) {
        this.filterModel.setAll(spectacles);

        this.filterSpectacleIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        this.filterSpectacleNameColumn.setCellValueFactory(new PropertyValueFactory<>("artistName"));
        this.filterSpectacleDateColumn.setCellValueFactory(new PropertyValueFactory<>("spectacleDate"));
        this.filterSpectaclePlaceColumn.setCellValueFactory(new PropertyValueFactory<>("spectaclePlace"));
        this.filterSeatsAvailableColumn.setCellValueFactory(new PropertyValueFactory<>("seatsAvailable"));
        this.filterSeatsSoldColumn.setCellValueFactory(new PropertyValueFactory<>("seatsSold"));

        this.filterSpectacleTable.setItems(this.filterModel);
    }

    public void handleResetFilter(ActionEvent actionEvent) {
        this.filterSpectacleTable.getItems().clear();
        this.spectacleDateInput.setValue(null);
    }

    public void setServer(IChatService server) {
        this.server = server;
    }

    private Order getOrder() throws ChatException {
        if(this.filterSpectacleTable.getItems().isEmpty()) {
            throw new ChatException("Nu exista spectacol la data specificata!!");
        }

        Spectacle spectacle = this.filterSpectacleTable.getSelectionModel().getSelectedItems().get(0);
        String buyerName = this.buyerNameInput.getText();

        if(numberOfTicketsInput.getText().isBlank()) {
            throw new ChatException("Seats number should not be null!!");
        }

        Long numberOfSeats = Long.parseLong(this.numberOfTicketsInput.getText());

        return new Order(buyerName, spectacle, numberOfSeats);
    }
}
