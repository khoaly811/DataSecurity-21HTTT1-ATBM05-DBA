package atbm_05;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.event.ActionEvent;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import DataAccessLayer.DataAccessLayer;

public class LoginController {
    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;


    @FXML
    private void loginButtonAction(ActionEvent event) throws SQLException, IOException {
        String username = usernameField.getText();
        String password = passwordField.getText();

        Alert.AlertType popUp = Alert.AlertType.INFORMATION;
        String title = "Login Successfully";
        String msg = "Welcome " + username + "!";
        DataAccessLayer dal;
        Connection conn;
        boolean flag = false;
        try {
            dal = DataAccessLayer.getInstance(username, password);
            conn = dal.connect();
            flag = true;
        } catch (SQLException e) {
            popUp = Alert.AlertType.ERROR;
            title = "Login failed";
            msg = "Invalid username or password!";
            showAlert(popUp, title, msg);
        } 
        if (flag) {
            dal = DataAccessLayer.getInstance(username, password);
            conn = dal.connect();
            try {
                PreparedStatement pst = conn.prepareStatement("select * from session_roles where role = 'DBA'",
                ResultSet.TYPE_SCROLL_INSENSITIVE , 
                ResultSet.CONCUR_UPDATABLE);
                ResultSet rs = pst.executeQuery();
            
                if (!rs.next()) {
                    throw new SecurityException();
                }

                FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("Dashboard.fxml"));
                Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
                Scene scene1 = new Scene(fxmlLoader.load());
                stage.setScene(scene1);
                stage.centerOnScreen();
                stage.show();
                
            } catch (SecurityException e) {
                popUp = Alert.AlertType.INFORMATION;
                title = "Login failed";
                msg = "You are not system admin";
                ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
                showAlert(popUp, title, msg);
            } 
           
        }
    } 

    private void showAlert(Alert.AlertType alertType, String title, String message) throws SQLException {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
};