module com.atbm.fitapp {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.atbm.fitapp to javafx.fxml;
    exports com.atbm.fitapp;
}