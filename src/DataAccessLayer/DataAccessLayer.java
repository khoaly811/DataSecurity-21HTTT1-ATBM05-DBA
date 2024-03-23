package DataAccessLayer;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import oracle.jdbc.OracleDriver;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class DataAccessLayer {
    private static volatile DataAccessLayer instance;
    private static Connection conn;
    private static ResultSet rs;

    private DataAccessLayer() throws SQLException {
        // Initialize the database connection
        DriverManager.registerDriver(new OracleDriver());
        conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "QLK", "Rack12345678");
    }

    public static DataAccessLayer getInstance() throws SQLException {
        // Singleton pattern to ensure only one instance of DataAccessLayer is created
        DataAccessLayer result = instance;
        if (result == null) {
            synchronized (DataAccessLayer.class) {
                result = instance;
                if (result == null) {
                    instance = result = new DataAccessLayer();
                }
            }
        }
        return result;
    }

    public Connection connect() {
        // Get the database connection
        return conn;
    }
    
}
