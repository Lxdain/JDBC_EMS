import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Functionality {

    private Connection conn;

    public void checkConnection() {
        String url = "jdbc:mysql://localhost:3306/jdbc_ems";
        String username = "root";
        String password = "root";

        try {
            conn = DriverManager.getConnection(url, username, password);
            System.out.println("Connection to the database successful!");
        } catch (SQLException e) {
            System.out.println("Failed to connect to the database. Error message: " + e.getMessage());
        }
    }
}
