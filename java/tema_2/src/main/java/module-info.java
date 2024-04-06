module ro.mpp2024.concertpro {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.logging.log4j;
    requires java.sql;


    opens ro.mpp2024.concertpro to javafx.fxml;
    exports ro.mpp2024.concertpro;
}