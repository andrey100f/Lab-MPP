module ro.mpp2024.concertpro {
    requires javafx.controls;
    requires javafx.fxml;


    opens ro.mpp2024.concertpro to javafx.fxml;
    exports ro.mpp2024.concertpro;
}