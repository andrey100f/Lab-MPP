package concertpro.persistance.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class JdbcUtils {

    private final Properties jdbcProperties;
    private  Connection instance = null;

    public JdbcUtils(Properties jdbcProperties) {
        this.jdbcProperties=jdbcProperties;
    }

    private Connection getNewConnection() {

        String url = this.jdbcProperties.getProperty("jdbc.url");
        String user = this.jdbcProperties.getProperty("jdbc.user");
        String password = this.jdbcProperties.getProperty("jdbc.password");

        Connection connection = null;
        try {
            if (user != null && password != null) {
                connection = DriverManager.getConnection(url, user, password);
            }
            else {
                connection = DriverManager.getConnection(url);
            }
        } catch (SQLException e) {
            System.out.println("Error getting connection " + e);
        }

        return connection;
    }

    public Connection getConnection() {
        try {
            if (instance == null || instance.isClosed()) {
                instance = getNewConnection();
            }
        } catch (SQLException e) {
            System.out.println("Error DB "+e);
        }

        return instance;
    }

}
