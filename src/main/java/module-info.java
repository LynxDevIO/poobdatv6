module dev.phil.poobdatv6 {
    requires javafx.controls;
    requires javafx.fxml;


    opens dev.phil.poobdatv6 to javafx.fxml;
    exports dev.phil.poobdatv6;
}