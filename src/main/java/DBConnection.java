import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {
    private final String url;
    private final String user;
    private final String password;

    public DBConnection(String propertiesPath) {
        if (propertiesPath == null || propertiesPath.trim().isEmpty()) {
            throw new IllegalArgumentException("Database properties path cannot be null or empty.");
        }

        Properties properties = new Properties();
        Path path = Paths.get(propertiesPath.trim());

        try (InputStream inputStream = Files.newInputStream(path)) {
            properties.load(inputStream);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to read database properties: " + path, e);
        }

        this.url = requireProperty(properties, "db.url");
        this.user = requireProperty(properties, "db.user");
        this.password = requireProperty(properties, "db.password");
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    private String requireProperty(Properties properties, String key) {
        String value = properties.getProperty(key);
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalStateException("Missing required property: " + key);
        }
        return value.trim();
    }
}
