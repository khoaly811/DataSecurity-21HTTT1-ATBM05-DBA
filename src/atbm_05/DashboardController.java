package atbm_05;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import dto.User;
import dto.Role;
import DataAccessLayer.DataAccessLayer;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;

public class DashboardController {
    @FXML
    private TableView<User> userTableView;

    @FXML
    private TableColumn<User, String> usernameColumn;

    @FXML
    private TableColumn<User, String> accountStatusColumn;

    @FXML
    private TableColumn<User, String> createdColumn;

    @FXML
    private TableColumn<User, String> lastLoginColumn;

    @FXML
    private TableColumn<User, String> grantedRoleColumn;

    @FXML
    private ObservableList<User> userList = FXCollections.observableArrayList();

    @FXML
    private Label userUsernameUpdateLabel;

    @FXML
    private Label userPasswordUpdateLabel;

    @FXML
    private TextField userUsernameUpdateTextField;

    @FXML
    private TextField searchRoleField;

    @FXML
    private PasswordField userPasswordUpdateTextField;
    @FXML
    private Button addOK;

    @FXML
    private TableView<Role> roleTableView;

    @FXML
    private TableColumn<Role, String> roleColumn;

    @FXML
    private TableColumn<Role, String> ownerColumn;

    @FXML
    private TableColumn<Role, String> tableNameColumn;

    @FXML
    private TableColumn<Role, String> columnNameColumn;

    @FXML
    private TableColumn<Role, String> privilegeColumn;

    @FXML
    private TableColumn<Role, String> grantableColumn;

    private ObservableList<Role> roleList = FXCollections.observableArrayList();

    @FXML
    private TextField updateUsernameTextField;

    @FXML
    private PasswordField updatePasswordTextField;

    @FXML
    private TextField ADDRoleField;

    @FXML
    private Button ADDRoleBtn;

    @FXML
    private Button DELETERoleWantToBTN;

    @FXML
    private Button ADDRoleWantoBTN;

    @FXML
    private Label ADDroleText;

    @FXML
    private Label UpdateUserPassText;

    @FXML
    private Label UpdateUserPassUserName;

    @FXML
    private Label UpdateUserPassPass;

    @FXML
    private Button UpdateUserPassBtnExe;

    @FXML
    public void initialize() {
        // Initialize table columns
        usernameColumn.setCellValueFactory(cellData -> cellData.getValue().USERNAMEproperty());
        accountStatusColumn.setCellValueFactory(cellData -> cellData.getValue().ACCOUNT_STATUSproperty());
        createdColumn.setCellValueFactory(cellData -> cellData.getValue().CREATEDproperty().asString());
        lastLoginColumn.setCellValueFactory(cellData -> cellData.getValue().LAST_LOGINproperty().asString());
        grantedRoleColumn.setCellValueFactory(cellData -> cellData.getValue().GRANTED_ROLEproperty());
        roleColumn.setCellValueFactory(cellData -> cellData.getValue().ROLEproperty());
        ownerColumn.setCellValueFactory(cellData -> cellData.getValue().OWNERproperty());
        tableNameColumn.setCellValueFactory(cellData -> cellData.getValue().TABLE_NAMEproperty());
        columnNameColumn.setCellValueFactory(cellData -> cellData.getValue().COLUMN_NAMEproperty());
        privilegeColumn.setCellValueFactory(cellData -> cellData.getValue().PRIVILEGEproperty());
        grantableColumn.setCellValueFactory(cellData -> cellData.getValue().GRANTABLEproperty());
        searchRoleField.textProperty().addListener((observable, oldValue, newValue) -> {
            searchRoles(newValue);
        });
        roleList = FXCollections.observableArrayList(); 

        loadRolesFromDatabase();
        // Load users from database
        loadUsersFromDatabase();

    }

    @FXML
    private void onAddUserButtonClick(ActionEvent event) {
        userUsernameUpdateLabel.setVisible(true);
        userPasswordUpdateLabel.setVisible(true);
        userUsernameUpdateTextField.setVisible(true);
        userPasswordUpdateTextField.setVisible(true);
        addOK.setVisible(true);

        userUsernameUpdateLabel.setDisable(false);
        userPasswordUpdateLabel.setDisable(false);
        userUsernameUpdateTextField.setDisable(false);
        userPasswordUpdateTextField.setDisable(false);
        addOK.setDisable(false);
    }

    @FXML
    private void onAddOKUserButtonClick(ActionEvent event) {
        // Retrieve the values from the input fields
        String username = userUsernameUpdateTextField.getText().trim();
        String password = userPasswordUpdateTextField.getText().trim();

        // Validate the input fields
        if (username.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "Please enter both username and password.");
            return;
        }

        // Insert the new user into the database
        DataAccessLayer dal = null;
        Connection conn = null;
        CallableStatement cst = null;

        try {
            dal = DataAccessLayer.getInstance("", "");
            conn = dal.connect();
            // pst = conn.prepareStatement(String.format("CREATE USER %s IDENTIFIED BY %s",
            // username, password));
            cst = conn.prepareCall("{CALL SP_CREATE_USER(?, ?)}");
            // Set parameters for the stored procedure
            cst.setString(1, username);
            cst.setString(2, password);
            // Execute the stored procedure
            System.out.println("nhan beo 1");
            cst.execute();
            /// pst = conn.prepareStatement("CREATE USERNAME MAPMINHBEO IDENTIFIED BY
            /// Rack123456");
            System.out.println("nhan beo 2");
            showAlert(Alert.AlertType.INFORMATION, "Success", "User added successfully.");
            // Clear the input fields after successful insertion
            userUsernameUpdateTextField.clear();
            userPasswordUpdateTextField.clear();
            // Refresh the TableView to reflect the changes
            loadUsersFromDatabase();
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to add user: " + e.getMessage());
            System.out.println(e.getMessage());
        }
    }

    @FXML
    private void onUpdatePasswordButtonClick(ActionEvent event) {
        String username = updateUsernameTextField.getText().trim();
        String newPassword = updatePasswordTextField.getText().trim();

        if (username.isEmpty() || newPassword.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "Please enter both username and new password.");
            return;
        }

        // Call the method to update user password
        updateUserPassword(username, newPassword);
    }

    private void updateUserPassword(String username, String newPassword) {
        DataAccessLayer dal = null;
        Connection conn = null;
        CallableStatement cst = null;

        try {
            dal = DataAccessLayer.getInstance("", "");
            conn = dal.connect();
            cst = conn.prepareCall("{CALL SP_CHANGE_PASS_USER(?, ?)}");
            cst.setString(1, username);
            cst.setString(2, newPassword);
            System.out.println("nhan beo 1");
            cst.execute();
            System.out.println("nhan beo 2");
            showAlert(Alert.AlertType.INFORMATION, "Success", "User password updated successfully.");

            // Clear the input fields after successful update
            updateUsernameTextField.clear();
            updatePasswordTextField.clear();

            // Refresh the TableView to reflect the changes
            loadUsersFromDatabase();
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to update user password: " + e.getMessage());
            System.out.println(e.getMessage());
        }
    }

    @FXML
    private void onAddRoleButtonClick(ActionEvent event) {
        if (!ADDRoleField.isVisible() && !ADDRoleBtn.isVisible() && !ADDroleText.isVisible()) {
            // If all elements are currently not visible, make them visible
            ADDRoleField.setVisible(true);
            ADDRoleBtn.setVisible(true);
            ADDroleText.setVisible(true);
            System.out.println("GGGG"); // Print when elements become visible
            ADDRoleField.setDisable(false);
            ADDRoleBtn.setDisable(false);
            ADDroleText.setDisable(false);
        } else {
            // If any one of the elements is currently visible, make them all invisible
            ADDRoleField.setVisible(false);
            ADDRoleBtn.setVisible(false);
            ADDroleText.setVisible(false);
            System.out.println("GGGG"); // Print when elements become invisible
            ADDRoleField.setDisable(true);
            ADDRoleBtn.setDisable(true);
            ADDroleText.setDisable(true);
        }
    }

    @FXML
    private void onUpdateUserButtonClick(ActionEvent event) {
        if (!UpdateUserPassPass.isVisible()) {
            // If all elements are currently not visible, make them visible
            UpdateUserPassText.setVisible(true);
            UpdateUserPassUserName.setVisible(true);
            UpdateUserPassPass.setVisible(true);
            updateUsernameTextField.setVisible(true);
            updatePasswordTextField.setVisible(true);
            UpdateUserPassBtnExe.setVisible(true);
            System.out.println("GGGG"); // Print when elements become visible
            UpdateUserPassText.setDisable(false);
            UpdateUserPassUserName.setDisable(false);
            UpdateUserPassPass.setDisable(false);
            updateUsernameTextField.setDisable(false);
            updatePasswordTextField.setDisable(false);
            UpdateUserPassBtnExe.setDisable(false);
        } else {
            // If any one of the elements is currently visible, make them all invisible
            UpdateUserPassText.setVisible(false);
            UpdateUserPassUserName.setVisible(false);
            UpdateUserPassPass.setVisible(false);
            updateUsernameTextField.setVisible(false);
            updatePasswordTextField.setVisible(false);
            UpdateUserPassBtnExe.setVisible(false);
            System.out.println("GGGG"); // Print when elements become invisible
            UpdateUserPassText.setDisable(true);
            UpdateUserPassUserName.setDisable(true);
            UpdateUserPassPass.setDisable(true);
            updateUsernameTextField.setDisable(true);
            updatePasswordTextField.setDisable(true);
            UpdateUserPassBtnExe.setDisable(true);
        }
    }

    @FXML
    private void onAddOKRoleButtonClick(ActionEvent event) {
        // Retrieve the values from the input fields
        // username la ten role :)))))
        String username = ADDRoleField.getText().trim();

        // Validate the input fields
        if (username.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "Please enter role name :<");
            return;
        }

        // Insert the new user into the database
        DataAccessLayer dal = null;
        Connection conn = null;
        CallableStatement cst = null;

        try {
            dal = DataAccessLayer.getInstance("", "");
            conn = dal.connect();
            // pst = conn.prepareStatement(String.format("CREATE USER %s IDENTIFIED BY %s",
            // username, password));
            cst = conn.prepareCall("{CALL SP_CREATE_ROLE(?)}");
            // Set parameters for the stored procedure
            cst.setString(1, username);
            // Execute the stored procedure
            System.out.println("nhan beo 1 ROLE");
            cst.execute();
            /// pst = conn.prepareStatement("CREATE USERNAME MAPMINHBEO IDENTIFIED BY
            /// Rack123456");
            System.out.println("nhan beo 2 ROLE");
            showAlert(Alert.AlertType.INFORMATION, "Success", "Role added successfully.");
            // Clear the input fields after successful insertion
            ADDRoleField.clear();
            // Refresh the TableView to reflect the changes
            loadRolesFromDatabase();
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to add role: " + e.getMessage());
            System.out.println(e.getMessage());
        }
    }

    @FXML
    private void onDeleteButtonClickRowRole(ActionEvent event) {
        // Get the selected row
        Role selectedRole = roleTableView.getSelectionModel().getSelectedItem();

        // Check if a row is selected
        if (selectedRole != null) {
            // Retrieve data from the first column of the selected row
            String roleName = selectedRole.getROLE();

            System.out.println("Role Name: " + roleName);

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to delete the role " + roleName + " ?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                DataAccessLayer dal = null;
                Connection conn = null;
                CallableStatement cst = null;

                try {
                    dal = DataAccessLayer.getInstance("", "");
                    conn = dal.connect();
                    cst = conn.prepareCall("{CALL SP_DROP_ROLE(?)}");
                    cst.setString(1, roleName);
                    cst.execute();
                    showAlert(Alert.AlertType.INFORMATION, "Success", "Role deleted successfully.");
                    loadRolesFromDatabase();
                    roleTableView.refresh();
                } catch (SQLException e) {
                    showAlert(Alert.AlertType.ERROR, "Error", "Failed to delete role: " + e.getMessage());
                    System.out.println(e.getMessage());
                }
            }

        } else {
            // If no row is selected, show an alert
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText(null);
            alert.setContentText("Please select a row to delete.");
            alert.showAndWait();
        }
    }
    @FXML
    private void onSearchRoleButtonClick(ActionEvent event) {
        // Retrieve the search query from the search field
        String searchText = searchRoleField.getText().trim();

        // Call the method to search roles
        searchRoles(searchText);
    }



    private void searchRoles(String searchText) {
        DataAccessLayer dal = null;
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
    
        try {
            dal = DataAccessLayer.getInstance("your_username", "your_password");
            conn = dal.connect();

            pst = conn.prepareStatement("SELECT * FROM role_tab_privs WHERE owner = 'C##QLK' AND role LIKE ?");
    
            // Set the search parameter
            pst.setString(1, "%" + searchText + "%");
            System.out.println("nhan beo 1");
            // Execute the query
            rs = pst.executeQuery();
            System.out.println("nhan beo 2");
            // Create a list to hold the roles
            List<Role> roleList = new ArrayList<>();
    
            // Iterate over the result set
            while (rs.next()) {
                Role role = new Role();
                role.setROLE(rs.getString("ROLE"));
                role.setOWNER(rs.getString("OWNER"));
                role.setTABLE_NAME(rs.getString("TABLE_NAME"));
                role.setCOLUMN_NAME(rs.getString("COLUMN_NAME"));
                role.setPRIVILEGE(rs.getString("PRIVILEGE"));
                role.setGRANTABLE(rs.getString("GRANTABLE"));
    
                // Add the role to the list
                roleList.add(role);
            }
    
            // Set the loaded roles to the table view
            roleTableView.setItems(FXCollections.observableArrayList(roleList));
    
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load roles from the database.");
            System.out.println(e.getMessage());
        }
    }
    

    private void loadUsersFromDatabase() {
        DataAccessLayer dal = null;
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            dal = DataAccessLayer.getInstance("your_username", "your_password");
            conn = dal.connect();
            pst = conn.prepareStatement(
                    "select * from dba_users join dba_role_privs on dba_users.username = dba_role_privs.grantee where (username like 'NV%' or username like 'SV%')");
            rs = pst.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setUSERNAME(rs.getString("USERNAME"));
                user.setACCOUNT_STATUS((rs.getString("ACCOUNT_STATUS")));
                user.setCREATED(rs.getDate("CREATED").toLocalDate());
                if (rs.getDate("LAST_LOGIN") == null) {
                    user.setLAST_LOGIN(null);
                } else {
                    user.setLAST_LOGIN(rs.getDate("LAST_LOGIN").toLocalDate());
                }
                user.setGRANTED_ROLE(rs.getString("GRANTED_ROLE"));
                // if (rs.getDate("PASSWORD_CHANGE_DATE") == null){
                // user.setPASSWORD_CHANGE_DATE(null);
                // }
                // else{
                // user.setPASSWORD_CHANGE_DATE(rs.getDate("PASSWORD_CHANGE_DATE").toLocalDate());
                // }

                userList.add(user);
            }
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load users from the database.");
        } finally {

        }

        // Set the loaded users to the table view
        userTableView.setItems(userList);
    }

    private void loadRolesFromDatabase() {
        DataAccessLayer dal = null;
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            dal = DataAccessLayer.getInstance("your_username", "your_password");
            conn = dal.connect();
            pst = conn.prepareStatement("select* from role_tab_privs where owner = 'C##QLK' ");
            rs = pst.executeQuery();
            while (rs.next()) {
                Role role = new Role();
                role.setROLE(rs.getString("ROLE"));
                role.setOWNER((rs.getString("OWNER")));
                role.setTABLE_NAME((rs.getString("TABLE_NAME")));
                role.setCOLUMN_NAME((rs.getString("COLUMN_NAME")));
                role.setPRIVILEGE((rs.getString("PRIVILEGE")));
                role.setGRANTABLE(rs.getString("GRANTABLE"));
                // if (rs.getDate("PASSWORD_CHANGE_DATE") == null){
                // user.setPASSWORD_CHANGE_DATE(null);
                // }
                // else{
                // user.setPASSWORD_CHANGE_DATE(rs.getDate("PASSWORD_CHANGE_DATE").toLocalDate());
                // }

                roleList.add(role);
            }
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load roles from the database.");
            System.out.println(e.getMessage());
        }

        // Set the loaded users to the table view
        roleTableView.setItems(roleList);
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
