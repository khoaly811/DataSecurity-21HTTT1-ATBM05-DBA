package DataAccessLayer;

import java.sql.Connection;
import java.sql.DriverManager;
//import java.sql.ResultSet;
import java.sql.SQLException;



public class DataAccessLayer {
    private static volatile DataAccessLayer instance;
    private static Connection conn;
    //private static ResultSet rs;

    private DataAccessLayer(String username, String password) throws SQLException {
        // Initialize the database connection
        DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
        conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/ATBM2024", username, password);
    }

    public static DataAccessLayer getInstance(String username, String password) throws SQLException {
        // Singleton pattern to ensure only one instance of DataAccessLayer is created
        DataAccessLayer result = instance;
        if (result == null) {
            synchronized (DataAccessLayer.class) {
                result = instance;
                if (result == null) {
                    instance = result = new DataAccessLayer(username, password);
                }
            }
        }
        return result;
    }

    public Connection connect() {
        // Get the database connection
        return conn;
    }

    // public static Nhansu getNHANSU(String MANV) throws SQLException{
    //     //Connection con = DataAccessLayer.getInstance().connect();
    //     PreparedStatement pst = con.prepareStatement(String.format("SELECT HOTEN, PHAI, NGSINH, PHUCAP, DT, VAITRO FROM C##QLK.NHANSU WHERE MANV='%s'", MANV));
        
    //     Nhansu nsu = new Nhansu();
    //     nsu.setMANV(MANV);
    //     rs = pst.executeQuery();
    //     if(rs.next()){
    //         nsu.setHOTEN(rs.getString("HOTEN"));
    //         nsu.setPHAI(rs.getString("PHAI"));
    //         java.sql.Date sqlDate = rs.getDate("NGSINH");
    //         LocalDate localDate = sqlDate.toLocalDate();
    //         nsu.setNGSINH(localDate);
    //         nsu.setPHUCAP(rs.getInt("PHUCAP"));
    //         nsu.setDT(rs.getString("DT"));
    //         nsu.setVAITRO(rs.getString("VAITRO"));
    //     }
        
        
    //     return nsu;
    // }

    // public static User getUser(String username, String password) {
    //     User user = new User();

    //     return user;
    // }
}
