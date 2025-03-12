module dev.phil.poobdatv6 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.postgresql.jdbc;


    opens dev.phil.poobdatv6 to javafx.fxml;
    exports dev.phil.poobdatv6;
    exports dev.phil.poobdatv6.controller;
    opens dev.phil.poobdatv6.controller to javafx.fxml;
    exports dev.phil.poobdatv6.view;
    opens dev.phil.poobdatv6.view to javafx.fxml;
    
    // Abrir o pacote model para o JavaFX
    exports dev.phil.poobdatv6.model;
    opens dev.phil.poobdatv6.model to javafx.base, javafx.fxml;
}