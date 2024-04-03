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
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import oracle.jdbc.OracleTypes;
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
    private Label UpdateUserPassUserName;

    @FXML
    private Label UpdateUserPassPass;

    @FXML
    private Button UpdateUserPassBtnExe;

    @FXML
    private TextField searchUserField;

    @FXML
    private Button GrantRoleBtn;

    @FXML
    private Button GrantPrivBtnOnClick;

    @FXML
    private Button GrantRoleBTNOK;

    @FXML
    private TextField GrantRoleTextField;

    @FXML
    private Label GrantTextLabel;

    @FXML
    private Button revokePrivfromRoleBTN;

    @FXML
    private Button RevoketableOK;

    @FXML
    private TextField RevoketableField;

    @FXML
    private SplitMenuButton PrivSelectRevoke;

    @FXML
    private Button revokePrivfromRoleBTN1;

    @FXML
    private Button RevoketableOKRole;

    @FXML
    private TextField RevoketableFieldRole;

    @FXML
    private SplitMenuButton PrivSelectRevokeRole;

    @FXML
    private TextField GrantPrivtoUserField;

    @FXML
    private Label ToText;

    @FXML
    private MenuButton SelectPrivGrant;

    @FXML
    private TextField TableNamePrivField;

    @FXML
    private Button ButtonAOKgrnantPriv;

    @FXML
    private MenuButton WithGrantOptUser;

    @FXML
    private TextField TableNamePrivField1;

    @FXML
    private MenuButton SelectPrivGrant1;
    
    @FXML
    private TextField TableNamePrivField2;

    @FXML
    private TextField TableNamePrivField11;

    @FXML
    private MenuButton WithGrantOptUser1;

    @FXML
    private TextField RoleName;
    @FXML
    private Button ButtonAOKgrnantPriv1;

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
        searchUserField.textProperty().addListener((observable, oldValue, newValue) -> {
            searchUsers(newValue);
        });
        roleList = FXCollections.observableArrayList();

        loadRolesFromDatabase();
        // Load users from database
        loadUsersFromDatabase();

    }

    @FXML
    private void onAddUserButtonClick(ActionEvent event) {
        if (!userUsernameUpdateLabel.isVisible()) {
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
        } else {

            userUsernameUpdateLabel.setVisible(false);
            userPasswordUpdateLabel.setVisible(false);
            userUsernameUpdateTextField.setVisible(false);
            userPasswordUpdateTextField.setVisible(false);
            addOK.setVisible(false);

            userUsernameUpdateLabel.setDisable(true);
            userPasswordUpdateLabel.setDisable(true);
            userUsernameUpdateTextField.setDisable(true);
            userPasswordUpdateTextField.setDisable(true);
            addOK.setDisable(true);
        }
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
            UpdateUserPassUserName.setVisible(true);
            UpdateUserPassPass.setVisible(true);
            updateUsernameTextField.setVisible(true);
            updatePasswordTextField.setVisible(true);
            UpdateUserPassBtnExe.setVisible(true);
            System.out.println("GGGG"); // Print when elements become visible
            UpdateUserPassUserName.setDisable(false);
            UpdateUserPassPass.setDisable(false);
            updateUsernameTextField.setDisable(false);
            updatePasswordTextField.setDisable(false);
            UpdateUserPassBtnExe.setDisable(false);
        } else {
            // If any one of the elements is currently visible, make them all invisible
            UpdateUserPassUserName.setVisible(false);
            UpdateUserPassPass.setVisible(false);
            updateUsernameTextField.setVisible(false);
            updatePasswordTextField.setVisible(false);
            UpdateUserPassBtnExe.setVisible(false);
            System.out.println("GGGG"); // Print when elements become invisible
            UpdateUserPassUserName.setDisable(true);
            UpdateUserPassPass.setDisable(true);
            updateUsernameTextField.setDisable(true);
            updatePasswordTextField.setDisable(true);
            UpdateUserPassBtnExe.setDisable(true);
        }
    }

    @FXML
    private void onDeleteButtonClickRowUser(ActionEvent event) {
        // Get the selected row
        User selectedUser = userTableView.getSelectionModel().getSelectedItem();

        // Check if a row is selected
        if (selectedUser != null) {
            // Retrieve data from the first column of the selected row
            String userName = selectedUser.getUSERNAME();

            System.out.println("User Name to delete: " + userName);

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to delete user " + userName + " ?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                DataAccessLayer dal = null;
                Connection conn = null;
                CallableStatement cst = null;

                try {
                    dal = DataAccessLayer.getInstance("", "");
                    conn = dal.connect();
                    cst = conn.prepareCall("{CALL SP_DROP_USER(?)}");
                    cst.setString(1, userName);
                    cst.execute();
                    showAlert(Alert.AlertType.INFORMATION, "Success", "User deleted successfully.");
                    loadUsersFromDatabase();
                    userTableView.refresh();
                    reloadWindow(); // force reload
                } catch (SQLException e) {
                    showAlert(Alert.AlertType.ERROR, "Error", "Failed to delete user: " + e.getMessage());
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
                    reloadWindow(); // force reload
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

    private void searchUsers(String searchText) {
        DataAccessLayer dal = null;
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        System.out.println(searchText);

        try {
            dal = DataAccessLayer.getInstance("your_username", "your_password");
            conn = dal.connect();

            pst = conn.prepareStatement(
                    "SELECT * FROM dba_users  join dba_role_privs on dba_users.username = dba_role_privs.grantee WHERE (username LIKE 'NV%' OR USERNAME LIKE 'SV%') AND USERNAME LIKE ?");

            // Set the search parameter
            pst.setString(1, "%" + searchText + "%");
            System.out.println("nhan beo 1");
            // Execute the query
            rs = pst.executeQuery();
            System.out.println(pst);
            System.out.println(rs);
            System.out.println("nhan beo 2");
            // Create a list to hold the users
            List<User> userList = new ArrayList<>();

            // Iterate over the result set
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

                // Add the role to the list
                userList.add(user);
            }

            // Set the loaded roles to the table view
            userTableView.setItems(FXCollections.observableArrayList(userList));

        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load users from the database.");
            System.out.println(e.getMessage());
        }
    }

    private void loadUsersFromDatabase() {
        DataAccessLayer dal = null;
        Connection conn = null;
        CallableStatement pst = null;
        ResultSet rs = null;

        try {
            dal = DataAccessLayer.getInstance("your_username", "your_password");
            conn = dal.connect();
            pst = conn.prepareCall(
                    "{CALL GET_USERS_PROC(?)}");
                    pst.registerOutParameter(1, OracleTypes.CURSOR);
                    pst.execute();
                    rs = (ResultSet) pst.getObject(1);
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
            userTableView.setItems(userList);
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

    private void reloadWindow() {
        // Get the current stage (window)
        Stage stage = (Stage) roleTableView.getScene().getWindow();

        // Close the window
        stage.close();

        // Reopen the window
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Dashboard.fxml"));
        Parent root;
        try {
            root = loader.load();
            Stage newStage = new Stage();
            newStage.setScene(new Scene(root));
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void GrantRoleBtnOnClick(ActionEvent event) {
        if (!GrantTextLabel.isVisible()) {
            GrantTextLabel.setVisible(true);
            GrantRoleBTNOK.setVisible(true);
            GrantRoleTextField.setVisible(true);
            GrantPrivtoUserField.setVisible(true);
            ToText.setVisible(true);

            GrantTextLabel.setDisable(false);
            GrantRoleBTNOK.setDisable(false);
            GrantRoleTextField.setDisable(false);
            GrantPrivtoUserField.setDisable(false);
            ToText.setDisable(false);

        } else {

            GrantTextLabel.setVisible(false);
            GrantRoleBTNOK.setVisible(false);
            GrantRoleTextField.setVisible(false);
            GrantPrivtoUserField.setVisible(false);
            ToText.setVisible(false);

            GrantTextLabel.setDisable(true);
            GrantRoleBTNOK.setDisable(true);
            GrantRoleTextField.setDisable(true);
            GrantPrivtoUserField.setDisable(true);
            ToText.setDisable(true);

        }
    }

    @FXML
    private void GrantPrivBtnOnClick(ActionEvent event) {
        if (!SelectPrivGrant.isVisible()) {
            SelectPrivGrant.setVisible(true);
            TableNamePrivField.setVisible(true);
            ButtonAOKgrnantPriv.setVisible(true);
            TableNamePrivField1.setVisible(true);
            WithGrantOptUser.setVisible(true);

            SelectPrivGrant.setDisable(false);
            TableNamePrivField.setDisable(false);
            ButtonAOKgrnantPriv.setDisable(false);
            TableNamePrivField1.setDisable(false);
            WithGrantOptUser.setDisable(false);

        } else {

            SelectPrivGrant.setVisible(false);
            TableNamePrivField.setVisible(false);
            ButtonAOKgrnantPriv.setVisible(false);
            TableNamePrivField1.setVisible(false);
            WithGrantOptUser.setVisible(false);

            SelectPrivGrant.setDisable(true);
            TableNamePrivField.setDisable(true);
            ButtonAOKgrnantPriv.setDisable(true);
            TableNamePrivField1.setDisable(true);
            WithGrantOptUser.setDisable(true);

        }

    }

    @FXML
    private void GrantRoleBTNOKOnClick(ActionEvent event) {
        // username la ten role :)))))
        String rolename = GrantRoleTextField.getText().trim();

        if (rolename.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "Please enter role name :<");
            return;
        }

        String userName = GrantPrivtoUserField.getText().trim();

        if (rolename.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "Please enter role name :<");
            return;
        }

        System.out.println("User Name to add Role to: " + userName);

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to add role " + rolename + " to user " + userName + " ?");

        // Insert the new user into the database
        DataAccessLayer dal = null;
        Connection conn = null;
        CallableStatement cst = null;

        try {
            dal = DataAccessLayer.getInstance("", "");
            conn = dal.connect();

            cst = conn.prepareCall("{CALL SP_GRANT_ROLE_USER(?,?)}");
            // Set parameters for the stored procedure
            cst.setString(1, userName);
            cst.setString(2, rolename);
            // Execute the stored procedure
            System.out.println("nhan beo 1 ROLE");
            cst.execute();
            /// pst = conn.prepareStatement("CREATE USERNAME MAPMINHBEO IDENTIFIED BY
            /// Rack123456");
            System.out.println("nhan beo 2 ROLE");
            showAlert(Alert.AlertType.INFORMATION, "Success", "Role added to user successfully.");
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
    private void GrantPrivBtnOnClick1(ActionEvent event) {
        if (!SelectPrivGrant1.isVisible()) {
            SelectPrivGrant1.setVisible(true);
            TableNamePrivField2.setVisible(true);
            ButtonAOKgrnantPriv1.setVisible(true);
            TableNamePrivField11.setVisible(true);
            WithGrantOptUser1.setVisible(true);

            SelectPrivGrant1.setDisable(false);
            TableNamePrivField2.setDisable(false);
            ButtonAOKgrnantPriv1.setDisable(false);
            TableNamePrivField11.setDisable(false);
            WithGrantOptUser1.setDisable(false);

        } else {

            SelectPrivGrant1.setVisible(false);
            TableNamePrivField2.setVisible(false);
            ButtonAOKgrnantPriv1.setVisible(false);
            TableNamePrivField11.setVisible(false);
            WithGrantOptUser1.setVisible(false);

            SelectPrivGrant1.setDisable(true);
            TableNamePrivField2.setDisable(true);
            ButtonAOKgrnantPriv1.setDisable(true);
            TableNamePrivField11.setDisable(true);
            WithGrantOptUser1.setDisable(true);

        }

    }

    @FXML
    public void menuItemClicked(ActionEvent event) {
        MenuItem menuItem = (MenuItem) event.getSource();
        String selectedPrivilege = menuItem.getText();
        PrivSelectRevoke.setText(selectedPrivilege);
    }

    @FXML
    private void RevoketableOKOnClick(ActionEvent event) {
        String tableName = RevoketableField.getText().trim();

        // Validate the input fields
        if (tableName.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "Please enter table name :<");
            return;
        }

        User selectedUser = userTableView.getSelectionModel().getSelectedItem();
        String selectedPrivilege = PrivSelectRevoke.getText();
        System.out.println(selectedPrivilege);
        if (selectedPrivilege == "Privilege") {
            showAlert(Alert.AlertType.ERROR, "Error", "Please choose privilege type");
            return;
        }
        // Check if a row is selected
        if (selectedUser != null) {
            // Retrieve data from the first column of the selected row
            String userName = selectedUser.getUSERNAME();

            // Insert the new user into the database
            DataAccessLayer dal = null;
            Connection conn = null;
            CallableStatement cst = null;

            try {
                dal = DataAccessLayer.getInstance("", "");
                conn = dal.connect();
                // pst = conn.prepareStatement(String.format("CREATE USER %s IDENTIFIED BY %s",
                // username, password));
                cst = conn.prepareCall("{CALL SP_REVOKE_PRIV(?,?,?)}");
                // Set parameters for the stored procedure
                cst.setString(1, userName);
                cst.setString(2, selectedPrivilege);
                cst.setString(3, tableName);
                System.out.println(userName);
                System.out.println(selectedPrivilege);
                System.out.println(tableName);
                // Execute the stored procedure
                System.out.println("nhan beo 1 ROLE");
                cst.execute();
                /// pst = conn.prepareStatement("CREATE USERNAME MAPMINHBEO IDENTIFIED BY
                /// Rack123456");
                System.out.println("nhan beo 2 ROLE");
                showAlert(Alert.AlertType.INFORMATION, "Success", "Priv revoked from user successfully.");
                // Clear the input fields after successful insertion
                ADDRoleField.clear();
                // Refresh the TableView to reflect the changes
                loadRolesFromDatabase();
            } catch (SQLException e) {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to revoke privilege: " + e.getMessage());
                System.out.println(e.getMessage());
            }
        }
    }

    @FXML
    public void menuItemClickedRole(ActionEvent event) {
        MenuItem menuItem = (MenuItem) event.getSource();
        String selectedPrivilege = menuItem.getText();
        PrivSelectRevokeRole.setText(selectedPrivilege);
    }

    @FXML
    private void revokePrivfromRoleBTNOnclick1(ActionEvent event) {
        if (!PrivSelectRevokeRole.isVisible()) {
            PrivSelectRevokeRole.setVisible(true);
            RevoketableFieldRole.setVisible(true);
            RevoketableOKRole.setVisible(true);

            PrivSelectRevokeRole.setDisable(false);
            RevoketableFieldRole.setDisable(false);
            RevoketableOKRole.setDisable(false);

        } else {

            PrivSelectRevokeRole.setVisible(false);
            RevoketableFieldRole.setVisible(false);
            RevoketableOKRole.setVisible(false);

            PrivSelectRevokeRole.setDisable(true);
            RevoketableFieldRole.setDisable(true);
            RevoketableOKRole.setDisable(true);
        }
    }
    @FXML
    private void revokePrivfromRoleBTNOnclick(ActionEvent event) {
        if (!PrivSelectRevoke.isVisible()) {
            PrivSelectRevoke.setVisible(true);
            RevoketableField.setVisible(true);
            RevoketableOK.setVisible(true);

            PrivSelectRevoke.setDisable(false);
            RevoketableField.setDisable(false);
            RevoketableOK.setDisable(false);

        } else {

            PrivSelectRevoke.setVisible(false);
            RevoketableField.setVisible(false);
            RevoketableOK.setVisible(false);

            PrivSelectRevoke.setDisable(true);
            RevoketableField.setDisable(true);
            RevoketableOK.setDisable(true);
        }
    }

    @FXML
    private void RevoketableOKOnClickRole(ActionEvent event) {
        String tableName = RevoketableFieldRole.getText().trim();

        // Validate the input fields
        if (tableName.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "Please enter table name :<");
            return;
        }

        Role selectedRole = roleTableView.getSelectionModel().getSelectedItem();
        String selectedPrivilege = PrivSelectRevokeRole.getText();
        System.out.println(selectedPrivilege);
        if (selectedPrivilege == "Privilege") {
            showAlert(Alert.AlertType.ERROR, "Error", "Please choose privilege type");
            return;
        }
        // Check if a row is selected
        if (selectedRole != null) {
            // Retrieve data from the first column of the selected row
            String userName = selectedRole.getROLE();

            // Insert the new user into the database
            DataAccessLayer dal = null;
            Connection conn = null;
            CallableStatement cst = null;

            try {
                dal = DataAccessLayer.getInstance("", "");
                conn = dal.connect();
                // pst = conn.prepareStatement(String.format("CREATE USER %s IDENTIFIED BY %s",
                // username, password));
                cst = conn.prepareCall("{CALL SP_REVOKE_PRIV(?,?,?)}");
                // Set parameters for the stored procedure
                cst.setString(1, userName);
                cst.setString(2, selectedPrivilege);
                cst.setString(3, tableName);
                System.out.println(userName);
                System.out.println(selectedPrivilege);
                System.out.println(tableName);
                // Execute the stored procedure
                cst.execute();
                /// pst = conn.prepareStatement("CREATE USERNAME MAPMINHBEO IDENTIFIED BY
                /// Rack123456");
                System.out.println("nhan beo 2 ROLE");
                showAlert(Alert.AlertType.INFORMATION, "Success", "Priv ole revoked from role successfully.");
                // Clear the input fields after successful insertion
                ADDRoleField.clear();
                // Refresh the TableView to reflect the changes
                loadRolesFromDatabase();
            } catch (SQLException e) {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to revoke privilege: " + e.getMessage());
                System.out.println(e.getMessage());
            }
        }
    }

    @FXML
    public void menuItemClickedGrantPrivBoo(ActionEvent event) {
        MenuItem menuItem = (MenuItem) event.getSource();
        String selectedPrivilege = menuItem.getText();
        WithGrantOptUser.setText(selectedPrivilege);
    }

    @FXML
    public void menuItemClickedGrantPriv(ActionEvent event) {
        MenuItem menuItem = (MenuItem) event.getSource();
        String selectedPrivilege = menuItem.getText();
        SelectPrivGrant.setText(selectedPrivilege);
    }

    @FXML
    private void ButtonAOKgrnantPrivOnClick(ActionEvent event) {
        String tableName = TableNamePrivField.getText().trim();
        String columnName = TableNamePrivField1.getText().trim();

        // Validate the input fields
        if (tableName.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "Please enter table name :<");
            return;
        }
        
        User selectedUser = userTableView.getSelectionModel().getSelectedItem();
        String userName = selectedUser.getUSERNAME();
        String selectedPrivilege = SelectPrivGrant.getText();
        String GrantOption = WithGrantOptUser.getText();
        System.out.println(selectedPrivilege);
        if (selectedPrivilege == "Privilege") {
            showAlert(Alert.AlertType.ERROR, "Error", "Please choose privilege type");
            return;
        }
        // Check if a row is selected
    
            // Retrieve data from the first column of the selected row
           

            // Insert the new user into the database
            DataAccessLayer dal = null;
            Connection conn = null;
            CallableStatement cst = null;
            if (GrantOption.compareTo("Yes") == 0)
                GrantOption = "WITH GRANT OPTION";
            if (GrantOption.compareTo("No") == 0)
                GrantOption = " ";
            if (GrantOption.compareTo("Option") == 0)
                GrantOption = " ";
            System.out.println(GrantOption);
            System.out.println(tableName);
            System.out.println(userName);
            System.out.println(columnName);
            try {
                dal = DataAccessLayer.getInstance("", "");
                conn = dal.connect();
                // pst = conn.prepareStatement(String.format("CREATE USER %s IDENTIFIED BY %s",
                // username, password));
                if (selectedPrivilege.compareTo("Select") == 0) {
                    System.out.println("GG");
                    cst = conn.prepareCall("{CALL SP_GRANT_SELECT_PRIV(?,?,?,?)}");
                    cst.setString(1, userName);
                    cst.setString(2, tableName);
                    cst.setString(3, columnName);
                    cst.setString(4, GrantOption);
                }
                if (selectedPrivilege.compareTo("Update") == 0) {
                    System.out.println("GG");
                    cst = conn.prepareCall("{CALL SP_GRANT_UPDATE_PRIV(?,?,?,?)}");
                    cst.setString(1, userName);
                    cst.setString(2, tableName);
                    cst.setString(3, columnName);
                    cst.setString(4, GrantOption);
                }
                
                if (selectedPrivilege.compareTo("Insert") == 0) {
                    System.out.println("GG");
                    cst = conn.prepareCall("{CALL SP_GRANT_INSERT_PRIV(?,?,?)}");
                    cst.setString(1, userName);
                    cst.setString(2, tableName);
                    cst.setString(3, GrantOption);
                }
                
                if (selectedPrivilege.compareTo("Delete") == 0) {
                    System.out.println("GG");
                    cst = conn.prepareCall("{CALL SP_GRANT_DELETE_PRIV(?,?,?)}");
                    cst.setString(1, userName);
                    cst.setString(2, tableName);
                    cst.setString(3, GrantOption);
                }
                // Set parameters for the stored procedure
                System.out.println(selectedUser);
                
                System.out.println(userName);
                System.out.println(selectedPrivilege);
                System.out.println(tableName);
                // Execute the stored procedure
                System.out.println("nhan beo 1 ROLE");
                cst.execute();
                /// pst = conn.prepareStatement("CREATE USERNAME MAPMINHBEO IDENTIFIED BY
                /// Rack123456");
                System.out.println("nhan beo 2 ROLE");
                showAlert(Alert.AlertType.INFORMATION, "Success", "Priv grant from user successfully.");
                // Clear the input fields after successful insertion
                ADDRoleField.clear();
                // Refresh the TableView to reflect the changes
                loadRolesFromDatabase();
            } catch (SQLException e) {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to grant privilege: " + e.getMessage());
                System.out.println(e.getMessage());
            }
        
    }
    @FXML
    public void menuItemClickedGrantPrivBoo1(ActionEvent event) {
        MenuItem menuItem = (MenuItem) event.getSource();
        String selectedPrivilege = menuItem.getText();
        WithGrantOptUser1.setText(selectedPrivilege);
    }

    @FXML
    public void menuItemClickedGrantPriv1(ActionEvent event) {
        MenuItem menuItem = (MenuItem) event.getSource();
        String selectedPrivilege = menuItem.getText();
        SelectPrivGrant1.setText(selectedPrivilege);
    }

    @FXML
    private void ButtonAOKgrnantPrivOnClick1(ActionEvent event) {
        String tableName = TableNamePrivField2.getText().trim();
        String columnName = TableNamePrivField11.getText().trim();
        System.out.println(columnName);
        // Validate the input fields
        if (tableName.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "Please enter table name :<");
            return;
        }

        Role selectedRole = roleTableView.getSelectionModel().getSelectedItem();
        String selectedPrivilege = SelectPrivGrant1.getText();
        String GrantOption = WithGrantOptUser1.getText();
        System.out.println(selectedPrivilege);
        if (selectedPrivilege == "Privilege") {
            showAlert(Alert.AlertType.ERROR, "Error", "Please choose privilege type");
            return;
        }
        // Check if a row is selected
        if (selectedRole != null) {
            // Retrieve data from the first column of the selected row
            String userName = selectedRole.getROLE();

            // Insert the new user into the database
            DataAccessLayer dal = null;
            Connection conn = null;
            CallableStatement cst = null;
            if (GrantOption.compareTo("Yes") == 0)
                GrantOption = "WITH GRANT OPTION";
            if (GrantOption.compareTo("No") == 0)
                GrantOption = " ";
            if (GrantOption.compareTo("Option") == 0)
                GrantOption = " ";
            System.out.println(GrantOption);
            System.out.println(tableName);
            System.out.println(userName);
            System.out.println(columnName);
            try {
                dal = DataAccessLayer.getInstance("", "");
                conn = dal.connect();
                // pst = conn.prepareStatement(String.format("CREATE USER %s IDENTIFIED BY %s",
                // username, password));
                if (selectedPrivilege.compareTo("Select") == 0) {
                    System.out.println("GG");
                    cst = conn.prepareCall("{CALL SP_GRANT_SELECT_PRIV(?,?,?,?)}");
                    cst.setString(1, userName);
                    cst.setString(2, tableName);
                    cst.setString(3, columnName);
                    cst.setString(4, GrantOption);
                }
                if (selectedPrivilege.compareTo("Update") == 0) {
                    System.out.println("GG");
                    cst = conn.prepareCall("{CALL SP_GRANT_UPDATE_PRIV(?,?,?,?)}");
                    cst.setString(1, userName);
                    cst.setString(2, tableName);
                    cst.setString(3, columnName);
                    cst.setString(4, GrantOption);
                }
                
                if (selectedPrivilege.compareTo("Insert") == 0) {
                    System.out.println("GG");
                    cst = conn.prepareCall("{CALL SP_GRANT_INSERT_PRIV(?,?,?)}");
                    cst.setString(1, userName);
                    cst.setString(2, tableName);
                    cst.setString(3, GrantOption);
                }
                
                if (selectedPrivilege.compareTo("Delete") == 0) {
                    System.out.println("GG");
                    cst = conn.prepareCall("{CALL SP_GRANT_DELETE_PRIV(?,?,?)}");
                    cst.setString(1, userName);
                    cst.setString(2, tableName);
                    cst.setString(3, GrantOption);
                }
                // Set parameters for the stored procedure

                System.out.println(userName);
                System.out.println(selectedPrivilege);
                System.out.println(tableName);
                // Execute the stored procedure
                System.out.println("nhan beo 1 ROLE");
                cst.execute();
                /// pst = conn.prepareStatement("CREATE USERNAME MAPMINHBEO IDENTIFIED BY
                /// Rack123456");
                System.out.println("nhan beo 2 ROLE");
                showAlert(Alert.AlertType.INFORMATION, "Success", "Priv grant to role successfully.");
                // Clear the input fields after successful insertion
                ADDRoleField.clear();
                // Refresh the TableView to reflect the changes
                loadRolesFromDatabase();
            } catch (SQLException e) {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to grnat privilege: " + e.getMessage());
                System.out.println(e.getMessage());
            }
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
