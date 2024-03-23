package atbm_05;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;

import java.sql.SQLException;

import DataAccessLayer.DataAccessLayer;

public class LoginController {
    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private void loginButtonAction(ActionEvent event) throws SQLException {
        String username = usernameField.getText();
        String password = passwordField.getText();

        // Dummy authentication - Replace with your authentication logic
        if (username.equals("ad") && password.equals("123")) {
            showAlert(Alert.AlertType.INFORMATION, "Login Successful", "Welcome, " + username + "!");
        } else {
            showAlert(Alert.AlertType.ERROR, "Login Failed", "Invalid username or password!");
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) throws SQLException {
        Alert alert = new Alert(alertType);
        message = DataAccessLayer.getNHANSU("NV0101").getVAITRO();
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}