import java.io.*;
import java.util.Properties;

public class DatabaseConfig {
    private static final String CONFIG_FILE = "db.properties";
    private static String url;
    private static String user;
    private static String password;

    static {
        loadConfig();
    }

    private static void loadConfig() {
        Properties props = new Properties();
        try (InputStream input = new FileInputStream(CONFIG_FILE)) {
            props.load(input);
            url = props.getProperty("db.url", "jdbc:mysql://localhost:3306/your_database_name");
            user = props.getProperty("db.user", "your_db_username");
            password = props.getProperty("db.password", "your_db_password");
        } catch (IOException e) {
            System.err.println("Config file not found. Using default values.");
            url = "jdbc:mysql://localhost:3306/your_database_name";
            user = "your_db_username";
            password = "your_db_password";
        }
    }

    public static String getUrl() {
        return url;
    }

    public static String getUser() {
        return user;
    }

    public static String getPassword() {
        return password;
    }
}
