
import java.sql.*;

public class Database {
    private static final String URL = DatabaseConfig.getUrl();
    private static final String USER = DatabaseConfig.getUser();
    private static final String PASSWORD = DatabaseConfig.getPassword();

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Driver not found!");
            e.printStackTrace();
        }
    }

    public static boolean insertUser(User user) {
        // Validate user data
        if (user == null || !isValidUser(user)) {
            System.err.println("Invalid user data");
            return false;
        }

        String query = "INSERT INTO users (username, email, phone, dob, password) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPhone());
            stmt.setString(4, user.getDob());
            // Hash the password before storing
            String hashedPassword = PasswordUtil.hashPassword(user.getPassword());
            stmt.setString(5, hashedPassword);

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;

        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                System.err.println("Duplicate entry: User already exists");
            } else if (e.getMessage().contains("Connection refused")) {
                System.err.println("Database connection failed");
            } else {
                System.err.println("Database error: " + e.getMessage());
            }
            e.printStackTrace();
            return false;
        }
    }

    private static boolean isValidUser(User user) {
        return user.getUsername() != null && !user.getUsername().isEmpty() &&
               user.getEmail() != null && user.getEmail().contains("@") &&
               user.getPhone() != null && !user.getPhone().isEmpty() &&
               user.getDob() != null && !user.getDob().isEmpty() &&
               user.getPassword() != null && user.getPassword().length() >= 6;
    }
}
