package atbm_05;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import dto.User;
import dto.Role;
import DataAccessLayer.DataAccessLayer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    private Label userUsernameUpdateLabel;

    @FXML
    private Label userPasswordUpdateLabel;

    @FXML
    private TextField userUsernameUpdateTextField;

    @FXML
    private TextField userPasswordUpdateTextField;


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

        // Load from database
        loadUsersFromDatabase();
        loadRolesFromDatabase();
    }

    @FXML
    private void onUpdateButtonClick(ActionEvent event) {
        userUsernameUpdateLabel.setVisible(true);
        userPasswordUpdateLabel.setVisible(true);
        userUsernameUpdateTextField.setVisible(true);
        userPasswordUpdateTextField.setVisible(true);

        userUsernameUpdateLabel.setDisable(false);
        userPasswordUpdateLabel.setDisable(false);
        userUsernameUpdateTextField.setDisable(false);
        userPasswordUpdateTextField.setDisable(false);
    }

    private void loadUsersFromDatabase() {
        DataAccessLayer dal = null;
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            dal = DataAccessLayer.getInstance("your_username", "your_password");
            conn = dal.connect();
            pst = conn.prepareStatement("select * from dba_users join dba_role_privs on dba_users.username = dba_role_privs.grantee where (username like 'NV%' or username like 'SV%')");
            rs = pst.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setUSERNAME(rs.getString("USERNAME"));
                user.setACCOUNT_STATUS((rs.getString("ACCOUNT_STATUS")));
                user.setCREATED(rs.getDate("CREATED").toLocalDate());
                if (rs.getDate("LAST_LOGIN") == null){
                    user.setLAST_LOGIN(null);
                }
                else{
                    user.setLAST_LOGIN(rs.getDate("LAST_LOGIN").toLocalDate());
                }
                user.setGRANTED_ROLE(rs.getString("GRANTED_ROLE"));
                // if (rs.getDate("PASSWORD_CHANGE_DATE") == null){
                //     user.setPASSWORD_CHANGE_DATE(null);
                // }
                // else{
                //     user.setPASSWORD_CHANGE_DATE(rs.getDate("PASSWORD_CHANGE_DATE").toLocalDate());
                // }
                
                userList.add(user);
            }
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load users from the database.");
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
            pst = conn.prepareStatement("select* from role_tab_privs where role = 'GV' or role = 'NVCB' or role = 'GVU' or role = 'TDV' or role = 'TKHOA' or role = 'SV' ");
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
                //     user.setPASSWORD_CHANGE_DATE(null);
                // }
                // else{
                //     user.setPASSWORD_CHANGE_DATE(rs.getDate("PASSWORD_CHANGE_DATE").toLocalDate());
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
