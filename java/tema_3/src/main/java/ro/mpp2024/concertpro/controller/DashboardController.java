package ro.mpp2024.concertpro.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ro.mpp2024.concertpro.MainApplication;
import ro.mpp2024.concertpro.dao.exception.ValidationException;
import ro.mpp2024.concertpro.dao.model.Order;
import ro.mpp2024.concertpro.dao.model.Spectacle;
import ro.mpp2024.concertpro.service.OrderService;
import ro.mpp2024.concertpro.service.SpectacleService;
import ro.mpp2024.concertpro.utils.ControllerGetter;
import ro.mpp2024.concertpro.utils.MessageAlert;
import ro.mpp2024.concertpro.utils.event.SpectacleChangeEventType;
import ro.mpp2024.concertpro.utils.observer.Observer;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class DashboardController implements Observer<SpectacleChangeEventType> {
    @FXML private Button logoutButton;
    @FXML private TableView<Spectacle> spectacleTable;
    @FXML private TableColumn<Spectacle, Long> spectacleIdColumn;
    @FXML private TableColumn<Spectacle, String> spectacleNameColumn;
    @FXML private TableColumn<Spectacle, LocalDate> spectacleDateColumn;
    @FXML private TableColumn<Spectacle, String> spectaclePlaceColumn;
    @FXML private TableColumn<Spectacle, Long> seatsAvailableColumn;
    @FXML private TableColumn<Spectacle, Long> seatsSoldColumn;

    @FXML private TableView<Spectacle> filterSpectacleTable;
    @FXML private TableColumn<Spectacle, Long> filterSpectacleIdColumn;
    @FXML private TableColumn<Spectacle, String> filterSpectacleNameColumn;
    @FXML private TableColumn<Spectacle, LocalDate> filterSpectacleDateColumn;
    @FXML private TableColumn<Spectacle, String> filterSpectaclePlaceColumn;
    @FXML private TableColumn<Spectacle, Long> filterSeatsAvailableColumn;
    @FXML private TableColumn<Spectacle, Long> filterSeatsSoldColumn;

    @FXML private DatePicker spectacleDateInput;
    @FXML private TextField buyerNameInput;
    @FXML private TextField numberOfTicketsInput;

    private ObservableList<Spectacle> model = FXCollections.observableArrayList();
    private ObservableList<Spectacle> filterModel = FXCollections.observableArrayList();
    private SpectacleService spectacleService;
    private OrderService orderService;
    private static final Logger logger = LogManager.getLogger();

    public DashboardController() {
        logger.info("Initializing DashboardComtroller");

        this.logoutButton = new Button();
        this.buyerNameInput = new TextField();
        this.spectacleDateInput = new DatePicker();
        this.numberOfTicketsInput = new TextField();

        this.spectacleTable = new TableView<>();
        this.spectacleIdColumn = new TableColumn<>();
        this.spectacleNameColumn = new TableColumn<>();
        this.spectacleDateColumn = new TableColumn<>();
        this.spectaclePlaceColumn = new TableColumn<>();
        this.seatsAvailableColumn = new TableColumn<>();
        this.seatsSoldColumn = new TableColumn<>();

        this.filterSpectacleTable = new TableView<>();
        this.filterSpectacleIdColumn = new TableColumn<>();
        this.filterSpectacleNameColumn = new TableColumn<>();
        this.filterSpectacleDateColumn = new TableColumn<>();
        this.filterSpectaclePlaceColumn = new TableColumn<>();
        this.filterSeatsAvailableColumn = new TableColumn<>();
        this.filterSeatsSoldColumn = new TableColumn<>();
    }

    public void setSpectacleService(SpectacleService spectacleService) {
        this.spectacleService = spectacleService;
        this.spectacleService.addObserver(this);
        this.initModel();
    }

    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    public void initModel() {
        List<Spectacle> spectacles = this.spectacleService.getAllSpectacles();
        this.model.setAll(spectacles);
    }

    public void handleLogOut() throws IOException {
        logger.traceEntry("Initializing logging out.");

        FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("login-view.fxml"));
        LogInController logInController = ControllerGetter.getLoginController();
        loader.setController(logInController);

        Parent root = loader.load();
        Stage window = (Stage) this.logoutButton.getScene().getWindow();
        window.setScene(new Scene(root, 600, 400));
        logger.traceExit();
    }

    public void handleFilter() {
        logger.traceEntry("Initializing filter task");
        try {
            LocalDate date = this.spectacleDateInput.getValue();

            if(date == null) {
                throw new ValidationException("The date input should not be null!");
            }

            List<Spectacle> spectacles = this.spectacleService.getAllSpectaclesByDate(date);
            this.initializeFilterTable(spectacles);
        }
        catch (ValidationException exception) {
            logger.error(exception.getMessage());
            MessageAlert.showErrorMessage(null, exception.getMessage());
        }
        logger.traceExit();
    }

    public void handleResetFilter() {
        this.filterSpectacleTable.getItems().clear();
        this.spectacleDateInput.setValue(null);
    }

    public void handleBuy() {
        try {
            logger.traceEntry("Initializing Buy Task.");

            Order order = getOrder();
            this.orderService.addOrder(order);

            List<Spectacle> spectacles = this.spectacleService.getAllSpectacles();
            this.updateTable(spectacles);

            MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "Adaugare comanda",
                    "Comanda adaugata cu success!");
        }
        catch (ValidationException exception) {
            logger.error(exception.getMessage());
            MessageAlert.showErrorMessage(null, exception.getMessage());
        }

        this.handleResetFilter();
        this.buyerNameInput.clear();
        this.numberOfTicketsInput.clear();

        logger.traceExit();
    }

    @Override
    public void update(SpectacleChangeEventType event) {
        this.initModel();
    }

    @FXML
    public void initialize() {
        this.spectacleIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        this.spectacleNameColumn.setCellValueFactory(new PropertyValueFactory<>("artistName"));
        this.spectacleDateColumn.setCellValueFactory(new PropertyValueFactory<>("spectacleDate"));
        this.spectaclePlaceColumn.setCellValueFactory(new PropertyValueFactory<>("spectaclePlace"));
        this.seatsAvailableColumn.setCellValueFactory(new PropertyValueFactory<>("seatsAvailable"));
        this.seatsSoldColumn.setCellValueFactory(new PropertyValueFactory<>("seatsSold"));

        this.spectacleTable.setItems(this.model);
    }

    @FXML
    public void updateTable(List<Spectacle> spectacles) {
        this.model.setAll(spectacles);
        this.initialize();
        this.spectacleTable.setItems(this.model);
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

    private Order getOrder() {
        if(this.filterSpectacleTable.getItems().isEmpty()) {
            throw new ValidationException("Nu exista spectacol la data specificata!!");
        }

        Spectacle spectacle = this.filterSpectacleTable.getSelectionModel().getSelectedItems().get(0);
        String buyerName = this.buyerNameInput.getText();

        if(numberOfTicketsInput.getText().isBlank()) {
            throw new ValidationException("Seats number should not be null!!");
        }

        Long numberOfSeats = Long.parseLong(this.numberOfTicketsInput.getText());

        return new Order(buyerName, spectacle, numberOfSeats);
    }
}
