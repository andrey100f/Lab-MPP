module ro.mpp2024.concertpro {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.logging.log4j;
    requires java.sql;


    opens ro.mpp2024.concertpro to javafx.fxml;
    exports ro.mpp2024.concertpro;
    exports ro.mpp2024.concertpro.controller;
    opens ro.mpp2024.concertpro.controller to javafx.fxml;

    exports ro.mpp2024.concertpro.dao.model;
    exports ro.mpp2024.concertpro.service;
    exports ro.mpp2024.concertpro.utils.event;
}