package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static Connection connection;
    private static final String URL = "jdbc:postgresql://localhost:5432/baticuisine";
    private static final String USER = "user";
    private static final String PASSWORD = "password";

    // Private constructor to prevent instantiation
    private DatabaseConnection() {
    }

    public static synchronized Connection getConnection() {
        if (connection == null) {
            try {
                Class.forName("org.postgresql.Driver");
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
            } catch (ClassNotFoundException e) {
                System.err.println("PostgreSQL driver not found: " + e.getMessage());
                // Log the error or handle it appropriately
                throw new RuntimeException("PostgreSQL driver not found", e);
            } catch (SQLException e) {
                System.err.println("Error establishing database connection: " + e.getMessage());
                // Log the error or handle it appropriately
                throw new RuntimeException("Error establishing database connection", e);
            }
        }
        return connection;
    }
}
